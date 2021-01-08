package fr.utbm.runf1.entities.relations;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import androidx.room.Embedded;
import androidx.room.Relation;
import fr.utbm.runf1.entities.Runner;
import fr.utbm.runf1.entities.Team;

/**
 * Created by Yosef B.I.
 */
public class TeamWithRunners {
    @Embedded private Team team;
    @Relation(parentColumn = "team_id",
            entityColumn = "team_id",
            entity = Runner.class)
    private List<Runner> runners = new ArrayList<>();

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setRunners(List<Runner> runners) {
        this.runners = runners;
    }

    public List<Runner> getRunners() {
        this.runners = this.runners
                .stream()
                .sorted(Comparator.comparingInt(Runner::getOrderWithinTeam))
                .collect(Collectors.toList());
        return this.runners;
    }

    public int getTeamLevel() {
        return this.runners.stream().mapToInt(Runner::getLevel).sum();
    }
}
