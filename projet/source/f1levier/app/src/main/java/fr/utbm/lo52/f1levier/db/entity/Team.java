package fr.utbm.lo52.f1levier.db.entity;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "team",
        foreignKeys = { @ForeignKey(entity = Race.class,
                parentColumns = "id",
                childColumns = "race_id")})
public class Team {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;

    @ColumnInfo(name="race_id")
    public int raceId;

    public Team(String name, int raceId) {
        this.name = name;
        this.raceId = raceId;
    }
}
