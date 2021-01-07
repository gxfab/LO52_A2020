package com.example.gestion_course

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gestion_course.dao.*
import com.example.gestion_course.entities.Equipe
import com.example.gestion_course.entities.Etape
import com.example.gestion_course.entities.Participant
import com.example.gestion_course.entities.Temps
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Equipe::class, Participant::class, Etape::class, Temps::class), version = 6)
abstract class AppDatabase : RoomDatabase() {

    abstract fun equipeDao(): EquipeDao
    abstract fun participantDao(): ParticipantDao
    abstract fun etapeDao(): EtapeDao
    abstract fun tempsDao(): TempsDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): AppDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    ctx.applicationContext, AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

            return instance!!

        }
    }

    fun clearTables() {
        GlobalScope.launch(Dispatchers.IO) {
            this@AppDatabase.clearAllTables()
        }
    }
}