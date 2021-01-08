package com.codep25.codep25.model.entity

data class RaceTime(
    val state: RacingState,
    val participant: Participant,
    val timeMs: Long
) {
    fun toRaceTimeEntity(teamId: Long) = RaceTimeEntity(
        0,
        state,
        teamId,
        participant.id_participant,
        timeMs
    )
}