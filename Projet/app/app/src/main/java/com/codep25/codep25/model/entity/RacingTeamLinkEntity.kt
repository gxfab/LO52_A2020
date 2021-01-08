package com.codep25.codep25.model.entity

import androidx.room.*

@Entity(
    tableName = "racing_team_link",
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
    ],
    indices = [Index(value = ["participant_id", "team_id"], unique = true)]
)
data class RacingTeamLinkEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(name = "participant_id")
    val participantId: Long,

    @ColumnInfo(name = "team_id")
    val racingTeamId: Long
)
