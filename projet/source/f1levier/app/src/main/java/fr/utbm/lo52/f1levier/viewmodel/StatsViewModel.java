package fr.utbm.lo52.f1levier.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import java.util.List;

import fr.utbm.lo52.f1levier.db.dao.QueryResult.ParticipantStat;
import fr.utbm.lo52.f1levier.db.dao.QueryResult.TeamStat;
import fr.utbm.lo52.f1levier.repository.MeasuredTimeRepository;

public class StatsViewModel extends AndroidViewModel {

    private MeasuredTimeRepository mMeasuredTimeRepository;

    public StatsViewModel(@NonNull Application application) {
        super(application);
        mMeasuredTimeRepository = new MeasuredTimeRepository(application);
    }

    public List<TeamStat> getTeamStatsByRaceId(int raceId) {
        return mMeasuredTimeRepository.getTeamStatsByRaceId(raceId);
    }

    public List<ParticipantStat> getParticipantStatsByRaceId(int raceId) {
        return mMeasuredTimeRepository.getParticipantStatsByRaceId(raceId);
    }
}
