package utbm.lepysidawy.code_p25.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import utbm.lepysidawy.code_p25.entity.Runner;

/**
 * DAO used to interact with the Runner table
 */
@Dao
public interface RunnerDAO {

    @Query("SELECT * FROM Runner")
    List<Runner> getAll();

    @Query("SELECT * FROM Runner WHERE Runner.idPlayer = :runnerId")
    Runner getRunnerFromId(int runnerId);

    @Insert
    long[] insertAll(Runner... runner);

    @Delete
    void delete(Runner runner);
}