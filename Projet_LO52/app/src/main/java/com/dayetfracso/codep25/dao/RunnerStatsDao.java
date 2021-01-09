package com.dayetfracso.codep25.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.dayetfracso.codep25.entity.RunnerStats;

import java.util.List;


@Dao
public interface RunnerStatsDao {
    @Insert
    long insertRunnerStats(RunnerStats runnerStats);

    @Transaction
    @Query("DELETE FROM runners_stats")
    public void deleteAllRunnerStats();

    @Transaction
    @Query("SELECT * FROM runners_stats where runnerId = :runnerId")
    public RunnerStats getRunnerStats(long runnerId);

    @Transaction
    @Query("SELECT * FROM runners_stats where runnerId = :runnerId")
    public List<RunnerStats> getAllRunnerStats(long runnerId);

    @Transaction
    @Query("SELECT * FROM runners_stats natural join runner where runner.teamIdFk = :teamId AND raceId = :raceId")
    public List<RunnerStats> getTeamRunnersStatsOnRace(long teamId, long raceId);


    @Transaction
    @Query("SELECT * FROM runners_stats natural join runner where runner.runnerId = :runnerId AND raceId = :raceId")
    public RunnerStats getRunnerStatsOnRace(long runnerId, long raceId);

//    @Transaction
//    @Query("SELECT * FROM runners_stats")
//    public List<RunnerStatsWithTeams> getRunnerStatsWithTeams();
//
//    @Transaction
//    @Query("SELECT * FROM runners_stats where runnerId = :runnerId")
//    public List<RunnerStatsWithTeams> getRunnerStatsWithRunnerStatss(long runnerId);
}
