package fr.utbm.runf1.view.statistics;

import java.util.List;
import java.util.concurrent.Executor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import fr.utbm.runf1.entities.Race;
import fr.utbm.runf1.entities.relations.TeamWithRunnersWithHistory;
import fr.utbm.runf1.repositories.RaceRepository;
import fr.utbm.runf1.repositories.RunnerHistoryRepository;
import fr.utbm.runf1.repositories.RunnerRepository;
import fr.utbm.runf1.repositories.TeamRepository;

/**
 * Created by Yosef B.I.
 */
public class StatisticsViewModel extends ViewModel {

    private final RunnerRepository runnerRepository;
    private final RunnerHistoryRepository runnerHistoryRepository;
    private final TeamRepository teamRepository;
    private final RaceRepository raceRepository;
    private final Executor executor;

    public StatisticsViewModel(RunnerRepository runnerRepository, RunnerHistoryRepository runnerHistoryRepository, TeamRepository teamRepository, RaceRepository raceRepository, Executor executor) {
        this.runnerRepository = runnerRepository;
        this.runnerHistoryRepository = runnerHistoryRepository;
        this.teamRepository = teamRepository;
        this.raceRepository = raceRepository;
        this.executor = executor;
    }

    public LiveData<List<TeamWithRunnersWithHistory>> getAllTeamsWithRunners() {
        return this.teamRepository.getAllTeamsWithRunnersWithHistory();
    }

    public LiveData<List<Race>> getAllRaces() {
        return this.raceRepository.getAllRaces();
    }
}
