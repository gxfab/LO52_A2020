package fr.utbm.lo52.f1levier.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import fr.utbm.lo52.f1levier.db.AppDatabase;
import fr.utbm.lo52.f1levier.db.dao.ParticipantDao;
import fr.utbm.lo52.f1levier.db.entity.Participant;

public class ParticipantRepository {

    private ParticipantDao mParticipantDao;

    private LiveData<List<Participant>> mAllParticipants;

    public ParticipantRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mParticipantDao = db.participantDao();
        mAllParticipants = mParticipantDao.getAll();
    }

    public LiveData<List<Participant>> getAllParticipants() {
        return mAllParticipants;
    }

    public void insertAsync(Participant participant) {
        new insertAsyncTask(mParticipantDao).execute(participant);
    }

    private static class insertAsyncTask extends AsyncTask<Participant, Void, Void> {

        private ParticipantDao mAsyncTaskDao;

        insertAsyncTask(ParticipantDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Participant... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
