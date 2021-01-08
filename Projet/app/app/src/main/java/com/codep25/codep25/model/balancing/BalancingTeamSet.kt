package com.codep25.codep25.model.balancing

import com.codep25.codep25.BuildConfig
import com.codep25.codep25.model.entity.Participant
import java.lang.Exception

data class BalancingTeamSet(
    val teams: Set<BalancingTeam>,
    val minScore: Int,
    val maxScore: Int
) {
    val size get() = teams.size
    val scoreGap = maxScore - minScore

    constructor(teams: Set<BalancingTeam>) : this(
        teams,
        teams.map { it.score }.min() ?: 0,
        teams.map { it.score }.max() ?: 0
    )

    private fun applyRandomSwap(fromTeam: BalancingTeam, fromSelectedParticipant: Participant): BalancingTeamSet {
        if (!teams.contains(fromTeam))
            throw Exception("This team is not contained by this set")

        if (size < 1)
            throw Exception("The team set is empty")

        val teamList = teams.toList()
        var selectedTeam = teamList[(0 until size).random()]

        while (selectedTeam == fromTeam)
            selectedTeam = teamList[(0 until size).random()]

        val selectedParticipant = selectedTeam.participants.toList()[(0 until selectedTeam.size).random()]
        val newTeams = teams.toMutableSet()

        val t1 = fromTeam.swapParticipants(fromSelectedParticipant, selectedParticipant)
        val t2 = selectedTeam.swapParticipants(selectedParticipant, fromSelectedParticipant)

        newTeams.remove(fromTeam)
        newTeams.remove(selectedTeam)
        newTeams.add(t1)
        newTeams.add(t2)

        return BalancingTeamSet(newTeams)
    }

    fun generateNeighbors(): List<BalancingTeamSet> {
        if (size == 0)
            throw Exception("Cannot generate neighborhood from empty set")

        val neighbors = ArrayList<BalancingTeamSet>(size * (teams.first().size))

        for (t in teams) {
            for (p in t.participants) {
                val newTeam = applyRandomSwap(t, p)
                neighbors.add(newTeam)
            }
        }

        return neighbors
    }

    companion object {

        fun fromParticipants(participants: List<Participant>, nbPerTeam: Int): BalancingTeamSet {
            var i = 0
            val teams = HashSet<BalancingTeam>()
            var nbFullTeam: Int = participants.size / nbPerTeam

            if (participants.size % nbPerTeam != 0)
                --nbFullTeam

            while (i < participants.size && teams.size < nbFullTeam) {
                teams.add(
                    BalancingTeam(
                    teams.size.toLong(), participants.subList(i, i + nbPerTeam).toSet()
                )
                )
                i += nbPerTeam
            }

            val remainingParticipants = participants.size - teams.size * nbPerTeam
            if (remainingParticipants != 0) {
                val mid: Int = remainingParticipants / 2
                teams.add(
                    BalancingTeam(
                    teams.size.toLong(), participants.subList(i, i + remainingParticipants - mid).toSet()
                )
                )
                i += remainingParticipants - mid
                teams.add(
                    BalancingTeam(
                    teams.size.toLong(), participants.subList(i, participants.size).toSet()
                )
                )
            }

            return BalancingTeamSet(teams)
        }
    }
}