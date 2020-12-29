package com.example.f1_levier.BDD.roomDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.f1_levier.BDD.entity.Runner;
import com.example.f1_levier.BDD.entity.Team;
import com.example.f1_levier.BDD.interfaceDAO.RunnerDAO;
import com.example.f1_levier.BDD.interfaceDAO.TeamDAO;

import java.util.ArrayList;
import java.util.List;

@Database(entities = {Runner.class, Team.class}, version = 6)
@TypeConverters({Converters.class})
public abstract class AppDataBase extends RoomDatabase
{
    // Singleton
    private static AppDataBase instance;

    public abstract TeamDAO teamDAO();
    public abstract RunnerDAO runnerDAO();

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

    /**
     * Return a runner in a List<Runner> based on its ID
     * @param runnerList : List<Runner>, the runner list
     * @param id : int, the runner ID
     * @return Runner, the runner with this ID
     */
    public Runner getRunnerFromId(List<Runner> runnerList, int id)
    {
        for(Runner r : runnerList)
        {
            if(r.getRunnerId() == id)
                return r;
        }
        return null;
    }

    /**
     * Return information about the runner who got the best time in a part
     * @param runnerList : List<Runner> the runner list
     * @param segment : int, the number of the segment we want the time of, from 1 to 5
     * @return List<String>: the ID of the best runner,
     *                       his last name,
     *                       his first name,
     *                       his time as n min s sec c cs
     */
    public List<String> getBestTimeAsString(List<Runner> runnerList, int segment)
    {
        long minTime = 100000000;
        Runner bestRunner = null;
        for(Runner r : runnerList)
        {
            if(r.getTime(segment) != -1 && r.getTime(segment) < minTime)
            {
                minTime = r.getTime(segment);
                bestRunner = r;
            }
        }
        List<String> result = new ArrayList<String>();
        if(bestRunner != null)
        {
            result.add(String.valueOf(bestRunner.getRunnerId()));
            result.add(bestRunner.getLastName());
            result.add(bestRunner.getFirstName());
            int minutes = (int)(minTime/60000);
            int sec = (int)((minTime - minutes*60000))/1000;
            long ms = minTime%1000;
            String timeString = minutes + " min " + sec + " s " + ms + " ms";
            result.add(timeString);
        }
        else
        {
            result.add("not found");
            result.add("not found");
            result.add("not found");
            result.add("not found");
        }
        return result;
    }
}