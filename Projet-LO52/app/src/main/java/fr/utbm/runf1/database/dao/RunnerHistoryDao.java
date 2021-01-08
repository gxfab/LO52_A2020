package fr.utbm.runf1.database.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import fr.utbm.runf1.entities.RunnerHistory;

/**
 * Created by Yosef B.I.
 */
@Dao
public interface RunnerHistoryDao {
    @Insert
    void insertRunnerHistory(RunnerHistory runnerHistory);

    @Update
    void updateRunnerHistory(RunnerHistory runnerHistory);

    @Query("SELECT * FROM RunnerHistory r WHERE r.id = :runnerHistoryId")
    RunnerHistory getRunnerHistory(int runnerHistoryId);

    @Query("SELECT * FROM RunnerHistory")
    List<RunnerHistory> getAllRunnerHistory();

    @Query("DELETE FROM RunnerHistory;")
    void clearTable();
}
