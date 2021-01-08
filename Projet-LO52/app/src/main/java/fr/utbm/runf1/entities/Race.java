package fr.utbm.runf1.entities;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import fr.utbm.runf1.entities.converters.DateConverter;

/**
 * Created by Yosef B.I.
 */
@Entity
@TypeConverters(DateConverter.class)
public class Race {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "race_id")
    private int id;

    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
