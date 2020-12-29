package com.example.f1_levier.BDD.interfaceDAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.f1_levier.BDD.entity.Runner;
import com.example.f1_levier.BDD.entity.Team;

import java.util.List;

@Dao
public interface TeamDAO
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertTeam(Team... teams);

    @Delete
    public void deleteTeam(Team team);

    @Update
    public void updateTeam(Team... teams);

    @Query("DELETE FROM Team")
    public void clearTeamTable();

}
