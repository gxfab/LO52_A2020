package fr.utbm.lo52.f1levier.db.entity;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "measured_time",
        primaryKeys = { "participant_id", "race_id", "step" },
        foreignKeys = {
                @ForeignKey(entity = Participant.class,
                        parentColumns = "id",
                        childColumns = "participant_id"),
                @ForeignKey(entity = Race.class,
                        parentColumns = "id",
                        childColumns = "race_id")
        })
public class MeasuredTime {

    public final class LapType {
        public final static int SPRINT = 0;
        public final static int SPLIT = 1;
        public final static int PIT_STOP = 2;
    }

    public static final List<Integer> queue = new ArrayList<Integer>() {{
        add(LapType.SPRINT);
        add(LapType.SPLIT);
        add(LapType.PIT_STOP);
        add(LapType.SPRINT);
        add(LapType.SPLIT);
    }};

    @ColumnInfo(name="participant_id")
    public final int participantId;

    @ColumnInfo(name="race_id")
    public final int raceId;

    public final int step;

    public final int stepType;

    public final long measuredTime;

    public MeasuredTime(int participantId, int raceId, int step, int stepType, long measuredTime) {
        this.participantId = participantId;
        this.raceId = raceId;
        this.step = step;
        this.stepType = stepType;
        this.measuredTime = measuredTime;
    }
}
