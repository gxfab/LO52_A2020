package com.codep25.codep25.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codep25.codep25.Config
import com.codep25.codep25.R
import com.codep25.codep25.extension.postError
import com.codep25.codep25.extension.postSuccess
import com.codep25.codep25.extension.setLoading
import com.codep25.codep25.model.color.ParticipantColors
import com.codep25.codep25.model.entity.*
import com.codep25.codep25.model.storage.DataBase
import com.codep25.codep25.model.storage.Storage
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.*
import javax.inject.Inject
import kotlin.math.min

class ParticipantsViewModel @Inject constructor(
    private val storage: Storage,
    private val colorsFactory: ParticipantColors.Factory,
    private val gson: Gson,
    private val appContext: Context,
    private val db: DataBase
) : ViewModel() {

    private val _participants = MutableLiveData<Resource<List<ParticipantWithTeam>>>()
    val participants: LiveData<Resource<List<ParticipantWithTeam>>> = _participants

    private val _parsedFile = SingleLiveEvent<Resource<Int>>()
    val parsedFile: LiveData<Resource<Int>> = _parsedFile

    private val _outFile = SingleLiveEvent<Resource<Int>>()
    val outFile: LiveData<Resource<Int>> = _outFile

    fun getParticipantsColors() =
        colorsFactory.fromPrefOrDefaultOrRandomAndSave(
            Config.DEFAULT_PARTICIPANTS_COLORS, storage.getNumberOfParticipantsPerTeam()
        )

    fun getMaxLevel(): Int = storage.getMaxLevel()

    fun fetchParticipants() {
        _participants.setLoading()
        viewModelScope.launch(Dispatchers.IO) {
            dbFetch()
        }

    }

    private fun dbFetch() {
        val p = db.getAllParticipantsWithTeam()
        _participants.postSuccess(p)
    }

    fun createParticipant(name: String, level: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val p = Participant(
                id_participant = 0,
                name = name,
                level = level,
                id_fkteam = null,
                teamPosition = null
            )

            addParticipants(p)
        }
    }

    fun updateParticipant(id: Long, name: String, level: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val p = db.getParticipantById(id)
                val newP = Participant(
                    id_participant = p.id_participant,
                    name = name,
                    level = min(level, getMaxLevel()),
                    id_fkteam = p.id_fkteam,
                    teamPosition = p.teamPosition
                )

                storage.setShouldRecreateTeams(true)
                db.updateParticipant(newP)
                dbFetch()
            } catch (e: Exception) {
                _parsedFile.postError(R.string.error_failed_to_update_participant)
            }
        }
    }

    fun deleteParticipant(id: Long) {
        return deleteParticipants(id)
    }

    fun deleteParticipants(vararg ids: Long) {
        viewModelScope.launch (Dispatchers.IO){
            db.deleteParticipants(*ids)
            storage.setShouldRecreateTeams(true)
            dbFetch()
        }
    }

    fun deleteAllParticipants() {
        viewModelScope.launch (Dispatchers.IO){
            db.clearParticipants()
            storage.setShouldRecreateTeams(true)
            dbFetch()
        }
    }

    fun fromFile(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                appContext.contentResolver.openInputStream(uri).use {stream ->
                    val reader = BufferedReader(InputStreamReader(stream))
                    val participants = gson.fromJson(reader, Array<Participant>::class.java)
                    reader.close()
                    _parsedFile.postSuccess(participants.size)

                    addParticipants(*participants)
                }
            } catch (e: Exception) {
                _parsedFile.postError(R.string.error_failed_to_parse_file)
            }
        }
    }

    fun toFile(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val participants = db.getAllParticipants()
                    .map { SimpleParticipant.fromParticipant(it) }

                appContext.contentResolver.openOutputStream(uri).use {stream ->
                    val writer = BufferedWriter(OutputStreamWriter(stream))
                    gson.toJson(participants, writer)
                    writer.close()
                }

                _outFile.postSuccess(participants.size)
            } catch (e: Exception) {
                _outFile.postError(R.string.error_failed_to_write_file)
            }
        }
    }

    private fun addParticipants(vararg participants: Participant) {
        val maxLevel = getMaxLevel()
        try {
            val scaledParticipants = participants.map { it.applyMaxLevel(maxLevel) }
            db.addAllParticipants(*scaledParticipants.toTypedArray())
            storage.setShouldRecreateTeams(true)
            dbFetch()
        } catch (e: Exception) {
            _parsedFile.postError(R.string.error_failed_to_add_participant)
        }
    }
}