package com.djira.f1levier.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Equipe {
    // create id column
    @PrimaryKey(autoGenerate = true)
    private Integer ID;

    // Create text column
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo
    private Integer level;

    @ColumnInfo
    private Integer temps;


    // Getter and setter

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getTemps() {
        return temps;
    }

    public void setTemps(Integer temps) {
        this.temps = temps;
    }
}
