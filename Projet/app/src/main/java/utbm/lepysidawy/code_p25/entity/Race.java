package utbm.lepysidawy.code_p25.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entity for the Race table
 */
@Entity
public class Race {

    @PrimaryKey(autoGenerate = true)
    private int idRace;

    @ColumnInfo(name = "NAME")
    private String name;

    public Race(String name){
        this.name = name;
    }

    public int getIdRace(){
        return idRace;
    }

    public String getName(){
        return name;
    }

    public void setIdRace(int idRace) {
        this.idRace = idRace;
    }
}
