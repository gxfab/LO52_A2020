package com.codep25.codep25.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codep25.codep25.R
import com.codep25.codep25.extension.postError
import com.codep25.codep25.extension.postSuccess
import com.codep25.codep25.model.entity.Race
import com.codep25.codep25.model.entity.Resource
import com.codep25.codep25.model.storage.DataBase
import com.codep25.codep25.router.RaceResultRouter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class RacesResultsViewModel @Inject constructor(
    private val db: DataBase
) : ViewModel() {
    private val _races = MutableLiveData<Resource<List<Race>>>()
    val races: LiveData<Resource<List<Race>>> = _races

    fun fetchRaces() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _races.postSuccess(db.getRaces())
            } catch (e: Exception) {
                _races.postError(R.string.unknown_error)
            }
        }
    }

    fun openRace(race: Race, context: Context) {
        RaceResultRouter.openRace(context, race)
    }
}