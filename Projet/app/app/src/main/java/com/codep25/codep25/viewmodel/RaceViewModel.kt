package com.codep25.codep25.viewmodel

import android.content.Context
import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codep25.codep25.Config
import com.codep25.codep25.extension.postSuccess
import com.codep25.codep25.model.color.ParticipantColors
import com.codep25.codep25.model.entity.*
import com.codep25.codep25.model.storage.DataBase
import com.codep25.codep25.model.storage.Storage
import com.codep25.codep25.router.RaceResultRouter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class RaceViewModel @Inject constructor(
    private val colorsFactory: ParticipantColors.Factory,
    private val storage: Storage,
    private val db: DataBase
) : ViewModel() {

    data class State(
        val isStarted: Boolean,
        val isFinished: Boolean,
        val timeBase: Long
    ) {
        fun mutateFinished(): State {
            return State(false, isFinished = true, timeBase = timeBase)
        }
    }

    private val _teams = MutableLiveData<Resource<List<RacingTeam>>>()
    val teams: LiveData<Resource<List<RacingTeam>>> = _teams

    private val _state = MutableLiveData<State>().apply {
        value =  State(isStarted = false, isFinished = false, timeBase = SystemClock.elapsedRealtime())
    }
    val state: LiveData<State> = _state

    private val _lastRaceId = MutableLiveData<Long>()
    val lastRaceId: LiveData<Long> = _lastRaceId

    private val isStarted get() = _state.value?.isStarted ?: false
    private val isFinished get() = _state.value?.isFinished ?: false
    private val timeBase get() = _state.value?.timeBase ?: SystemClock.elapsedRealtime()

    private val raceConfig = RaceConfig(
        storage.getNumberOfTurnsPerParticipant(),
        storage.getNumberOfParticipantsPerTeam() - 1
    )

    fun getParticipantsColors() =
        colorsFactory.fromPrefOrDefaultOrRandomAndSave(
            Config.DEFAULT_PARTICIPANTS_COLORS, storage.getNumberOfParticipantsPerTeam()
        )

    fun fetchTeams(force: Boolean = false) {
        if (!force && (isStarted || isFinished))
            return

        viewModelScope.launch(Dispatchers.IO) {
            val teams = db.getAllTeams()
            _teams.postSuccess(teams.map { RacingTeam.Factory.fromTeamWithParticipants(it) })
        }
    }

    fun computeNextStep(team: RacingTeam) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!isStarted) return@launch
            val oldTeams = _teams.value?.data ?: return@launch
            val teamInd = oldTeams.lastIndexOf(team)
            if (teamInd == -1) return@launch
            val newTeamState = team.computeNext(raceConfig, SystemClock.elapsedRealtime() - timeBase)
            if (team == newTeamState) return@launch
            val newTeams = oldTeams.toMutableList()
            var allArrived = true
            newTeams[teamInd] = newTeamState

            for (t in newTeams) {
                if (t.progress != 100) {
                    allArrived = false
                    break
                }
            }

            _teams.postSuccess(newTeams)

            if (allArrived) {
                storeResults()
                _state.postValue(_state.value?.mutateFinished())
            }
        }
    }

    private fun storeResults() {
        viewModelScope.launch(Dispatchers.IO) {
            val teams = _teams.value?.data ?: return@launch
            val race = RaceWithTeams(raceConfig, teams)
            _lastRaceId.postValue(db.insertRaceWithTeams(race))
        }
    }

    fun start() {
        fetchTeams(force = true)
        _state.value = State(true, isFinished = false, timeBase = SystemClock.elapsedRealtime())
    }

    fun stop() {
        _state.value = State(
            false,
            isFinished = false,
            timeBase = timeBase
        )
    }

    fun showResults(context: Context) {
        val raceId = lastRaceId.value ?: return
        RaceResultRouter.openRace(context, raceId)
    }
}