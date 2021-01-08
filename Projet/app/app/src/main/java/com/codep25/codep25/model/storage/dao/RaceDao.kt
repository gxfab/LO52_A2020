package com.codep25.codep25.model.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.codep25.codep25.model.entity.*

@Dao
interface RaceDao {

    @Query("SELECT * FROM race")
    fun getRaces(): List<Race>

    @Query("SELECT * FROM race WHERE race_id == :id")
    fun getRaceById(id: Long): Race

    @Query("SELECT * FROM racing_team WHERE race_id == :id")
    fun getRacingTeamsByRaceId(id: Long): List<RacingTeamEntity>

    @Query("SELECT * FROM racing_team_link WHERE team_id == :id")
    fun getParticipantsForTeam(id: Long): List<RacingTeamLinkEntity>

    @Query("SELECT * FROM race_time")
    fun getTimes(): List<RaceTimeEntity>

    @Query("SELECT * FROM race_time WHERE team_id == :id")
    fun getTimesForTeam(id: Long): List<RaceTimeEntity>

    @Insert
    fun insertRace(race: Race): Long

    @Insert
    fun insertRacingTeam(racingTeamEntity: RacingTeamEntity): Long

    @Insert
    fun insertRacingTeamLinks(vararg racingTeamLinkEntity: RacingTeamLinkEntity)

    @Insert
    fun insertRaceTimes(vararg raceTimeEntity: RaceTimeEntity)
}