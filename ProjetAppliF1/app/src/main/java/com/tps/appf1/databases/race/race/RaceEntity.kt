package com.tps.appf1.databases.race.race

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Races Table")
data class RaceEntity(
        @PrimaryKey(autoGenerate = true)  //This guarantees that the ID for each night is unique.
        var RaceID: Long = 0L,

        @ColumnInfo(name = "Date of creation")
        val CreationDate: Long = System.currentTimeMillis()
        //var TeamsDB: database = TeamsDB
        //var RunnerDB: database = RunnerDB
)
