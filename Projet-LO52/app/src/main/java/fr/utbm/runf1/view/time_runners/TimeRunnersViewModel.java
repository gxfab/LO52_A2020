package fr.utbm.runf1.view.time_runners;

import java.util.List;
import java.util.concurrent.Executor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import fr.utbm.runf1.entities.Race;
import fr.utbm.runf1.entities.Runner;
import fr.utbm.runf1.entities.RunnerHistory;
import fr.utbm.runf1.entities.relations.TeamWithRunners;
import fr.utbm.runf1.entities.relations.TeamWithRunnersWithHistory;
import fr.utbm.runf1.repositories.RaceRepository;
import fr.utbm.runf1.repositories.RunnerHistoryRepository;
import fr.utbm.runf1.repositories.RunnerRepository;
import fr.utbm.runf1.repositories.TeamRepository;

/**
 * Created by Yosef B.I.
 */
public class TimeRunnersViewModel extends ViewModel {

    private final RunnerRepository runnerRepository;
    private final RunnerHistoryRepository runnerHistoryRepository;
    private final TeamRepository teamRepository;
    private final RaceRepository raceRepository;
    private final Executor executor;

    public TimeRunnersViewModel(RunnerRepository runnerRepository, RunnerHistoryRepository runnerHistoryRepository, TeamRepository teamRepository, RaceRepository raceRepository, Executor executor) {
        this.runnerRepository = runnerRepository;
        this.runnerHistoryRepository = runnerHistoryRepository;
        this.teamRepository = teamRepository;
        this.raceRepository = raceRepository;
        this.executor = executor;
    }

    public void insertRunnerHistory(RunnerHistory runnerHistory) {
        executor.execute(() -> this.runnerHistoryRepository.insertRunnerHistory(runnerHistory));
    }

    public LiveData<List<TeamWithRunnersWithHistory>> getAllTeamsWithRunnersWithHistory() {
        return this.teamRepository.getAllTeamsWithRunnersWithHistory();
    }

    public void insertRace(Race race) {
        executor.execute(() -> this.raceRepository.insertRace(race));
    }

    public LiveData<Race> getLastInsertedRace() {
        return this.raceRepository.getLastInsertedRace();
    }
}
