package fr.utbm.lo52.f1levier.db.entity;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "race")
public class Race {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String name;

    @NonNull
    @ColumnInfo(name="created_at")
    public Date createdAt;

    @ColumnInfo(name="started_at")
    public Date startedAt;

    @ColumnInfo(name="finished_at")
    public Date finishedAt;

    public Race(@NonNull String name) {
        this.name = name;
        this.createdAt = new Date();
        this.startedAt = null;
        this.finishedAt = null;
    }
}
