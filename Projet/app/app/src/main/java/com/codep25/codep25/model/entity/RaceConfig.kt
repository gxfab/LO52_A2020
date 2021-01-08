package com.codep25.codep25.model.entity

import androidx.room.Entity

@Entity
data class RaceConfig(
    val nbTurnPerParticipant: Int,
    val nbOfRelay: Int
) {
    val totalNbOfSteps = (nbOfRelay + 1) * (nbTurnPerParticipant * RacingState.values().size - 1)
}