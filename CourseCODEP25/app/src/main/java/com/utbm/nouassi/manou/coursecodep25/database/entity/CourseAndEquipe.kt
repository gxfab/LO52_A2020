package com.utbm.nouassi.manou.coursecodep25.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CourseAndEquipe (
    @Embedded val course: Course,
    @Relation(
        parentColumn = "id",
        entityColumn = "courseId"
    )
    val equipes: List<Equipe>
)