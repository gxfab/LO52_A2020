package com.codep25.codep25.model.balancing

import com.codep25.codep25.model.entity.Participant

data class BalancingTeam(
    val teamId: Long,
    val participants: Set<Participant>,
    val score: Int
) {

    val size get() = participants.size

    constructor(teamId: Long, participants: Set<Participant>):
            this(teamId, participants, participants.map { it.level }.sum())

    fun swapParticipants(oldParticipant: Participant, newParticipant: Participant): BalancingTeam {
        val newParticipants = participants.toMutableSet()
        val newScore = score - oldParticipant.level + newParticipant.level

        if (!newParticipants.remove(oldParticipant))
            throw Exception("This participant is not in the team")

        if (size < 1)
            throw Exception("This team is empty")

        newParticipants.add(newParticipant)

        return BalancingTeam(teamId, newParticipants, newScore)
    }

    fun getBestParticipant() : Participant {
        var p = participants.first()

        for (_p in participants) {
            if (_p.level > p.level)
                p = _p
        }

        return p
    }
}
