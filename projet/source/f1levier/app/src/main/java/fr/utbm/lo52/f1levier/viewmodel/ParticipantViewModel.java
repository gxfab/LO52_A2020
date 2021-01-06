package fr.utbm.lo52.f1levier.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import fr.utbm.lo52.f1levier.db.entity.Participant;
import fr.utbm.lo52.f1levier.repository.ParticipantRepository;

public class ParticipantViewModel extends AndroidViewModel {

    private ParticipantRepository mRepository;

    private LiveData<List<Participant>> mAllParticipants;

    public ParticipantViewModel(Application application) {
        super(application);
        mRepository = new ParticipantRepository(application);
        mAllParticipants = mRepository.getAllParticipants();
    }

    public LiveData<List<Participant>> getAllParticipants() {
        return mAllParticipants;
    }

    public void insertAsync(Participant participant) {
        mRepository.insertAsync(participant);
    }
}
