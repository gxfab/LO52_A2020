package com.dayetfracso.codep25.entity;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.dayetfracso.codep25.dao.AppDatabase;
import com.dayetfracso.codep25.repository.TeamRepository;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "teams")
public class Team {
    @PrimaryKey(autoGenerate = true)
    private int teamId;
    private String name;

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RunnerStats> getTeamsStatsInRace(Context context, long raceId){
        List<RunnerStats> lts = new ArrayList<>();
        List<RunnerStats> teamRunnersStats = AppDatabase.getDatabase(context).runnerStatsDao().getTeamRunnersStatsOnRace(this.teamId,raceId);
        return teamRunnersStats;
    }

    public long getGlobalTimeOnRace(Context context, long raceId){
        long globalTime = 0;
        for(RunnerStats runnerStats : getTeamsStatsInRace(context,raceId)){
            globalTime += runnerStats.getGlobalTime();
        }
        return globalTime;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean createTeams(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        List<Runner> runners = db.runnerDao().getAllRunners();
        db.teamDao().deleteAllTeams();

        runners.sort((runner1, runner2) -> {
            if (runner1.getLevel() == runner2.getLevel()) return 0;
            return runner1.getLevel() > runner2.getLevel() ? 1 : -1;
        });

        if (runners.size() % 3 == 0) {
            int i = runners.size() - 1;

            while (i > 0) {
                List<Runner> teamRunners = new ArrayList<Runner>();
                teamRunners.add(runners.get(i));
                runners.remove(i);

                teamRunners.add(runners.get(i - 1));
                runners.remove(i - 1);

                teamRunners.add(runners.get(0));
                runners.remove(0);

                i = runners.size() - 1;

                new TeamRepository(context).insert(teamRunners);
            }

            return true;
        }

        return false;
    }
}