package com.codep25.codep25.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "race"
)
data class Race(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "race_id")
    val id: Long,

    val date: Long,

    @ColumnInfo(name = "nb_turn_per_participant")
    val nbTurnPerParticipant: Int,

    @ColumnInfo(name = "nb_relay")
    val nbOfRelay: Int
)