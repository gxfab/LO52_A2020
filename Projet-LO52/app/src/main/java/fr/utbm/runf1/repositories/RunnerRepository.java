package fr.utbm.runf1.repositories;

import java.util.List;

import androidx.lifecycle.LiveData;
import fr.utbm.runf1.database.dao.RunnerDao;
import fr.utbm.runf1.entities.Runner;
import fr.utbm.runf1.entities.relations.RunnerWithHistory;
import fr.utbm.runf1.entities.relations.TeamWithRunners;

/**
 * Created by Yosef B.I.
 */
public class RunnerRepository {

    private final RunnerDao runnerDao;

    public RunnerRepository(RunnerDao runnerDao) {
        this.runnerDao = runnerDao;
    }

    public void insertRunner(Runner runner) {
        this.runnerDao.insertRunner(runner);
    }

    public void updateRunner(Runner runner) {
        this.runnerDao.updaterunner(runner);
    }

    public void deleteRunner(int runnerId) {
        this.runnerDao.deleteRunner(runnerId);
    }

    public LiveData<List<Runner>> getAllRunners() {
        return this.runnerDao.getAllRunners();
    }

    public void clearTable() {
        this.runnerDao.clearTable();
    }

    /**
     * Since {team_id, order_within_team} is a unique combination,
     * we first change the order to some other number, to avoid clash,
     * and then update them again with the right "order_within_team" value
     * @param teamWithRunners
     */
    public void updateRunnersOrder(TeamWithRunners teamWithRunners, List<Runner> newRunnersOrder) {
        for(int i = 0; i < teamWithRunners.getRunners().size(); i++) {
            Runner tempRunner = new Runner(teamWithRunners.getRunners().get(i));
            tempRunner.setOrderWithinTeam(teamWithRunners.getRunners().size() + i + 1);
            this.runnerDao.updaterunner(tempRunner);
        }

        for(int i = 0; i < newRunnersOrder.size(); i++) {
            Runner tempRunner = new Runner(newRunnersOrder.get(i));
            tempRunner.setOrderWithinTeam(i);
            this.runnerDao.updaterunner(tempRunner);
        }
    }

    public LiveData<RunnerWithHistory> getRunnerWithHistory(int runnerId) {
        return this.runnerDao.getRunnerWithHistory(runnerId);
    }
}
