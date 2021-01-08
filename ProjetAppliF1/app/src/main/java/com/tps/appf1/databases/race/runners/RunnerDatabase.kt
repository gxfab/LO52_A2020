package com.tps.appf1.databases.race.runners

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RunnerEntity::class], version = 2, exportSchema = false)
abstract class RunnerDatabase : RoomDatabase() {
    abstract fun runnerDatabaseDao(): RunnerDAO  //Refers the Database Access Object
    companion object {    //Allows to create and use db without instantiating the class
        @Volatile
        private var INSTANCE: RunnerDatabase? = null   //Instantly sync the db to all threads + avoid repeatedly opening connections to the database

        fun getInstance(context: Context): RunnerDatabase {
            synchronized(this) {        //Ensure one thread is writing to the db at a time
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(          //Build db
                        context.applicationContext,
                        RunnerDatabase::class.java,
                        "runners_info_database").fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}