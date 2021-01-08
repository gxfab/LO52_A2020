package com.example.lo52_project_v2.model.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.lo52_project_v2.model.bo.Course;
import com.example.lo52_project_v2.model.bo.Participant;
import com.example.lo52_project_v2.model.bo.Equipe;
import com.example.lo52_project_v2.model.bo.ParticipantEquipe;
import com.example.lo52_project_v2.model.bo.Tour;

@Database(entities = {Participant.class, Equipe.class, Course.class, Tour.class, ParticipantEquipe.class}, exportSchema = false, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase instance;

    public static synchronized AppDataBase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDataBase.class, "CODEP25F1").allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract ParticipantDao participantDao();
    public abstract EquipeDao equipeDao();
    public abstract CourseDao courseDao();
    public abstract TourDao tourDao();
    public abstract ParticipantEquipeDao participantEquipeDao();
}
