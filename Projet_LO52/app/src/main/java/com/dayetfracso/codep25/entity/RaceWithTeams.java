package com.dayetfracso.codep25.entity;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class RaceWithTeams {
    @Embedded
    public Race race;

    @Relation(
            parentColumn = "raceId",
            entityColumn = "teamId",
            associateBy = @Junction(RaceTeamCrossRef.class)
    )
    public List<Team> teams;
}


