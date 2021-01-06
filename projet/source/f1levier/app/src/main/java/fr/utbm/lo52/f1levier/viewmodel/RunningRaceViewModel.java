package fr.utbm.lo52.f1levier.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import fr.utbm.lo52.f1levier.db.dao.QueryResult.TeamNameAndMemberIds;
import fr.utbm.lo52.f1levier.db.entity.MeasuredTime;
import fr.utbm.lo52.f1levier.db.entity.Race;
import fr.utbm.lo52.f1levier.repository.MeasuredTimeRepository;
import fr.utbm.lo52.f1levier.repository.RaceRepository;
import fr.utbm.lo52.f1levier.repository.TeamRepository;

public class RunningRaceViewModel extends AndroidViewModel {

    private TeamRepository mTeamRepository;
    private RaceRepository mRaceRepository;
    private MeasuredTimeRepository mMeasuredTimeRepository;

    public RunningRaceViewModel(Application application) {
        super(application);
        mTeamRepository = new TeamRepository(application);
        mRaceRepository = new RaceRepository(application);
        mMeasuredTimeRepository = new MeasuredTimeRepository(application);
    }

    public List<TeamNameAndMemberIds> getTeamNamesAndMemberIds(int raceId) {
        return mTeamRepository.getTeamNamesAndMemberIds(raceId);
    }

    public void insertMeasuredTimeAsync(MeasuredTime measuredTime) {
        mMeasuredTimeRepository.insertAsync(measuredTime);
    }

    public void startRaceAsync(int raceId) {
        mRaceRepository.startRaceAsync(raceId);
    }

    public void finishRaceSync(int raceId) {
        mRaceRepository.finishRaceSync(raceId);
    }
}
