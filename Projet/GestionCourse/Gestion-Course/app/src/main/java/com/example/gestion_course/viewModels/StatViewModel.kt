package com.example.gestion_course.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.gestion_course.AppDatabase
import com.example.gestion_course.entities.Participant

class StatViewModel(application: Application) : AndroidViewModel(application) {

    val database = AppDatabase.getInstance(application.applicationContext)

    fun getParticipants(): List<Participant>{
        return database.participantDao().getAll()
    }
}