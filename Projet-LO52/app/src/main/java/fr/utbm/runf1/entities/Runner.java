package fr.utbm.runf1.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Created by Yosef B.I.
 */
@Entity(foreignKeys = @ForeignKey(entity = Team.class,
    parentColumns = "team_id",
    childColumns = "team_id"), //Constraint : ensures no Team is set to Runner if Team doesn't exist

        indices = {@Index(value = {"team_id", "order_within_team"},
    unique = true)})

public class Runner {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "runner_id")
    private int id;

    private String firstName;
    private String lastName;
    private int level;

    @ColumnInfo(name = "team_id")
    private Integer teamId;
    @ColumnInfo(name = "order_within_team")
    private int orderWithinTeam;

    public Runner(){}

    public Runner(String firstName, String lastName, int level) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.level = level;
    }

    public Runner(String firstName, String lastName, int level, int teamId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.level = level;
        this.teamId = teamId;
    }

    public Runner(String firstName, String lastName, int level, long startTime, long timeSprint1, long timeObstacle1, long timePitStop, long timeSprint2, long timeObstacle2, int teamId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.level = level;
        this.teamId = teamId;
    }

    public Runner(Runner runner) {
        this.id = runner.getId();
        this.firstName = runner.getFirstName();
        this.lastName = runner.getLastName();
        this.level = runner.getLevel();
        this.teamId = runner.getTeamId();
        this.orderWithinTeam = runner.getOrderWithinTeam();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getOrderWithinTeam() {
        return orderWithinTeam;
    }

    public void setOrderWithinTeam(int orderWithinTeam) {
        this.orderWithinTeam = orderWithinTeam;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    @Override
    public String toString() {
        return "Runner{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", level=" + level +
                ", teamId=" + teamId +
                '}';
    }
}
