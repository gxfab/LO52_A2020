package fr.utbm.runf1.injection;

import java.util.concurrent.Executor;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import fr.utbm.runf1.repositories.RaceRepository;
import fr.utbm.runf1.repositories.RunnerHistoryRepository;
import fr.utbm.runf1.repositories.RunnerRepository;
import fr.utbm.runf1.repositories.TeamRepository;
import fr.utbm.runf1.view.manage_order_in_team.ManageOrderInTeamViewModel;
import fr.utbm.runf1.view.manage_runners.ManageRunnersViewModel;
import fr.utbm.runf1.view.manage_teams.ManageTeamsViewModel;
import fr.utbm.runf1.view.statistics.StatisticsViewModel;
import fr.utbm.runf1.view.time_runners.TimeRunnersViewModel;

/**
 * Created by Yosef B.I.
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    private final RunnerRepository runnerRepository;
    private final TeamRepository teamRepository;
    private RaceRepository raceRepository;
    private RunnerHistoryRepository runnerHistoryRepository;
    private final Executor executor;

    public ViewModelFactory(RunnerRepository runnerRepository, TeamRepository teamRepository, RaceRepository raceRepository, RunnerHistoryRepository runnerHistoryRepository, Executor executor) {
        this.runnerRepository = runnerRepository;
        this.teamRepository = teamRepository;
        this.raceRepository = raceRepository;
        this.runnerHistoryRepository = runnerHistoryRepository;
        this.executor = executor;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ManageRunnersViewModel.class)) {
            return (T) new ManageRunnersViewModel(runnerRepository, teamRepository, raceRepository, runnerHistoryRepository, executor);
        }
        if (modelClass.isAssignableFrom(ManageTeamsViewModel.class)) {
            return (T) new ManageTeamsViewModel(runnerRepository, teamRepository, executor);
        }
        if (modelClass.isAssignableFrom(ManageOrderInTeamViewModel.class)) {
            return (T) new ManageOrderInTeamViewModel(runnerRepository, teamRepository, executor);
        }
        if (modelClass.isAssignableFrom(TimeRunnersViewModel.class)) {
            return (T) new TimeRunnersViewModel(runnerRepository, runnerHistoryRepository, teamRepository, raceRepository, executor);
        }
        if (modelClass.isAssignableFrom(StatisticsViewModel.class)) {
            return (T) new StatisticsViewModel(runnerRepository, runnerHistoryRepository, teamRepository, raceRepository, executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
