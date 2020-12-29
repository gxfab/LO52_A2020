package com.example.f1_levier.BDD.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;

@Entity
public class Team {

    @PrimaryKey
    private int teamId;
    private int level;
    private int rating;
    private long time;
    private int nb_step;
    private int firstRunnerId;
    private int secondRunnerId;
    private int thirdRunnerId;
    private boolean _goal;
    private int _id_p;

    public Team(){};

    public Team(int _id, int lvl, int first, int second, int third){
        this.teamId = _id;
        this.level=lvl;
        this.rating = 0;
        this.time = -1;
        this.nb_step =0;
        this.firstRunnerId = first;
        this.secondRunnerId = second;
        this.thirdRunnerId = third;
        this._goal = false;
        this._id_p = 1;
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getFirstRunnerId() {
        return firstRunnerId;
    }

    public void setFirstRunnerId(int firstRunnerId) {
        this.firstRunnerId = firstRunnerId;
    }

    public int getSecondRunnerId() {
        return secondRunnerId;
    }

    public void setSecondRunnerId(int secondRunnerId) {
        this.secondRunnerId = secondRunnerId;
    }

    public int getThirdRunnerId() {
        return thirdRunnerId;
    }

    public void setThirdRunnerId(int thirdRunnerId) {
        this.thirdRunnerId = thirdRunnerId;
    }

    public boolean is_goal() {
        return _goal;
    }

    public void set_goal(boolean _goal) {
        this._goal = _goal;
    }

    public int get_id_p() {
        return _id_p;
    }

    public void set_id_p(int _id_p) {
        this._id_p = _id_p;
    }
}
