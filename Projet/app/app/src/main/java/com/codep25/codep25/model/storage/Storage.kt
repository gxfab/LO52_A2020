package com.codep25.codep25.model.storage

interface Storage {
    fun getNumberOfParticipantsPerTeam(): Int
    fun setNumberOfParticipantsPerTeam(n: Int)

    fun getNumberOfTurnsPerParticipant(): Int
    fun setNumberOfTurnPerParticipant(n: Int)

    fun getParticipantPositionColors(): List<Int>?
    fun setParticipantPositionColors(list: List<Int>)

    fun getMaxLevel(): Int
    fun setMaxLevel(level: Int)

    fun shouldRecreateTeams(): Boolean
    fun setShouldRecreateTeams(newVal: Boolean)
}