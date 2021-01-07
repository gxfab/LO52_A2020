package utbm.lepysidawy.code_p25.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entity for the Runner table
 */
@Entity
public class Runner {

    @PrimaryKey(autoGenerate = true)
    private int idPlayer;

    @ColumnInfo(name = "FIRST_NAME")
    private String firstName;

    @ColumnInfo(name = "LAST_NAME")
    private String lastName;

    @ColumnInfo(name = "LEVEL")
    private int niveau;

    public Runner(String firstName, String lastName, int niveau){
        this.firstName = firstName;
        this.lastName = lastName;
        this.niveau = niveau;
    }

    public int getIdPlayer(){
        return idPlayer;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public int getNiveau(){
        return niveau;
    }

    @Override
    public String toString() {
        return this.firstName + " " + this.lastName + " " + this.niveau;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }
}
