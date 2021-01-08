package fr.utbm.runf1.view.manage_order_in_team;

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
public class ManageOrderInTeamViewModel  extends ViewModel {

    private final RunnerRepository runnerRepository;
    private final TeamRepository teamRepository;
    private final Executor executor;

    public ManageOrderInTeamViewModel(RunnerRepository runnerRepository, TeamRepository teamRepository, Executor executor) {
        this.runnerRepository = runnerRepository;
        this.teamRepository = teamRepository;
        this.executor = executor;
    }

    public LiveData<TeamWithRunners> getTeamWithRunners(int teamId) {
        return this.teamRepository.getRunnersInTeam(teamId);
    }

    public void updateRunnersOrder(TeamWithRunners teamWithRunners, List<Runner> newRunnersOrder) {
        executor.execute(() -> this.runnerRepository.updateRunnersOrder(teamWithRunners, newRunnersOrder));
    }

    public LiveData<TeamWithRunners> getRunnersInTeam(int teamId) {
        return this.teamRepository.getRunnersInTeam(teamId);
    }
}
