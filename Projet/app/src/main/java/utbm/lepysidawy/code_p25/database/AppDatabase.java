package utbm.lepysidawy.code_p25.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import utbm.lepysidawy.code_p25.dao.ParticipateRaceDAO;
import utbm.lepysidawy.code_p25.dao.RaceDAO;
import utbm.lepysidawy.code_p25.dao.RunnerDAO;
import utbm.lepysidawy.code_p25.entity.ParticipateRace;
import utbm.lepysidawy.code_p25.entity.Race;
import utbm.lepysidawy.code_p25.entity.Runner;

/**
 * Singleton class for the database
 */
@Database(entities = {Runner.class, Race.class, ParticipateRace.class}, version = 6)
public abstract class AppDatabase extends RoomDatabase {

    public abstract RunnerDAO runnerDAO();
    public abstract RaceDAO raceDAO();
    public abstract ParticipateRaceDAO participateRaceDAO();

    private static AppDatabase instance;

    /**
     * Get the application database instance
     * @param context The application context
     * @return The database instance
     */
    public static synchronized AppDatabase getInstance(Context context){
        if(instance == null){
            //TODO: Remove allowMainThreadQueries, because it can freeze UI on big queries (but normally not on this project). Better to call queries outside of the UI thread.
            instance  = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app_db").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }
        return instance;
    }
}
