package fr.utbm.runf1.view.manage_runners;

import java.util.List;
import java.util.concurrent.Executor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import fr.utbm.runf1.entities.Race;
import fr.utbm.runf1.entities.Runner;
import fr.utbm.runf1.entities.RunnerHistory;
import fr.utbm.runf1.entities.Team;
import fr.utbm.runf1.entities.relations.RunnerWithHistory;
import fr.utbm.runf1.entities.relations.TeamWithRunners;
import fr.utbm.runf1.repositories.RaceRepository;
import fr.utbm.runf1.repositories.RunnerHistoryRepository;
import fr.utbm.runf1.repositories.RunnerRepository;
import fr.utbm.runf1.repositories.TeamRepository;

/**
 * Created by Yosef B.I.
 */
public class ManageRunnersViewModel extends ViewModel {

    private final RunnerRepository runnerRepository;
    private final TeamRepository teamRepository;
    private final RaceRepository raceRepository;
    private final RunnerHistoryRepository runnerHistoryRepository;
    private final Executor executor;

    public ManageRunnersViewModel(RunnerRepository runnerRepository, TeamRepository teamRepository, RaceRepository raceRepository, RunnerHistoryRepository runnerHistoryRepository, Executor executor) {
        this.runnerRepository = runnerRepository;
        this.teamRepository = teamRepository;
        this.raceRepository = raceRepository;
        this.runnerHistoryRepository = runnerHistoryRepository;
        this.executor = executor;
    }

    public void insertRunner(Runner runner) {
        executor.execute(() -> this.runnerRepository.insertRunner(runner));
    }

    public void updateRunner(Runner runner) {
        executor.execute(() -> this.runnerRepository.updateRunner(runner));
    }

    public LiveData<List<Runner>> getAllRunners() {
        return this.runnerRepository.getAllRunners();
    }

    public void deleteRunner(int runnerId) {
        executor.execute(() -> this.runnerRepository.deleteRunner(runnerId));
    }

    public void clearRunnerTable() {
        executor.execute(this.runnerRepository::clearTable);
        executor.execute(this.runnerHistoryRepository::clearTable);
        executor.execute(this.raceRepository::clearTable);
    }

    public void clearTeamTable() {
        executor.execute(this.teamRepository::clearTable);
    }

    public LiveData<RunnerWithHistory> getRunnerWithHistory(int runnerId) {
        return this.runnerRepository.getRunnerWithHistory(runnerId);
    }
}
