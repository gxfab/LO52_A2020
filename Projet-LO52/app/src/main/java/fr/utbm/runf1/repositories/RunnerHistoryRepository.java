package fr.utbm.runf1.repositories;

import fr.utbm.runf1.database.dao.RunnerHistoryDao;
import fr.utbm.runf1.entities.RunnerHistory;

/**
 * Created by Yosef B.I.
 */
public class RunnerHistoryRepository {
    private final RunnerHistoryDao runnerHistoryDao;

    public RunnerHistoryRepository(RunnerHistoryDao runnerHistoryDao) {
        this.runnerHistoryDao = runnerHistoryDao;
    }

    public void insertRunnerHistory(RunnerHistory runnerHistory) {
        this.runnerHistoryDao.insertRunnerHistory(runnerHistory);
    }

    public void clearTable() {
        this.runnerHistoryDao.clearTable();
    }
}
