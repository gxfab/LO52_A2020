package fr.utbm.runf1.injection;

import android.content.Context;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.utbm.runf1.database.ApplicationDatabase;
import fr.utbm.runf1.repositories.RaceRepository;
import fr.utbm.runf1.repositories.RunnerHistoryRepository;
import fr.utbm.runf1.repositories.RunnerRepository;
import fr.utbm.runf1.repositories.TeamRepository;

/**
 * Created by Yosef B.I.
 */
public class Injection {

    public static RunnerRepository provideItemDataSource(Context context) {
        ApplicationDatabase database = ApplicationDatabase.getInstance(context);
        return new RunnerRepository(database.runnerDao());
    }

    public static RunnerHistoryRepository provideRunnerHistoryDataSource(Context context) {
        ApplicationDatabase database = ApplicationDatabase.getInstance(context);
        return new RunnerHistoryRepository(database.runnerHistoryDao());
    }

    public static RaceRepository provideRaceDataSource(Context context) {
        ApplicationDatabase database = ApplicationDatabase.getInstance(context);
        return new RaceRepository(database.raceDao());
    }

    public static TeamRepository provideUserDataSource(Context context) {
        ApplicationDatabase database = ApplicationDatabase.getInstance(context);
        return new TeamRepository(database.teamDao());
    }

    public static Executor provideExecutor(){ return Executors.newSingleThreadExecutor(); }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        RunnerRepository dataSourceItem = provideItemDataSource(context);
        TeamRepository dataSourceUser = provideUserDataSource(context);
        RunnerHistoryRepository runnerHistoryRepository = provideRunnerHistoryDataSource(context);
        RaceRepository raceRepository = provideRaceDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceItem, dataSourceUser, raceRepository, runnerHistoryRepository, executor);
    }
}
