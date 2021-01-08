package com.codep25.codep25.model.entity

import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.Relation

data class TeamWithParticipants(
    @Embedded
    val team: Team,

    @Relation(
        parentColumn = "id_team",
        entityColumn = "id_fkteam"
    )
    val participants: List<Participant>
) {
    @Ignore
    val orderedParticipants = participants.sortedBy { it.teamPosition ?: -1 }
    val level get() = participants.map { it.level }.sum()

    fun swapOrdered(fromPos: Int, toPos: Int): TeamWithParticipants {
        val newParticipants = orderedParticipants.toMutableList()
        newParticipants[fromPos] = newParticipants[fromPos].affectNewPosition(toPos)

        var start = fromPos + 1
        var stop = toPos
        var mutator = -1

        if (toPos < fromPos) {
            start = toPos
            stop = fromPos - 1
            mutator = 1
        }

        for (i in start..stop)
            newParticipants[i] = newParticipants[i].affectNewPosition(
                i + mutator
            )

        return TeamWithParticipants(
            team,
            newParticipants
        )
    }
}