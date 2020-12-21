package com.example.f1_levier;

import android.text.Editable;

import java.util.ArrayList;

public class Team {
    private ArrayList<Participant> _team;
    private int _level;
    private int _id;
    private int _nb_step;
    private boolean _goal;
    private int _id_p;

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
        this._nb_step=0;
        this._goal = false;
        this._id_p=1;
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
    public void setParticipant(int n_id,Participant p){
        getParticipants().get(n_id).setName(p.getName());
        getParticipants().get(n_id).setFirstName(p.getFirstName());
        getParticipants().get(n_id).setLevel(p.getLevel());
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

    public void setNb_step(int step){
        this._nb_step=step;
    }

    public int getNb_step(){ return _nb_step;}

    public void setGoal(boolean i){this._goal=i;}

    public boolean getGoal(){return _goal;}

    public void setIdP(int i){this._id_p=i;}

    public int getIdP(){return _id_p;}
}
