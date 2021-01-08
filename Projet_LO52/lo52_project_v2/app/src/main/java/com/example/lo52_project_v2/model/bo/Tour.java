package com.example.lo52_project_v2.model.bo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Tour {

    public Tour(String type, long duree , int idCourse , int idParticipant){
        this.type = type;
        this.idCourse = idCourse;
        this.duree = duree;
        this.idParticipant = idParticipant;
    }

    @PrimaryKey(autoGenerate = true)
    public int idTour;

    @ColumnInfo(name = "type")
    public String type;

    public int idCourse;
    public int idParticipant;
    public long duree;
}
