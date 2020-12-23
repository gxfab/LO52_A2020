package BDD.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

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
