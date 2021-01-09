package com.dayetfracso.codep25.entity;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

import java.util.List;

public class TeamWithRunners {
    @Embedded
    public Team team;

    @Relation(
            parentColumn = "teamId",
            entityColumn = "teamIdFk",
            entity = Runner.class
    )
    public List<Runner> runners;
}
