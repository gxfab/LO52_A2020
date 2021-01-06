package fr.utbm.lo52.f1levier.viewmodel;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import fr.utbm.lo52.f1levier.db.dao.QueryResult.TeamDetail;
import fr.utbm.lo52.f1levier.db.dao.QueryResult.TeamMember;
import fr.utbm.lo52.f1levier.db.entity.Team;
import fr.utbm.lo52.f1levier.db.entity.TeamMemberJoin;
import fr.utbm.lo52.f1levier.repository.TeamRepository;

public class TeamViewModel extends AndroidViewModel {

    private TeamRepository mTeamRepository;

    public TeamViewModel(Application application) {
        super(application);
        mTeamRepository = new TeamRepository(application);
    }

    public LiveData<List<TeamDetail>> getAllDetailedTeamsByRaceId(int raceId) {
        return mTeamRepository.getAllDetailedTeamsByRaceId(raceId);
    }

    public LiveData<List<TeamMember>> getOtherMembersNotPickedByRaceId(int teamId, int raceId) {
        return mTeamRepository.getOtherMembersNotPickedByRaceId(teamId, raceId);
    }

    public Long insertSync(Team team) {
        return mTeamRepository.insertSync(team);
    }

    public void insertTeamMemberAsync(TeamMemberJoin teamMemberJoin) {
        mTeamRepository.insertTeamMemberAsync(teamMemberJoin);
    }

    public void deleteTeamMemberAsync(int teamId, int memberId) {
        mTeamRepository.deleteTeamMemberAsync(teamId, memberId);
    }

    public int checkConstraintsSync(int raceId) {
        return mTeamRepository.checkConstraintsSync(raceId);
    }
}
