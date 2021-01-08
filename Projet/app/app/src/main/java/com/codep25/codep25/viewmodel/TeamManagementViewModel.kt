package com.codep25.codep25.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codep25.codep25.Config
import com.codep25.codep25.R
import com.codep25.codep25.extension.postError
import com.codep25.codep25.extension.postSuccess
import com.codep25.codep25.extension.setSuccess
import com.codep25.codep25.model.balancing.TeamBalancer
import com.codep25.codep25.model.color.ParticipantColors
import com.codep25.codep25.model.entity.Participant
import com.codep25.codep25.model.entity.Resource
import com.codep25.codep25.model.entity.Team
import com.codep25.codep25.model.entity.TeamWithParticipants
import com.codep25.codep25.model.storage.DataBase
import com.codep25.codep25.model.storage.Storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class TeamManagementViewModel @Inject constructor(
    private val storage: Storage,
    private val colorsFactory: ParticipantColors.Factory,
    private val db: DataBase,
    private val appContext: Context
) : ViewModel() {

    private val _teams = MutableLiveData<Resource<List<TeamWithParticipants>>>()
    val teams: LiveData<Resource<List<TeamWithParticipants>>> = _teams

    val participantColors = colorsFactory.fromPrefOrDefaultOrRandomAndSave(
        Config.DEFAULT_PARTICIPANTS_COLORS, storage.getNumberOfParticipantsPerTeam()
    )

    fun fetchTeams(forceOld: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            val teams = db.getAllTeams()

            if (!forceOld) {
                val nbOfParticipants = db.getNumberOfParticipants()

                if (nbOfParticipants == 0) {
                    _teams.postSuccess(listOf())
                    return@launch
                }

                if (nbOfParticipants % 3 != 0) {
                    _teams.postError(R.string.incorrect_participants_number)
                    return@launch
                }

                val teamCounts = teams.map { it.participants.size }
                val maxTeamSize = teamCounts.max() ?: 0
                val teamSum = teamCounts.sum()
                val nominalTeamSize = storage.getNumberOfParticipantsPerTeam()

                if (
                    storage.shouldRecreateTeams() ||
                    nbOfParticipants != teamSum ||
                    (teamSum >= nominalTeamSize && maxTeamSize != nominalTeamSize)
                ) {
                    _teams.postError(R.string.fragment_teams_changed_warn)
                    return@launch
                }
            }

            storage.setShouldRecreateTeams(false)
            _teams.postSuccess(teams)

        }
    }

    fun createTeams() {
        viewModelScope.launch(Dispatchers.IO) {
            val participants = db.getAllParticipants()
            if (participants.isEmpty())
                return@launch

            val teamSet = TeamBalancer.balance(participants, storage.getNumberOfParticipantsPerTeam())
            val teams = ArrayList<TeamWithParticipants>(teamSet.size)

            for (t in teamSet.teams) {
                teams.add(TeamWithParticipants(
                    Team(
                        0,
                        appContext.getString(R.string.team_name_format, t.getBestParticipant().name)
                    ), t.participants.toList()
                ))
            }

            db.clearTeams()
            db.addAllTeams(*teams.toTypedArray())

            fetchTeams(forceOld = true)
        }
    }

    fun updateTeam(teamWithParticipants: TeamWithParticipants) {
        viewModelScope.launch(Dispatchers.IO) {
            db.updateAllParticipants(*teamWithParticipants.participants.toTypedArray())
        }
    }

}