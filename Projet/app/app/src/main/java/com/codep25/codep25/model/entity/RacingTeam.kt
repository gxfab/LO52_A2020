package com.codep25.codep25.model.entity

data class RacingTeam(
    val team: Team,
    val participants: List<Participant>,
    val cycleNumber: Int,
    val progress: Int,
    val currentParticipantPos: Int,
    val currentState: RacingState,
    val times: List<RaceTime>
) {
    val currentParticipant get() = participants[currentParticipantPos]
    val totalRaceTime = times.map { it.timeMs }.sum()
    val isFinished get() = progress == 100
    private val stepNumber = times.size + 1

    fun computeNext(config: RaceConfig, elapsedTime: Long) : RacingTeam {
        if (progress == 100)
            return this

        var nextState = currentState.getNext()
        var nextCycle = cycleNumber + if (nextState == RacingState.SPRINT) 1 else 0
        val newTimes = times.toMutableList().apply {
            add(RaceTime(
                currentState,
                currentParticipant,
                elapsedTime - totalRaceTime
            ))
        }
        var nextParticipantPos = currentParticipantPos

        if (nextCycle == config.nbTurnPerParticipant && nextState == RacingState.PIT_STOP) {
            nextCycle = 1
            nextState = RacingState.SPRINT
            nextParticipantPos = (currentParticipantPos + 1) % participants.size
        }

        return RacingTeam(
            team,
            participants,
            nextCycle,
            (100 * stepNumber) / config.totalNbOfSteps,
            nextParticipantPos,
            nextState,
            newTimes
        )
    }

    fun toRacingTeamEntity(raceId: Long) = RacingTeamEntity(
        0,
        raceId,
        team.name
    )

    class Factory {
        companion object {
            fun fromTeamWithParticipants(teamWithParticipants: TeamWithParticipants) : RacingTeam {
                return RacingTeam(
                    teamWithParticipants.team,
                    teamWithParticipants.orderedParticipants,
                    1,
                    0,
                    0,
                    RacingState.SPRINT,
                    listOf()
                )
            }

            fun fromRacingTeamEntity(
                raceTeamEntity: RacingTeamEntity,
                participants: List<Participant>,
                times: List<RaceTime>
            ): RacingTeam {
                return RacingTeam(
                    Team(raceTeamEntity.id, raceTeamEntity.name),
                    participants,
                    1,
                    100,
                    0,
                    RacingState.SPRINT,
                    times
                )
            }
        }
    }
}