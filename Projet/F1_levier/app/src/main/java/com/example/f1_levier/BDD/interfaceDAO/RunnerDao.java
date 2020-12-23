package com.example.f1_levier.BDD.interfaceDAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;

import com.example.f1_levier.BDD.entity.Runner;

@Dao
public interface RunnerDao {
    @Insert
    void insertAll(Runner... runners);

    @Delete
    void delete(Runner runner);
}
