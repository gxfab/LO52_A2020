package fr.utbm.lo52.f1levier.db.dao;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import fr.utbm.lo52.f1levier.db.dao.QueryResult.TeamDetail;
import fr.utbm.lo52.f1levier.db.entity.Team;

@Dao
public interface TeamDao {

    @Query("SELECT * FROM Team WHERE race_id = :raceId ORDER BY name")
    LiveData<List<Team>> getAllByRaceId(final int raceId);

    @Query("SELECT Team.*, COUNT(Participant.id) AS membersCount, " +
            "SUM(Participant.echelon) AS echelonsSum FROM Team " +
            "LEFT JOIN team_member ON Team.id = team_member.team_id " +
            "LEFT JOIN Participant ON Participant.id = team_member.member_id " +
            "WHERE Team.race_id = :raceId " +
            "GROUP BY Team.id " +
            "ORDER BY Team.name")
    LiveData<List<TeamDetail>> getAllDetailedByRaceId(final int raceId);

    @Insert
    Long insert(Team team);

    @Query("SELECT COUNT(*) FROM Team LEFT JOIN team_member ON Team.id = team_member.team_id " +
            "WHERE Team.race_id = :raceId GROUP BY Team.id HAVING COUNT(team_member.member_id) <> 3")
    int checkConstraints(int raceId);
}
