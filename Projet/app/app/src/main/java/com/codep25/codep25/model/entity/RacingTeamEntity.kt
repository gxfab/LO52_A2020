package com.codep25.codep25.model.entity

import androidx.room.*

@Entity(
    tableName = "racing_team",
    foreignKeys = [
        ForeignKey(
            entity = Race::class,
            parentColumns = ["race_id"],
            childColumns = ["race_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RacingTeamEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "racing_team_id")
    val id: Long,

    @ColumnInfo(name = "race_id")
    val raceId: Long,

    @ColumnInfo(name = "team_name")
    val name: String
)
