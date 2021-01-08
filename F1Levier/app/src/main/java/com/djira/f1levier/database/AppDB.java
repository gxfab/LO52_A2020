package com.djira.f1levier.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.djira.f1levier.dao.EquipeDao;
import com.djira.f1levier.dao.ParticipantDao;
import com.djira.f1levier.entity.Equipe;
import com.djira.f1levier.entity.Participant;

@Database(entities = {Participant.class, Equipe.class}, version = 13, exportSchema = false)
public abstract class AppDB extends RoomDatabase {
    // create database instance
    private static AppDB database;
    //Define database name;
    private static String DATABASE_NAME = "database";

    public synchronized static AppDB getInstance(Context context) {
        //When database is null initialize database
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext()
                    ,AppDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public static void destroyInstance() {
        database = null;
    }

    //Create Dao

    public abstract ParticipantDao participantDao();
    public abstract EquipeDao equipeDao();

}
