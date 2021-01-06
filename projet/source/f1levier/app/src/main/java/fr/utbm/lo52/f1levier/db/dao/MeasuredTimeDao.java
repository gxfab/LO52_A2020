package fr.utbm.lo52.f1levier.db.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import fr.utbm.lo52.f1levier.db.dao.QueryResult.ParticipantStat;
import fr.utbm.lo52.f1levier.db.dao.QueryResult.TeamStat;
import fr.utbm.lo52.f1levier.db.entity.MeasuredTime;

@Dao
public interface MeasuredTimeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MeasuredTime measuredTime);

    @Query("SELECT Team.id, Team.name, SUM(measured_time.measuredTime) AS time " +
            "FROM Team INNER JOIN team_member ON Team.id = team_member.team_id " +
            "INNER JOIN Participant ON team_member.member_id = Participant.id " +
            "INNER JOIN measured_time ON Participant.id = measured_time.participant_id " +
            "WHERE Team.race_id = :raceId " +
            "GROUP BY Team.id " +
            "ORDER BY SUM(measured_time.measuredTime)")
    List<TeamStat> getTeamStatsByRaceId(int raceId);

    @Query("SELECT Participant.id, Participant.name, Team.name as team, " +
            "SUM(measured_time.measuredTime) AS time " +
            "FROM Team INNER JOIN team_member ON Team.id = team_member.team_id " +
            "INNER JOIN Participant ON team_member.member_id = Participant.id " +
            "INNER JOIN measured_time ON Participant.id = measured_time.participant_id " +
            "WHERE Team.race_id = :raceId " +
            "GROUP BY Participant.id " +
            "ORDER BY SUM(measured_time.measuredTime)")
    List<ParticipantStat> getParticipantStatsByRaceId(int raceId);
}
