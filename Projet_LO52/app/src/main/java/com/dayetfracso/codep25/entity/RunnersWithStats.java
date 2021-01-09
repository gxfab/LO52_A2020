package com.dayetfracso.codep25.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class RunnersWithStats {
    @Embedded
    public Runner runner;

    @Relation(
            parentColumn = "runnerId",
            entityColumn = "runnerId",
            entity = RunnerStats.class
    )
    public List<RunnerStats> runnerStats;
}
