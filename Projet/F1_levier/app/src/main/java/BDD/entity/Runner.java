package BDD.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity
public class Runner
{
    @PrimaryKey(autoGenerate = true)
    private int runnerId;
    @ColumnInfo(name = "last_name")
    private String lastName;
    @ColumnInfo(name = "first_name")
    private int isInTeamId;
    private String firstName;
    private int level;
    private Date time1;
    private Date time2;
    private Date time3;
    private Date time4;
    private Date time5;

    /*Builder*/
    public Runner()
    {

    }

    public Runner(String name, String fname, int lvl){
        this.lastName=name;
        this.firstName =fname;
        this.level =lvl;
        this.time1 = null;
        this.time2 = null;
        this.time3 = null;
        this.time4 = null;
        this.time5 = null;
    }

    public Runner(Runner p){
        this.runnerId=p.getRunnerId();
        this.lastName=p.getLastName();
        this.firstName =p.getFirstName();
        this.level =p.getLevel();
        this.time1 = p.getTime1();
        this.time2 = p.getTime2();
        this.time3 = p.getTime3();
        this.time4 = p.getTime4();
        this.time5 = p.getTime5();
    }


    /*Getter & Setter*/

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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getIsInTeamId() {
        return isInTeamId;
    }

    public void setIsInTeamId(int isInTeamId) {
        this.isInTeamId = isInTeamId;
    }

    public Date getTime1() {
        return time1;
    }

    public void setTime1(Date time1) {
        this.time1 = time1;
    }

    public Date getTime2() {
        return time2;
    }

    public void setTime2(Date time2) {
        this.time2 = time2;
    }

    public Date getTime3() {
        return time3;
    }

    public void setTime3(Date time3) {
        this.time3 = time3;
    }

    public Date getTime4() {
        return time4;
    }

    public void setTime4(Date time4) {
        this.time4 = time4;
    }

    public Date getTime5() {
        return time5;
    }

    public void setTime5(Date time5) {
        this.time5 = time5;
    }

    public int getRunnerId() {
        return runnerId;
    }

    public void setRunnerId(int runnerId) {
        this.runnerId = runnerId;
    }
}
