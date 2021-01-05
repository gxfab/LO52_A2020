package com.tps.appf1.databases.race.race

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RaceEntity::class], version = 1, exportSchema = false)
abstract class RaceDatabase : RoomDatabase() {
    abstract val raceDatabaseDao: RaceDAO  //Refers the Database Access Object
    companion object {    //Allows to create and use db without instantiating the class
        @Volatile
        private var INSTANCE: RaceDatabase? = null   //Instantly sync the db to all threads + avoid repeatedly opening connections to the database

        fun getInstance(context: Context): RaceDatabase {
            synchronized(this) {        //Ensure one thread is writing to the db at a time
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(          //Build db
                        context.applicationContext,
                        RaceDatabase::class.java,
                        "races_history_database").fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
