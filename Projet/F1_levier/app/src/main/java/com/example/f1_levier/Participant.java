package com.example.f1_levier;

import android.text.Editable;

public class Participant {
    private String _name;
    private String _first_name;
    private int _level;

    /*Builder*/
    public Participant(String name, String fname, int lvl){
        this._name=name;
        this._first_name=fname;
        this._level=lvl;
    }

    /*Getter & Setter*/
    public void setName(String name){
        this._name=name;
    }

    public String getName(){
        return _name;
    }

    public void setFirstName(String first_name){
        this._first_name=first_name;
    }

    public String getFirstName() { return _first_name; }

    public void setLevel(int lvl){
        this._level=lvl;
    }

    public int getLevel(){
        return _level;
    }
}
