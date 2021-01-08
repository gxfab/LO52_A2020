package fr.utbm.runf1.repositories;

import java.util.List;

import androidx.lifecycle.LiveData;
import fr.utbm.runf1.database.dao.TeamDao;
import fr.utbm.runf1.entities.Team;
import fr.utbm.runf1.entities.relations.TeamWithRunners;
import fr.utbm.runf1.entities.relations.TeamWithRunnersWithHistory;

/**
 * Created by Yosef B.I.
 */
public class TeamRepository {

    private final TeamDao teamDao;

    public TeamRepository(TeamDao teamDao) {
        this.teamDao = teamDao;
    }

    public void deleteTeam(List<Integer> teamId) {
        this.teamDao.deleteTeam(teamId);
    }

    public LiveData<TeamWithRunners> getRunnersInTeam(int teamId) {
        return this.teamDao.getRunnersInTeam(teamId);
    }

    public LiveData<List<TeamWithRunners>> getAllTeamsWithRunners() {
        return this.teamDao.getAllTeamsWithRunners();
    }

    public LiveData<List<TeamWithRunnersWithHistory>> getAllTeamsWithRunnersWithHistory() {
        return this.teamDao.getAllTeamsWithRunnersWithHistory();
    }

    public void clearTable() {
        this.teamDao.clearTable();
    }

    public void insertTeamWithRunners(List<TeamWithRunners> teamWithRunners) {
        this.teamDao.insertTeamWithRunners(teamWithRunners);
    }
}
