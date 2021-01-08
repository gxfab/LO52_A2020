package com.codep25.codep25.model.entity


data class RaceWithTeams(
    val config: RaceConfig,
    val teams: List<RacingTeam>
) {
    fun toRace() = Race(
        0,
        System.currentTimeMillis(),
        config.nbTurnPerParticipant,
        config.nbOfRelay
    )

    fun allTimes(): List<RaceTime> {
        val times = ArrayList<RaceTime>()
        teams.forEach { times.addAll(it.times) }
        return times
    }
}