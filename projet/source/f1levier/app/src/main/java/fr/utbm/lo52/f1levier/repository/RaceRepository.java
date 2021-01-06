package fr.utbm.lo52.f1levier.repository;


import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.Date;
import java.util.List;

import fr.utbm.lo52.f1levier.db.AppDatabase;
import fr.utbm.lo52.f1levier.db.dao.RaceDao;
import fr.utbm.lo52.f1levier.db.entity.Race;

public class RaceRepository {

    private RaceDao mRaceDao;

    private LiveData<List<Race>> mNotStartedRaces;

    private LiveData<List<Race>> mFinishedRaces;

    public RaceRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mRaceDao = db.raceDao();
        mNotStartedRaces = mRaceDao.getAllNotStarted();
        mFinishedRaces = mRaceDao.getAllFinished();
    }

    public LiveData<List<Race>> getNotStartedRaces() {
        return mNotStartedRaces;
    }

    public LiveData<List<Race>> getFinishedRaces() {
        return mFinishedRaces;
    }

    public Long insertSync(Race race) {
        return mRaceDao.insert(race);
    }

    public void startRaceAsync(int raceId) {
        new startRaceAsyncTask(mRaceDao).execute(raceId);
    }

    public void finishRaceSync(int raceId) {
        mRaceDao.finishRace(raceId, new Date());
    }

    private static class startRaceAsyncTask extends AsyncTask<Integer, Void, Void> {

        private RaceDao mAsyncTaskDao;

        startRaceAsyncTask(RaceDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Integer... params) {
            mAsyncTaskDao.startRace(params[0], new Date());
            return null;
        }
    }
}
