package com.example.f1_levier.BDD.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;

@Entity
public class Team {

    @PrimaryKey (autoGenerate = true)
    private int teamId;
    private int level;
    private int rating;
    private Date time;
    private int nb_step;



    /**
     * Builder
     * @param id
     * @param lvl
     */
    public Team(int id, int lvl){
        this.level=lvl;
        this.teamId=id;
        this.rating = 0;
        this.time = null;
        this.nb_step =0;
    }

    /**
     * Builder
     * @param id
     * @param p1
     * @param p2
     * @param p3
     * @param lvl
     */
    public Team(int id, Runner p1, Runner p2, Runner p3, int lvl){
        ArrayList<Runner> team = new ArrayList<Runner>();
        team .add(p1);
        team.add(p2);
        team.add(p3);
        this.level=lvl;
        this.teamId=id;
        this.rating = 0;
        this.time = null;
        this.nb_step =0;
    }

    /**
     * Builder
     * @param id
     * @param p1
     * @param p2
     * @param p3
     */
    public Team(int id, Runner p1, Runner p2, Runner p3){
        ArrayList<Runner> team = new ArrayList<Runner>();
        team.add(p1);
        team.add(p2);
        team.add(p3);
        this.level=p1.getLevel()+p2.getLevel()+p3.getLevel();
        this.teamId=id;
        this.nb_step =0;
    }





    public void setLevel(int lvl){
        this.level=lvl;
    }

    public int getLevel(){
        return level;
    }

    public void setId(int id){
        this.teamId=id;
    }

    public int getId(){
        return teamId;
    }

    public void setNb_step(int step){
        this.nb_step =step;
    }

    public int getNb_step(){ return nb_step;}

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
