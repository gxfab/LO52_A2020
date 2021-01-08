package com.codep25.codep25.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codep25.codep25.R
import com.codep25.codep25.extension.postError
import com.codep25.codep25.extension.postSuccess
import com.codep25.codep25.extension.setError
import com.codep25.codep25.extension.setSuccess
import com.codep25.codep25.model.color.ParticipantColors
import com.codep25.codep25.model.entity.Resource
import com.codep25.codep25.model.storage.DataBase
import com.codep25.codep25.model.storage.Storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val storage: Storage,
    private val db: DataBase,
    val colorFactory: ParticipantColors.Factory
) : ViewModel() {
    private val _maxLevel = MutableLiveData<Resource<Int>>()
    val maxLevel: LiveData<Resource<Int>> = _maxLevel

    private val _nbParticipantsPerTeam = MutableLiveData<Resource<Int>>()
    val nbParticipantsPerTeam: LiveData<Resource<Int>> = _nbParticipantsPerTeam

    private val _participantColors = MutableLiveData<Resource<ParticipantColors>>()
    val participantColors: LiveData<Resource<ParticipantColors>> = _participantColors

    private val _changesApplied = MutableLiveData<Resource<Boolean>>()
    val changesApplied: LiveData<Resource<Boolean>> = _changesApplied

    fun fetchMaxLevel() {
        _maxLevel.postSuccess(storage.getMaxLevel())
    }

    fun fetchNbParticipantsPerTeam() {
        _nbParticipantsPerTeam.postSuccess(storage.getNumberOfParticipantsPerTeam())
    }

    fun fetchParticipantColors() {
        _participantColors.postSuccess(
            colorFactory.fromPref()
        )
    }

    fun resizeColors(colors: ParticipantColors, newSize: Int) {
        _participantColors.postSuccess(
            colors.resizeWith(newSize) {colorFactory.fromRandom(it)}
        )
    }

    fun applyChanges(maxLevel: Int, nbParticipantsPerTeam: Int,
                     colors: ParticipantColors, safeMode: Boolean=true
    ) {
        if (maxLevel <= 0 || nbParticipantsPerTeam <= 0) {
            _changesApplied.setError(R.string.fragment_settings_invalid_params)
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            if (safeMode && db.getParticipantsOverLevel(maxLevel).isNotEmpty()) {
                _changesApplied.postError(R.string.fragment_settings_level_fit_warning)
                return@launch
            }

            storage.setMaxLevel(maxLevel)
            storage.setNumberOfParticipantsPerTeam(nbParticipantsPerTeam)
            db.updateMaxParticipantLevel(maxLevel)
            colorFactory.saveToPref(colors)

            _changesApplied.postSuccess(true)

            fetchMaxLevel()
            fetchNbParticipantsPerTeam()
        }
    }
}