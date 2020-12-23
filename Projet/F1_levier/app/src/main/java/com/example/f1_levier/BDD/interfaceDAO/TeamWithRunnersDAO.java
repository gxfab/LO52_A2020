package com.example.f1_levier.BDD.interfaceDAO;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import com.example.f1_levier.BDD.entity.Runner;

@Dao
public interface TeamWithRunnersDAO
{
    @Transaction
    @Query("SELECT * FROM Team")
    public List<Runner> runnerList();
}
