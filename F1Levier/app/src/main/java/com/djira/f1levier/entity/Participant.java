package com.djira.f1levier.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.SET_NULL;

@Entity(indices  = {@Index("equipe_id")},
        foreignKeys = @ForeignKey(entity=Equipe.class, parentColumns="ID", childColumns="equipe_id",
                onUpdate = CASCADE,
                onDelete = SET_NULL)
)
public class Participant {
    // create id column
    @PrimaryKey(autoGenerate = true)
    private int ID;

    // Create text column
    @ColumnInfo
    private String name;

    // Create text column
    @ColumnInfo
    private int level;

    // Create text column
    @ColumnInfo
    private int t1;

    // Create text column
    @ColumnInfo
    private int t2;

    @ColumnInfo
    private int ps;

    // Create text column
    @ColumnInfo
    private int t3;

    // Create text column
    @ColumnInfo
    private int t4;

    @ColumnInfo
    Integer equipe_id;

    // Getter and setter


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getT1() {
        return t1;
    }

    public void setT1(int t1) {
        this.t1 = t1;
    }

    public int getT2() {
        return t2;
    }

    public void setT2(int t2) {
        this.t2 = t2;
    }

    public int getPs() {
        return ps;
    }

    public void setPs(int ps) {
        this.ps = ps;
    }

    public int getT3() {
        return t3;
    }

    public void setT3(int t3) {
        this.t3 = t3;
    }

    public int getT4() {
        return t4;
    }

    public void setT4(int t4) {
        this.t4 = t4;
    }

    public Integer getEquipe_id() {
        return equipe_id;
    }

    public void setEquipe_id(Integer equipe_id) {
        this.equipe_id = equipe_id;
    }
}

