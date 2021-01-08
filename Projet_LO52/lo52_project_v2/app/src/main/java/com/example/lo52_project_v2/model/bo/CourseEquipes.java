package com.example.lo52_project_v2.model.bo;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class CourseEquipes {
    @Embedded
    public Course course;
    @Relation(
            parentColumn = "idCourse",
            entityColumn = "idCourse"
    )
    public List<Equipe> equipes;

}
