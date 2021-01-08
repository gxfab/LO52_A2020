package com.codep25.codep25.model.entity

data class SimpleParticipant (
    val name: String,
    val level: Int
) {
    companion object {
        fun fromParticipant(p: Participant): SimpleParticipant {
            return SimpleParticipant(
                p.name, p.level
            )
        }
    }
}