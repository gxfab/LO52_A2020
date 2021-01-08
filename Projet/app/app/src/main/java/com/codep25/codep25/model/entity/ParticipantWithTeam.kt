package com.codep25.codep25.model.entity

data class ParticipantWithTeam (
    val id: Long,
    val name: String,
    val level: Int,
    val teamName: String,
    val position: Int
) {
    constructor(participant: Participant, teamWithParticipants: TeamWithParticipants?) :
            this(
                participant.id_participant,
                participant.name,
                participant.level,
                teamWithParticipants?.team?.name ?: TEAM_NAME_UNKNOWN,
                participant.teamPosition?.inc() ?: POSITION_UNKNOWN
            )

    companion object {
        const val POSITION_UNKNOWN = -1
        const val TEAM_NAME_UNKNOWN = "-"
    }
}