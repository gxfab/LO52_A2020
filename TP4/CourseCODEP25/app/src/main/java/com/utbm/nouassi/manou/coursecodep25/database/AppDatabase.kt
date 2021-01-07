package com.utbm.nouassi.manou.coursecodep25.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.utbm.nouassi.manou.coursecodep25.database.dao.CoureurDao
import com.utbm.nouassi.manou.coursecodep25.database.dao.CourseDao
import com.utbm.nouassi.manou.coursecodep25.database.dao.EquipeDao
import com.utbm.nouassi.manou.coursecodep25.database.dao.ParticipantDao
import com.utbm.nouassi.manou.coursecodep25.database.entity.*

@Database(entities = arrayOf(Coureur::class, Course::class, Equipe::class, Participant::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
    abstract fun equipeDao(): EquipeDao
    abstract fun participantDao(): ParticipantDao
    abstract fun coureurDao(): CoureurDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "db-course-codep25"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}