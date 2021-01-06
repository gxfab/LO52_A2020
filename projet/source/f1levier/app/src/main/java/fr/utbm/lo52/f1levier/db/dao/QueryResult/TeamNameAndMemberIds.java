package fr.utbm.lo52.f1levier.db.dao.QueryResult;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Relation;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import fr.utbm.lo52.f1levier.db.entity.TeamMemberJoin;

public class TeamNameAndMemberIds {
    public int id;
    public String name;
    @ColumnInfo(name = "race_id")
    public int raceId;
    @Relation(parentColumn = "id",
            entityColumn = "team_id",
            entity = TeamMemberJoin.class,
            projection = "member_id")
    public List<Long> memberIds;
    @Ignore
    public long lastTime = 0;
    @Ignore
    public int currentRunner = 0;
    @Ignore
    public int currentLapType = 0;
    @Ignore
    public ArrayBlockingQueue lapsQueue;
    @Ignore
    public int step = 1;
}
