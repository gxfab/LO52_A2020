package utbm.lepysidawy.code_p25.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

/**
 * Entity for the ParticipateRace table
 */
@Entity(primaryKeys = {"ID_RACE","ID_RUNNER"}, foreignKeys = {@ForeignKey(entity = Race.class, parentColumns = "idRace", childColumns = "ID_RACE"), @ForeignKey(entity = Runner.class, parentColumns = "idPlayer", childColumns = "ID_RUNNER")})
public class ParticipateRace {

    @ColumnInfo(name = "ID_RACE")
    private int idRace;
    @ColumnInfo(name = "ID_RUNNER")
    private int idRunner;
    @ColumnInfo(name = "TEAM_NUMBER")
    private int teamNumber;
    @ColumnInfo(name = "RUNNING_ORDER")
    private int runningOrder;
    @ColumnInfo(name = "SPRINT1")
    private float sprint1;
    @ColumnInfo(name = "OBSTACLE1")
    private float obstacle1;
    @ColumnInfo(name = "PIT_STOP")
    private float pitStop;
    @ColumnInfo(name = "SPRINT2")
    private float sprint2;
    @ColumnInfo(name = "OBSTACLE2")
    private float obstacle2;

    public ParticipateRace(int idRace, int idRunner, int teamNumber, int runningOrder) {
        this.idRace = idRace;
        this.idRunner = idRunner;
        this.teamNumber = teamNumber;
        this.runningOrder = runningOrder;
    }

    public int getIdRace() {
        return idRace;
    }

    public int getIdRunner() {
        return idRunner;
    }

    public int getTeamNumber() {
        return teamNumber;
    }

    public int getRunningOrder() {
        return runningOrder;
    }

    public float getSprint1() {
        return sprint1;
    }

    public void setSprint1(float sprint1) {
        this.sprint1 = sprint1;
    }

    public float getObstacle1() {
        return obstacle1;
    }

    public void setObstacle1(float obstacle1) {
        this.obstacle1 = obstacle1;
    }

    public float getPitStop() {
        return pitStop;
    }

    public void setPitStop(float pitStop) {
        this.pitStop = pitStop;
    }

    public float getSprint2() {
        return sprint2;
    }

    public void setSprint2(float sprint2) {
        this.sprint2 = sprint2;
    }

    public float getObstacle2() {
        return obstacle2;
    }

    public void setObstacle2(float obstacle2) {
        this.obstacle2 = obstacle2;
    }

    @Override
    public String toString() {
        return "SPRINT 1 " + this.sprint1 + "\n" + " OBSTACLE 1 " + this.obstacle1 + "\n"
        + " PITSTOP " + this.pitStop + "\n" + " SPRINT 2 " + this.sprint2 + "\n" +
        " OBSTACLE 2 " + this.obstacle2 + "\n";
    }

}
