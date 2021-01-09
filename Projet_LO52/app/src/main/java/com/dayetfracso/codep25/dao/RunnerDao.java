package com.dayetfracso.codep25.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.dayetfracso.codep25.entity.Runner;

import java.util.List;

@Dao
public interface RunnerDao {
    @Insert
    void insertAll(Runner runner);

    @Insert
    long insertRunner(Runner runner);

    @Update
    public void updateRunner(Runner runner);

    @Query("SELECT * FROM runner")
    List<Runner> getAllRunners();

    @Delete
    void delete(Runner runner);
}

//@Query("SELECT * FROM coureur")
//List<Coureur> getAll();

//@Query("SELECT * FROM coureur WHERE uid IN (:coureurIds)")
//List<Coureur> loadAllByIds(int[] coureurIds);

//@Query("SELECT * FROM coureur WHERE first_name LIKE :first AND " +    "last_name LIKE :last LIMIT 1")
//Coureur findByName(String first, String last);
