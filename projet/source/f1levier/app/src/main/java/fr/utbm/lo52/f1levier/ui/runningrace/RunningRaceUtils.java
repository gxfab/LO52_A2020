package fr.utbm.lo52.f1levier.ui.runningrace;


import android.os.AsyncTask;

import java.util.List;

import fr.utbm.lo52.f1levier.db.dao.QueryResult.TeamNameAndMemberIds;
import fr.utbm.lo52.f1levier.viewmodel.RunningRaceViewModel;

public class RunningRaceUtils {

    interface TeamGetHandler {
        void onTeamGetPostExecute(List<TeamNameAndMemberIds> teams);
    }

    static class TeamsGetAsyncTask extends AsyncTask<Integer, Void, List<TeamNameAndMemberIds>> {

        private RunningRaceViewModel runningRaceViewModel;

        private TeamGetHandler handler;

        TeamsGetAsyncTask(RunningRaceViewModel runningRaceViewModel, TeamGetHandler handler) {
            this.runningRaceViewModel = runningRaceViewModel;
            this.handler = handler;
        }

        @Override
        protected List<TeamNameAndMemberIds> doInBackground(Integer... params) {
            return runningRaceViewModel.getTeamNamesAndMemberIds(params[0]);
        }

        @Override
        protected void onPostExecute(List<TeamNameAndMemberIds> teams) {
            handler.onTeamGetPostExecute(teams);
        }
    }
}
