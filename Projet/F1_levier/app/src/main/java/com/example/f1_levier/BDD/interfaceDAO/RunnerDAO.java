package com.example.f1_levier.BDD.interfaceDAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.f1_levier.BDD.entity.Runner;

import java.util.List;

@Dao
public interface RunnerDAO
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertRunner(Runner... runners);

    @Delete
    public void deleteRunner(Runner runner);

    @Update
    public void updateRunner(Runner... runners);

    @Query("SELECT COUNT(*) FROM Runner")
    public int getNumberOfRunners();

    @Query("SELECT * FROM Runner")
    public List<Runner> getAllRunner();

    @Query("SELECT * FROM Runner WHERE runnerId = :id")
    public Runner getRunnerFromID(int id);

    @Query("SELECT last_name FROM Runner WHERE runnerID = :id")
    public String getRunnerLastNameFromID(int id);

    @Query("SELECT first_name FROM Runner WHERE runnerID = :id")
    public String getRunnerFirstNameFromID(int id);

    @Query("SELECT level FROM Runner WHERE runnerId = :id")
    public int getRunnerLevelFromID(int id);

    @Query("DELETE FROM Runner")
    public void clearRunnerTable();
}
