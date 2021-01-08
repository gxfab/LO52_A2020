package fr.utbm.runf1.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

/**
 * Created by Yosef B.I.
 */
@Entity(
        foreignKeys =
                {
                        @ForeignKey(entity = Runner.class,
                                parentColumns = "runner_id",
                                childColumns = "runner_id",
                                onDelete = CASCADE,
                                onUpdate = CASCADE),

                        @ForeignKey(entity = Race.class,
                                parentColumns = "race_id",
                                childColumns = "race_id")
                }
)
public class RunnerHistory {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "runner_id")
    private int runnerId;

    @ColumnInfo(name = "race_id")
    private int raceId;

    private long timeSprint1;
    private long timeObstacle1;
    private long timePitStop;
    private long timeSprint2;
    private long timeObstacle2;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRunnerId() {
        return runnerId;
    }

    public void setRunnerId(int runnerId) {
        this.runnerId = runnerId;
    }

    public int getRaceId() {
        return raceId;
    }

    public void setRaceId(int raceId) {
        this.raceId = raceId;
    }

    public long getTimeSprint1() {
        return timeSprint1;
    }

    public void setTimeSprint1(long timeSprint1) {
        this.timeSprint1 = timeSprint1;
    }

    public long getTimeObstacle1() {
        return timeObstacle1;
    }

    public void setTimeObstacle1(long timeObstacle1) {
        this.timeObstacle1 = timeObstacle1;
    }

    public long getTimePitStop() {
        return timePitStop;
    }

    public void setTimePitStop(long timePitStop) {
        this.timePitStop = timePitStop;
    }

    public long getTimeSprint2() {
        return timeSprint2;
    }

    public void setTimeSprint2(long timeSprint2) {
        this.timeSprint2 = timeSprint2;
    }

    public long getTimeObstacle2() {
        return timeObstacle2;
    }

    public void setTimeObstacle2(long timeObstacle2) {
        this.timeObstacle2 = timeObstacle2;
    }
}
