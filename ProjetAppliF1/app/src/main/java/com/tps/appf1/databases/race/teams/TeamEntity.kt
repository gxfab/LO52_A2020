package com.tps.appf1.databases.race.teams

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Teams Table")
data class TeamEntity(
        @PrimaryKey(autoGenerate = true)  //This guarantees that the ID for each night is unique.
        var TeamID: Long = 0L,

        @ColumnInfo(name = "Nom Coureur 1")
        val TeamRunner1: String = "none",

        @ColumnInfo(name = "Nom Coureur 2")
        val TeamRunner2: String = "none",

        @ColumnInfo(name = "Nom Coureur 3")
        val TeamRunner3: String = "none",

        @ColumnInfo(name = "Niveau Total")
        val TeamLevel: Int = 0,

        @ColumnInfo(name = "Temps Total Course")
        val TeamTime: String = "none"
)
