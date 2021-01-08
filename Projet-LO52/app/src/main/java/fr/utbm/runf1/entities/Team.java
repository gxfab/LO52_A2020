package fr.utbm.runf1.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Yosef B.I.
 */
@Entity
public class Team {

    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "team_id")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
