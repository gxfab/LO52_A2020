package com.example.gestion_course.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Equipe (
    @PrimaryKey(autoGenerate = false) val num_equipe: Int,
    val nom_equipe: String,
)