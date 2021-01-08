package fr.utbm.runf1.database.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import fr.utbm.runf1.entities.Runner;
import fr.utbm.runf1.entities.Team;
import fr.utbm.runf1.entities.relations.TeamWithRunners;
import fr.utbm.runf1.entities.relations.TeamWithRunnersWithHistory;

/**
 * Created by Yosef B.I.
 */
@Dao
public abstract class TeamDao{

    public void insertTeamWithRunners(TeamWithRunners teamWithRunners) {
        long teamId = insertTeam(teamWithRunners.getTeam());

        for (Runner runner : teamWithRunners.getRunners()) {
            runner.setTeamId((int)teamId);
            updateRunner(runner);
        }
    }

    public void insertTeamWithRunners(List<TeamWithRunners> teamWithRunnersList) {
        for (TeamWithRunners teamWithRunners : teamWithRunnersList) {
            long teamId = insertTeam(teamWithRunners.getTeam());
            teamWithRunners.getRunners().forEach(r -> r.setTeamId((int)teamId));
            teamWithRunners.getRunners().forEach(this::updateRunner);
        }
    }

    @Update
    abstract public void updateRunner(List<Runner> runner);

    @Update
    abstract public void updateRunner(Runner runner);

    @Insert
    abstract public long insertTeam(Team team);

    @Update
    abstract public void updateTeam(Team team);

    @Query("DELETE FROM Team WHERE team_id = :teamId")
    abstract public void deleteTeam(int teamId);

    public void deleteTeam(List<Integer> teams) {
        teams.forEach(this::deleteTeam);
    }

    @Query("SELECT * FROM Team t WHERE t.team_id = :teamId")
    abstract public LiveData<Team> getTeam(int teamId);

    @Transaction
    @Query("SELECT * FROM Team t WHERE t.team_id = :teamId")
    abstract public LiveData<TeamWithRunners> getRunnersInTeam(int teamId);

    @Query("SELECT * FROM Team")
    abstract public LiveData<List<TeamWithRunners>> getAllTeamsWithRunners();

    @Query("DELETE FROM Team")
    abstract public void clearTable();

    @Query("SELECT * FROM Team ORDER BY team_id DESC LIMIT 1")
    abstract public LiveData<Team> getLastInsertedTeam();

    @Transaction
    @Query("SELECT * FROM Team")
    public abstract LiveData<List<TeamWithRunnersWithHistory>> getAllTeamsWithRunnersWithHistory();
}
