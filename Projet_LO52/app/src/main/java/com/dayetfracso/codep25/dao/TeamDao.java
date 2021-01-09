package com.dayetfracso.codep25.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;


import com.dayetfracso.codep25.entity.Runner;
import com.dayetfracso.codep25.entity.Team;
import com.dayetfracso.codep25.entity.TeamWithRunners;

import java.util.List;

@Dao
public interface TeamDao {
    @Insert
    long insertTeam(Team team);

    @Transaction
    @Query("SELECT * FROM teams")
    public List<Team> getAllTeams();

    @Transaction
    @Query("DELETE FROM teams")
    public void deleteAllTeams();

    @Transaction
    @Query("SELECT * FROM teams")
    public List<TeamWithRunners> getTeamsWithRunners();

    @Transaction
    @Query("SELECT * FROM teams where teamId = :teamId")
    public Team getTeam(long teamId);

    @Transaction
    @Query("SELECT * FROM teams where teamId = :teamId")
    public TeamWithRunners getTeamWithRunners(long teamId);
}
