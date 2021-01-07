package com.example.gestion_course.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
        foreignKeys = [
            ForeignKey(entity = Etape::class,
        parentColumns = arrayOf("num_etape"),
        childColumns = arrayOf("num_etape_participant"),
        onDelete = ForeignKey.NO_ACTION),
            ForeignKey(entity = Equipe::class,
        parentColumns = arrayOf("num_equipe"),
        childColumns = arrayOf("num_equipe_participant"),
        onDelete = ForeignKey.NO_ACTION)],

        indices = [Index(value = ["num_etape_participant", "num_equipe_participant"])])


data class Participant (
    @PrimaryKey(autoGenerate = false) val num_participant: Int,
    val nom_participant: String,
    val niveau_participant: Int,
    var ordre_passage: Int?,
    var num_equipe_participant: Int?,
    var num_etape_participant: Int?
)