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

@Database(entities = {Runner.class, Team.class}, version = 7)
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
     * Return a team in a List<Team> based on its ID
     * @param teamList : List<Team>, the team list
     * @param id : int, the team ID
     * @return Team, the team with this ID
     */
    public Team getTeamFromId(List<Team> teamList, int id)
    {
        for(Team t : teamList)
        {
            if(t.getTeamId() ==  id)
                return t;
        }
        return null;
    }

    /**
     * Return information about the runner who got the best time in a part
     * @param runnerList : List<Runner> the runner list
     * @param teamList : List<Team> the list of teams
     * @param segment : int, the number of the segment we want the time of, from 1 to 5
     * @return List<String>: the ID of the best runner,
     *                       his last name,
     *                       his first name,
     *                       his time as n min s sec c cs
     */
    public List<String> getBestTimeAsString(List<Runner> runnerList, List<Team> teamList, int segment)
    {
        long minTime = 100000000;
        Runner bestRunner = null;
        for(Runner r : runnerList)
        {
            long time = -1;
            switch(segment)
            {
                case 1:
                    //Different way to calculate the time depending the position in the team
                    Team t = getTeamFromId(teamList, r.getTeamId());
                    if(r.getRunnerId() == t.getFirstRunnerId())
                        time = r.getTime1();
                    if(r.getRunnerId() == t.getSecondRunnerId())
                        time = r.getTime1() - getRunnerFromId(runnerList, t.getFirstRunnerId()).getTime5();
                    if(r.getRunnerId() == t.getThirdRunnerId())
                        time = r.getTime1() - getRunnerFromId(runnerList, t.getSecondRunnerId()).getTime5();
                    break;
                case 2:
                    time = r.getTime2() - r.getTime1();
                    break;
                case 3:
                    time = r.getTime3() - r.getTime2();
                    break;
                case 4:
                    time = r.getTime4() - r.getTime3();
                    break;
                case 5:
                    time = r.getTime5() - r.getTime4();
                    break;
            }
            if(time != -1 && time < minTime)
            {
                minTime = time;
                bestRunner = r;
            }
        }
//        System.out.println("time 1 : " + bestRunner.getTime2() + " -- time 2: " + bestRunner.getTime1());
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