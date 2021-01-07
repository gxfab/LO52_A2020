package utbm.lepysidawy.code_p25.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import utbm.lepysidawy.code_p25.entity.ParticipateRace;

/**
 * DAO used to interact with the ParticipateRace table
 */
@Dao
public interface ParticipateRaceDAO {

    @Query("SELECT * FROM ParticipateRace")
    List<ParticipateRace> getAll();

    @Query("SELECT * FROM ParticipateRace WHERE ParticipateRace.ID_RACE = :raceId AND ParticipateRace.TEAM_NUMBER = :teamNumber AND ParticipateRace.RUNNING_ORDER = :runningOrder")
    ParticipateRace getParticipationsFromCourseAndTeamNumberAndRunningOrder(int raceId, int teamNumber, int runningOrder);

    @Query("SELECT * FROM ParticipateRace WHERE ParticipateRace.ID_RACE = :raceId AND ParticipateRace.TEAM_NUMBER = :teamNumber")
    List<ParticipateRace> getParticipationsFromCourseAndTeamNumber(int raceId, int teamNumber);

    @Query("SELECT * FROM ParticipateRace WHERE ParticipateRace.ID_RACE = :raceId")
    List<ParticipateRace> getParticipationsFromCourse(int raceId);

    @Query("SELECT COUNT(DISTINCT ParticipateRace.TEAM_NUMBER) FROM ParticipateRace WHERE ParticipateRace.ID_RACE = :raceId")
    int getTeamsNumber(int raceId);

    @Query("UPDATE ParticipateRace SET RUNNING_ORDER = :runningOrder WHERE ID_RACE = :raceId AND ID_RUNNER = :runnerId")
    int updateRunningOrder(int raceId, int runnerId, int runningOrder);

    @Query("UPDATE ParticipateRace SET SPRINT1 = :time WHERE ID_RACE = :raceId AND ID_RUNNER = :runnerId")
    int updateSprint1(int raceId, int runnerId, float time);

    @Query("UPDATE ParticipateRace SET OBSTACLE1 = :time WHERE ID_RACE = :raceId AND ID_RUNNER = :runnerId")
    int updateObstacle1(int raceId, int runnerId, float time);

    @Query("UPDATE ParticipateRace SET PIT_STOP = :time WHERE ID_RACE = :raceId AND ID_RUNNER = :runnerId")
    int updatePitStop(int raceId, int runnerId, float time);

    @Query("UPDATE ParticipateRace SET SPRINT2 = :time WHERE ID_RACE = :raceId AND ID_RUNNER = :runnerId")
    int updateSprint2(int raceId, int runnerId, float time);

    @Query("UPDATE ParticipateRace SET OBSTACLE2 = :time WHERE ID_RACE = :raceId AND ID_RUNNER = :runnerId")
    int updateObstacle2(int raceId, int runnerId, float time);

    @Insert
    void insert(ParticipateRace participation);

    @Delete
    void delete(ParticipateRace participation);

}
