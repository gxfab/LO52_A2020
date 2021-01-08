package com.utbm.nouassi.manou.coursecodep25.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class EquipeAndCoureur (
    @Embedded val equipe: Equipe,
    @Relation(
        parentColumn = "id",
        entityColumn = "equipeId"
    )
    val coureurs: List<Coureur>
)