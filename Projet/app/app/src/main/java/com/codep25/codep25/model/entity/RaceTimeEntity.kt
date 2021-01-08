package com.codep25.codep25.model.entity

import androidx.room.*
import java.lang.Exception

@Entity(
    tableName = "race_time",
    foreignKeys = [
        ForeignKey(
            entity = Participant::class,
            parentColumns = ["id_participant"],
            childColumns = ["participant_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = RacingTeamEntity::class,
            parentColumns = ["racing_team_id"],
            childColumns = ["team_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RaceTimeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "race_time_id")
    val id: Long,

    @ColumnInfo(name = "state")
    val state: RacingState,

    @ColumnInfo(name = "team_id")
    val teamId: Long,

    @ColumnInfo(name = "participant_id")
    val participantId: Long,

    @ColumnInfo(name = "time_ms")
    val timeMs: Long
){
    fun toRacingTime(p: Participant) : RaceTime {
        if (p.id_participant != participantId)
            throw Exception("Wrong participant")

        return RaceTime(state, p, timeMs)
    }
}
