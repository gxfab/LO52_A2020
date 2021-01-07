package com.tps.appf1.databases.race.teams

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Teams Table")
data class TeamEntity(
        @PrimaryKey(autoGenerate = true)  //This guarantees that the ID for each night is unique.
        val TeamID: Long = 0L,

        @ColumnInfo(name = "ID Coureur 1")
        var TeamRunner1: Int = 0,

        @ColumnInfo(name = "Lvl Coureur 1")
        var TeamRunner1Level: Int = 0,

        @ColumnInfo(name = "ID Coureur 2")
        var TeamRunner2: Int = 0,

        @ColumnInfo(name = "Lvl Coureur 2")
        var TeamRunner2Level: Int = 0,

        @ColumnInfo(name = "ID Coureur 3")
        var TeamRunner3: Int = 0,

        @ColumnInfo(name = "Lvl Coureur 3")
        var TeamRunner3Level: Int = 0,

        @ColumnInfo(name = "Niveau Total")
        var TeamLevel: Int = 0,

        @ColumnInfo(name = "Temps Total Course")
        var TeamTime: String = "none"
)
