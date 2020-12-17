package BDD.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Runner
{
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "level")
    private int level;

    @ColumnInfo(name = "last_name")
    private String lastName;

    @ColumnInfo(name = "first_name")
    private String firstName;

    /*Builder*/
    public Runner(String name, String fname, int lvl){
        this.lastName=name;
        this.firstName =fname;
        this.level =lvl;
    }

    public Runner(Runner p){
        this.lastName=p.getName();
        this.firstName =p.getFirstName();
        this.level =p.getLevel();
    }


    /*Getter & Setter*/
    public void setName(String name){
        this.lastName=name;
    }

    public String getName(){
        return lastName;
    }

    public void setFirstName(String first_name){
        this.firstName =first_name;
    }

    public String getFirstName() { return firstName; }

    public void setLevel(int lvl){
        this.level =lvl;
    }

    public int getLevel(){
        return level;
    }
}
