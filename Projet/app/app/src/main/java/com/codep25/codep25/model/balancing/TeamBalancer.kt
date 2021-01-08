package com.codep25.codep25.model.balancing

import com.codep25.codep25.model.entity.Participant
import java.util.*

class TeamBalancer {
    companion object {
        fun balance(participants: List<Participant>, nbPerTeam: Int,
                    threshold: Int=-1, maxIterations: Int=200, tabuSize: Int=30
        ): BalancingTeamSet {
            if (participants.isEmpty())
                throw Exception("Cannot balance an empty team")

            var i = 0
            var sBest = BalancingTeamSet.fromParticipants(participants, nbPerTeam)
            var bestCandidate = sBest
            val tabuList = LinkedList<BalancingTeamSet>()
            tabuList.add(sBest)

            while (i < maxIterations && sBest.scoreGap > threshold) {
                val neighborhood = bestCandidate.generateNeighbors()
                bestCandidate = neighborhood.first()

                for (c in neighborhood) {
                    if ( c.scoreGap < bestCandidate.scoreGap && !tabuList.contains(c)) {
                        bestCandidate = c
                    }
                }

                if (bestCandidate.scoreGap < sBest.scoreGap)
                    sBest = bestCandidate

                tabuList.add(bestCandidate)

                if (tabuList.size > tabuSize)
                    tabuList.removeAt(0)

                ++i
            }

            return sBest
        }
    }
}