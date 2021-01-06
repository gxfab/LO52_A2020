package fr.utbm.lo52.f1levier.repository;


import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import fr.utbm.lo52.f1levier.db.AppDatabase;
import fr.utbm.lo52.f1levier.db.dao.MeasuredTimeDao;
import fr.utbm.lo52.f1levier.db.dao.QueryResult.ParticipantStat;
import fr.utbm.lo52.f1levier.db.dao.QueryResult.TeamStat;
import fr.utbm.lo52.f1levier.db.entity.MeasuredTime;

public class MeasuredTimeRepository {

    private MeasuredTimeDao mMeasuredTimeDao;

    public MeasuredTimeRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mMeasuredTimeDao = db.measuredTimeDao();
    }

    public void insertAsync(MeasuredTime measuredTime) {
        new insertAsyncTask(mMeasuredTimeDao).execute(measuredTime);
    }

    public List<TeamStat> getTeamStatsByRaceId(int raceId) {
        return mMeasuredTimeDao.getTeamStatsByRaceId(raceId);
    }

    public List<ParticipantStat> getParticipantStatsByRaceId(int raceId) {
        return mMeasuredTimeDao.getParticipantStatsByRaceId(raceId);
    }

    private static class insertAsyncTask extends AsyncTask<MeasuredTime, Void, Void> {

        private MeasuredTimeDao mAsyncTaskDao;

        insertAsyncTask(MeasuredTimeDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final MeasuredTime... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
