package fr.utbm.runf1.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import fr.utbm.runf1.database.dao.RaceDao;
import fr.utbm.runf1.database.dao.RunnerDao;
import fr.utbm.runf1.database.dao.RunnerHistoryDao;
import fr.utbm.runf1.database.dao.TeamDao;
import fr.utbm.runf1.entities.Race;
import fr.utbm.runf1.entities.Runner;
import fr.utbm.runf1.entities.RunnerHistory;
import fr.utbm.runf1.entities.Team;

/**
 * Created by Yosef B.I.
 */
@Database(entities = {Runner.class, Team.class, RunnerHistory.class, Race.class}, version = 1, exportSchema = false)
public abstract class ApplicationDatabase extends RoomDatabase {

    private static volatile ApplicationDatabase INSTANCE;

    public abstract RunnerDao runnerDao();
    public abstract TeamDao teamDao();
    public abstract RunnerHistoryDao runnerHistoryDao();
    public abstract RaceDao raceDao();

    public static ApplicationDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ApplicationDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ApplicationDatabase.class, "MyDatabase.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
