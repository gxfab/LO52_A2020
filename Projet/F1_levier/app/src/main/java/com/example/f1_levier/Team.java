package com.example.f1_levier;

import android.text.Editable;

import java.util.ArrayList;

public class Team {
    private ArrayList<Participant> _team;
    private int _level;
    private int _id;

    /*Builder*/
    public Team(int id,ArrayList<Participant>team, int lvl){
        this._team = team;
        this._level=lvl;
        this._id=id;
    }
    public Team(int id,Participant p1, Participant p2,Participant p3,int lvl){
        ArrayList<Participant> team = new ArrayList<Participant>();
        team .add(p1);
        team.add(p2);
        team.add(p3);
        this._team = team;
        this._level=lvl;
        this._id=id;
    }
    public Team(int id,Participant p1, Participant p2,Participant p3){
        ArrayList<Participant> team = new ArrayList<Participant>();
        team.add(p1);
        team.add(p2);
        team.add(p3);
        this._team = team;
        this._level=p1.getLevel()+p2.getLevel()+p3.getLevel();
        this._id=id;
    }

    /*Getter & Setter*/
    public void setParticipants(ArrayList<Participant> team){
        this._team=team;
    }
    public void setParticipants(Participant p1, Participant p2,Participant p3){
        ArrayList<Participant> team = new ArrayList<Participant>();
        team .add(p1);
        team.add(p2);
        team.add(p3);
        this._team = team;
    }
    public ArrayList<Participant> getParticipants(){
        return _team;
    }

    public void setLevel(int lvl){
        this._level=lvl;
    }

    public int getLevel(){
        return _level;
    }

    public void setId(int id){
        this._id=id;
    }

    public int getId(){
        return _id;
    }


}
