package com.example.gestion_course.entities

import androidx.room.Entity
import androidx.room.Index


@Entity(
        primaryKeys = ["num_etape_temps","num_participant_temps"],
        foreignKeys = [
        androidx.room.ForeignKey(entity = com.example.gestion_course.entities.Etape::class,
                parentColumns = kotlin.arrayOf("num_etape"),
                childColumns = kotlin.arrayOf("num_etape_temps"),
                onDelete = androidx.room.ForeignKey.NO_ACTION),
        androidx.room.ForeignKey(entity = com.example.gestion_course.entities.Participant::class,
                parentColumns = kotlin.arrayOf("num_participant"),
                childColumns = kotlin.arrayOf("num_participant_temps"),
                onDelete = androidx.room.ForeignKey.NO_ACTION)],

        indices = [Index(value = ["num_etape_temps", "num_participant_temps"])])


data class Temps (
        val num_etape_temps: Int,
        val num_participant_temps: Int,
        val time: Int
)