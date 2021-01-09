package com.dayetfracso.codep25.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dayetfracso.codep25.entity.Race;
import com.dayetfracso.codep25.entity.RaceTeamCrossRef;
import com.dayetfracso.codep25.entity.Runner;
import com.dayetfracso.codep25.entity.RunnerStats;
import com.dayetfracso.codep25.entity.Team;
import com.dayetfracso.codep25.entity.TeamWithRunners;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {
        Runner.class,
        Team.class,
        Race.class,
        RunnerStats.class,
        RaceTeamCrossRef.class
}, version = 1)

public abstract class AppDatabase extends RoomDatabase {
    public abstract RunnerDao runnerDao();
    public abstract TeamDao teamDao();
    public abstract RaceDao raceDao();
    public abstract RunnerStatsDao runnerStatsDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "codep25_database")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}