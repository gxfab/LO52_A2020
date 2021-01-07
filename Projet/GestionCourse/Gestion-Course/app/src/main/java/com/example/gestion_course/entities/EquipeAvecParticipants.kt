package com.example.gestion_course.entities

import androidx.room.Embedded
import androidx.room.Relation

data class EquipeAvecParticipants (
    @Embedded val equipe: Equipe,
    @Relation(
        parentColumn = "num_equipe",
        entityColumn = "num_participant"
    )
    val participants: List<Participant>
)