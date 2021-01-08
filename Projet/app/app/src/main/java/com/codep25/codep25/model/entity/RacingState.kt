package com.codep25.codep25.model.entity

enum class RacingState {
    SPRINT,
    HURDLE,
    PIT_STOP;

    fun getNext() : RacingState {
        return when (this) {
            SPRINT -> HURDLE
            HURDLE -> PIT_STOP
            PIT_STOP -> SPRINT
        }
    }
}