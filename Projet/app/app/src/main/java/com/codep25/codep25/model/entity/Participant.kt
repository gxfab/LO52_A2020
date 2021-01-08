package com.codep25.codep25.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.SET_NULL
import androidx.room.PrimaryKey

@Entity(
    tableName = "participants",
    foreignKeys = [ForeignKey(
        entity = Team::class,
        parentColumns = ["id_team"],
        childColumns = ["id_fkteam"],
        onDelete = SET_NULL
    )
    ]
)
data class Participant(
    @PrimaryKey(autoGenerate = true)
    val id_participant: Long,

    @ColumnInfo(name = "id_fkteam", index = true)
    val id_fkteam: Long?,

    @ColumnInfo(name = "team_position")
    val teamPosition: Int?,

    @ColumnInfo(name = "participant_name")
    val name: String,

    @ColumnInfo(name = "participant_level")
    val level: Int
) {
    
    fun affectToTeam(id: Long, position: Int) = Participant(
        id_participant,
        id,
        position,
        name,
        level
    )

    fun affectNewPosition(position: Int?) = Participant(
        id_participant,
        id_fkteam,
        position,
        name,
        level
    )
    
    fun applyMaxLevel(maxLevel: Int): Participant {
        return if (level < maxLevel)
            this
        else
            Participant(
                id_participant,
                id_fkteam,
                teamPosition,
                name,
                maxLevel
            )
    }
}