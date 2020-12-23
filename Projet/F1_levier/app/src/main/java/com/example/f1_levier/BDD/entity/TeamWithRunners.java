package com.example.f1_levier.BDD.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.f1_levier.model.Team;

import java.util.List;

public class TeamWithRunners
{
    @Embedded private Team team;
    @Relation(
            parentColumn = "teamId",
            entityColumn = "isInTeamId"
    )
    private List<Runner> runnerList;
}
