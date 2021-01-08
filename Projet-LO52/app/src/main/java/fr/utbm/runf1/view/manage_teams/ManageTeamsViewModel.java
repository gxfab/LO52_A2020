package fr.utbm.runf1.view.manage_teams;

import java.util.List;
import java.util.concurrent.Executor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import fr.utbm.runf1.entities.Runner;
import fr.utbm.runf1.entities.relations.TeamWithRunners;
import fr.utbm.runf1.repositories.RunnerRepository;
import fr.utbm.runf1.repositories.TeamRepository;

/**
 * Created by Yosef B.I.
 */
public class ManageTeamsViewModel extends ViewModel {

    private final RunnerRepository runnerRepository;
    private final TeamRepository teamRepository;
    private final Executor executor;

    public ManageTeamsViewModel(RunnerRepository runnerRepository, TeamRepository teamRepository, Executor executor) {
        this.runnerRepository = runnerRepository;
        this.teamRepository = teamRepository;
        this.executor = executor;
    }

    public LiveData<List<TeamWithRunners>> getAllTeamsWithRunners() {
        return this.teamRepository.getAllTeamsWithRunners();
    }

    public LiveData<List<Runner>> getAllRunners() {
        return this.runnerRepository.getAllRunners();
    }


    public void insertTeamWithRunners(List<TeamWithRunners> teamWithRunners) {
        executor.execute(() -> this.teamRepository.insertTeamWithRunners(teamWithRunners));
    }

    public void deleteTeam(List<Integer> teamId) {
        executor.execute(() -> this.teamRepository.deleteTeam(teamId));
    }
}
