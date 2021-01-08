package com.codep25.codep25.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codep25.codep25.R
import com.codep25.codep25.extension.postError
import com.codep25.codep25.extension.postSuccess
import com.codep25.codep25.model.entity.RaceWithTeams
import com.codep25.codep25.model.entity.Resource
import com.codep25.codep25.model.storage.DataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class RaceResultsViewModel @Inject constructor(
    private val db: DataBase
) : ViewModel() {
    private val _race = MutableLiveData<Resource<RaceWithTeams>>()
    val race: LiveData<Resource<RaceWithTeams>> = _race

    fun fetchRaceById(raceId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _race.postSuccess(db.getRaceWithTeamsById(raceId))
            } catch (e: Exception) {
                _race.postError(R.string.activity_results_race_retrieve_error)
            }
        }
    }
}