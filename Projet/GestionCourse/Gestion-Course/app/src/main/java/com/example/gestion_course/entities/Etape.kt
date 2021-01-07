package com.example.gestion_course.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Etape (
    @PrimaryKey(autoGenerate = false) val num_etape: Int,
    val nom_etape: String,
)