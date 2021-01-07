package com.example.gestion_course.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.gestion_course.AppDatabase
import com.example.gestion_course.entities.Equipe
import com.example.gestion_course.entities.EquipeAvecParticipants
import com.example.gestion_course.entities.Etape
import com.example.gestion_course.entities.Participant
import kotlinx.coroutines.runBlocking

class CourseViewModel(application: Application) : AndroidViewModel(application) {

    val database = AppDatabase.getInstance(application.applicationContext)

    suspend fun getEquipes(): List<Equipe>{
            return database.equipeDao().getAll()
    }

    suspend fun getParticipants(nbEquipes: Int): List<List<Participant>>{
        var listParticipantsList = mutableListOf<List<Participant>>()
        for (i in 1..nbEquipes){
            listParticipantsList.add(database.participantDao().getParticipantsParEquipe(i))
        }
        return listParticipantsList
    }

    suspend fun getEtapes(): List<Etape>{
        return database.etapeDao().getAll()
    }
}