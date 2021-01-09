package com.dayetfracso.codep25.entity;

import androidx.room.Entity;

@Entity(primaryKeys = {"raceId", "teamId"})
public class RaceTeamCrossRef {
    public long raceId;
    public long teamId;

    public RaceTeamCrossRef() {}

    public RaceTeamCrossRef(long raceID, int teamId) {
        this.raceId = raceID;
        this.teamId = teamId;
    }
}
