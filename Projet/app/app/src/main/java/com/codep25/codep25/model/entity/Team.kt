package com.codep25.codep25.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "teams"
)
data class Team(
    @PrimaryKey(autoGenerate = true)
    val id_team: Long,

    @ColumnInfo(name = "team_name")
    val name: String
)