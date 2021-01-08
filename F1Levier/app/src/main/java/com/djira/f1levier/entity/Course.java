package com.djira.f1levier.entity;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class Course {

    // create id column
    @PrimaryKey(autoGenerate = true)
    private int ID;

    // Create text column
    @ColumnInfo
    private String c1;

    // Create text column
    @ColumnInfo
    private String c2;

    // Create text column
    @ColumnInfo
    private String c3;

    // Create text column
    @ColumnInfo
    private String c4;


    // Getter and setter


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }



}
