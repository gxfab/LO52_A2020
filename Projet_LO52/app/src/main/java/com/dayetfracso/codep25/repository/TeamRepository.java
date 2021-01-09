package com.dayetfracso.codep25.repository;

import android.content.Context;
import android.os.AsyncTask;

import com.dayetfracso.codep25.dao.AppDatabase;
import com.dayetfracso.codep25.dao.RunnerDao;
import com.dayetfracso.codep25.dao.TeamDao;
import com.dayetfracso.codep25.entity.Runner;
import com.dayetfracso.codep25.entity.Team;
import com.dayetfracso.codep25.entity.TeamWithRunners;

import java.util.List;

public class TeamRepository {
    private TeamDao teamDao;
    private RunnerDao runnerDao;

    public TeamRepository(Context context) {
        AppDatabase database = AppDatabase.getDatabase(context);
        teamDao = database.teamDao();
        runnerDao = database.runnerDao();
    }

    public void insert(List<Runner> runners) {
        new insertAsync(teamDao,runnerDao).execute(runners);
    }

    private static class insertAsync extends AsyncTask<List<Runner>, Void, Void> {
        private TeamDao teamDaoAsync;
        private RunnerDao runnerDaoAsync;

        insertAsync(TeamDao teamDao, RunnerDao runnerDao) {
            teamDaoAsync = teamDao;
            runnerDaoAsync = runnerDao;
        }

        @Override
        protected Void doInBackground(List<Runner>... runners) {

            long identifier = teamDaoAsync.insertTeam(new Team());

            for (Runner runner : runners[0]) {
                runner.setTeamIdFk(identifier);
                runnerDaoAsync.updateRunner(runner);
            }

            return null;
        }

    }
}