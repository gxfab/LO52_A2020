package com.codep25.codep25.model.storage

import android.content.SharedPreferences
import com.codep25.codep25.Config

class SharedPreferencesStorage constructor(_pref: SharedPreferences) : Storage {
    private val pref = _pref

    override fun getNumberOfParticipantsPerTeam() = pref.getInt(
        NB_PARTICIPANTS_PER_TEAM_KEY,
        Config.DEFAULT_NB_PARTICIPANTS_PER_TEAM
    )
    override fun setNumberOfParticipantsPerTeam(n: Int) {
        pref.edit()
            .putInt(NB_PARTICIPANTS_PER_TEAM_KEY, n)
            .apply()
    }

    override fun getNumberOfTurnsPerParticipant() = pref.getInt(
        NB_TURNS_PER_PARTICIPANT,
        Config.DEFAULT_NB_TURNS_PER_PARTICIPANT
    )

    override fun setNumberOfTurnPerParticipant(n: Int) {
        pref.edit()
            .putInt(NB_TURNS_PER_PARTICIPANT, n)
            .apply()
    }

    override fun getParticipantPositionColors(): List<Int>? {
        val colorString = pref.getString(PARTICIPANTS_POSITIONS_COLORS_KEY, null) ?: return null

        return colorString
            .split(",")
            .map { it.toInt() }
    }

    override fun setParticipantPositionColors(list: List<Int>) {
        pref.edit()
            .putString(PARTICIPANTS_POSITIONS_COLORS_KEY, list.joinToString(separator = ","))
            .apply()
    }

    override fun getMaxLevel(): Int = pref.getInt(
        MAX_LEVEL_KEY,
        Config.DEFAULT_MAX_LEVEL
    )

    override fun setMaxLevel(level: Int) {
        pref.edit()
            .putInt(MAX_LEVEL_KEY, level)
            .apply()
    }

    override fun shouldRecreateTeams(): Boolean = pref.getBoolean(
        SHOULD_RECREATE_TEAMS_KEY,
        false
    )

    override fun setShouldRecreateTeams(newVal: Boolean) {
        pref.edit()
            .putBoolean(SHOULD_RECREATE_TEAMS_KEY, newVal)
            .apply()
    }


    companion object {
        private const val NB_PARTICIPANTS_PER_TEAM_KEY = "nb_participants_per_team"
        private const val NB_TURNS_PER_PARTICIPANT = "nb_turn_per_participant"
        private const val PARTICIPANTS_POSITIONS_COLORS = "participants_positions_colors"
        private const val PARTICIPANTS_POSITIONS_COLORS_KEY = "participants_positions_colors"
        private const val MAX_LEVEL_KEY = "max_level"
        private const val SHOULD_RECREATE_TEAMS_KEY = "should_recreate_teams"
    }
}