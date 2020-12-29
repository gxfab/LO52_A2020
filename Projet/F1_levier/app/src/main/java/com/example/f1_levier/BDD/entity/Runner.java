package com.example.f1_levier.BDD.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Runner
{
    @PrimaryKey
    private int runnerId;
    @ColumnInfo(name = "last_name")
    private String lastName;
    @ColumnInfo(name = "first_name")
    private String firstName;
    private int level;
    private int teamId;
    private long time1;
    private long time2;
    private long time3;
    private long time4;
    private long time5;

    /*Builder*/
    public Runner(){}

    public Runner(int _id, String name, String fname, int lvl)
    {
        this.runnerId = _id;
        this.lastName=name;
        this.firstName =fname;
        this.level = lvl;
        this.teamId = -1;
        this.time1 = -1;
        this.time2 = -1;
        this.time3 = -1;
        this.time4 = -1;
        this.time5 = -1;
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

    public long getTime1() {
        return time1;
    }

    public void setTime1(long time1) {
        this.time1 = time1;
    }

    public long getTime2() {
        return time2;
    }

    public void setTime2(long time2) {
        this.time2 = time2;
    }

    public long getTime3() {
        return time3;
    }

    public void setTime3(long time3) {
        this.time3 = time3;
    }

    public long getTime4() {
        return time4;
    }

    public void setTime4(long time4) {
        this.time4 = time4;
    }

    public long getTime5() {
        return time5;
    }

    public void setTime5(long time5) {
        this.time5 = time5;
    }

    public int getRunnerId() {
        return runnerId;
    }

    public void setRunnerId(int runnerId) {
        this.runnerId = runnerId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public long getTime (int id)
    {
        long result = -1;
        switch(id)
        {
            case 1:
                result = time1;
                break;
            case 2:
                result = time2;
                break;
            case 3:
                result = time3;
                break;
            case 4:
                result = time4;
                break;
            case 5:
                result = time5;
                break;
        }
        return result;
    }
}