package fr.utbm.lo52.f1levier.db.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import fr.utbm.lo52.f1levier.db.dao.QueryResult.TeamMember;
import fr.utbm.lo52.f1levier.db.dao.QueryResult.TeamNameAndMemberIds;
import fr.utbm.lo52.f1levier.db.entity.TeamMemberJoin;

@Dao
public interface TeamMemberJoinDao {

    @Query("SELECT * FROM Team  WHERE race_id = :raceId ORDER BY name")
    List<TeamNameAndMemberIds> getTeamNamesAndMemberIds(final int raceId);

    @Query("SELECT Participant.*, 1 AS picked FROM Participant INNER JOIN team_member ON " +
            "Participant.id = team_member.member_id WHERE team_member.team_id = :teamId " +
            "UNION " +
            "SELECT DISTINCT Participant.*, 0 AS picked FROM Participant " +
            "WHERE Participant.id NOT IN " +
            "(SELECT Participant.id FROM Participant " +
            "INNER JOIN team_member ON Participant.id = team_member.member_id " +
            "INNER JOIN Team ON Team.id = team_member.team_id " +
            "WHERE team.race_id = :raceId) " +
            "ORDER BY name"
    )
    LiveData<List<TeamMember>> getOtherMembersNotPickedByRaceId(final int teamId, final int raceId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TeamMemberJoin teamMemberJoin);

    @Query("DELETE FROM team_member " +
            "WHERE team_member.team_id = :teamId " +
            "AND team_member.member_id = :memberId")
    void delete(final int teamId, final int memberId);
}
