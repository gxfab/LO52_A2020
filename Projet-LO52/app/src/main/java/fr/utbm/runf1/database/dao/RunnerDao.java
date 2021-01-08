package fr.utbm.runf1.database.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import fr.utbm.runf1.entities.Runner;
import fr.utbm.runf1.entities.relations.RunnerWithHistory;

/**
 * Created by Yosef B.I.
 */
@Dao
public abstract class RunnerDao {
    @Insert
    public abstract void insertRunner(Runner runner);

    @Update
    public abstract int updaterunner(Runner runner);

    @Query("DELETE FROM Runner WHERE runner_id = :runnerId")
    public abstract void deleteRunner(int runnerId);

    @Query("SELECT * FROM Runner r WHERE r.runner_id = :runnerId")
    public abstract LiveData<Runner> getRunner(int runnerId);

    @Query("SELECT * FROM Runner")
    public abstract LiveData<List<Runner>> getAllRunners();

    @Query("DELETE FROM Runner; ")
    public abstract void clearTable();

    @Transaction
    @Query("SELECT * FROM Runner r WHERE r.runner_id = :runnerId")
    public abstract LiveData<RunnerWithHistory> getRunnerWithHistory(int runnerId);
}
