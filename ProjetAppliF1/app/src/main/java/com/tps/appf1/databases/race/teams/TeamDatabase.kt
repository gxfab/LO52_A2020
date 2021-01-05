package com.tps.appf1.databases.race.teams

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TeamEntity::class], version = 1, exportSchema = false)
abstract class TeamDatabase : RoomDatabase() {
    abstract val teamDatabaseDao: TeamDAO  //Refers the Database Access Object
    companion object {    //Allows to create and use db without instantiating the class
        @Volatile
        private var INSTANCE: TeamDatabase? = null   //Instantly sync the db to all threads + avoid repeatedly opening connections to the database

        fun getInstance(context: Context): TeamDatabase {
            synchronized(this) {        //Ensure one thread is writing to the db at a time
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(          //Build db
                        context.applicationContext,
                        TeamDatabase::class.java,
                        "teams_info_database").fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}