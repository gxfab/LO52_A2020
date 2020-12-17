package BDD.roomDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import BDD.entity.Runner;
import BDD.interfaceDAO.RunnerDao;

@Database(entities =  {Runner.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase
{
    // Singleton
    private static AppDataBase instance;
    public abstract RunnerDao runnerDao();

    /**
     * Get the application database instance
     * @param context The application context
     * @return The database instance
     */
    public static synchronized AppDataBase getInstance(Context context){
        if(instance == null)
        {
            //TODO: Remove allowMainThreadQueries, because it can freeze UI on big queries (but normally not on this project). Better to call queries outside of the UI thread.
            instance  = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "app_db").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }
        return instance;
    }
}
