package com.codep25.codep25.model.storage

import android.content.Context
import androidx.room.Room
import com.codep25.codep25.model.entity.Participant
import com.codep25.codep25.model.entity.ParticipantWithTeam
import com.codep25.codep25.model.entity.Team
import com.codep25.codep25.model.entity.TeamWithParticipants
import com.codep25.codep25.model.entity.*
import com.codep25.codep25.model.storage.db.AppDatabase

class DataBase constructor(_appCtx: Context) {
    private val db: AppDatabase = Room.databaseBuilder(
        _appCtx,
        AppDatabase::class.java, "codep25-database"
    ).build()

    fun getAllParticipants() = db.participantDao().getParticipants()

    fun getAllParticipantsWithTeam() : List<ParticipantWithTeam> {
        val pList = ArrayList<ParticipantWithTeam>()

        db.runInTransaction {
            val participants = db.participantDao().getParticipants()

            for (p in participants) {
                pList.add(
                    ParticipantWithTeam(
                    p,
                    if (p.id_fkteam != null) db.teamDao().getTeamById(p.id_fkteam) else null
                ))
            }
        }

        return pList
    }

    fun getNumberOfParticipants() =
        db.participantDao().getNumberOfParticipants()

    fun getParticipantById(id: Long) =
        db.participantDao().getParticipantById(id)

    fun getParticipantsOverLevel(lvl: Int) =
        db.participantDao().getParticipantsOverLevel(lvl)

    fun getParticipantsOrderedBySkill() = db.participantDao().getParticipantsOrderedBySkill()

    fun addParticipant(participant: Participant) =
        db.participantDao().insert(participant)

    fun addAllParticipants(vararg participants: Participant) =
        db.participantDao().insertAll(*participants)

    fun updateParticipant(participant: Participant) =
        db.participantDao().update(participant)

    fun updateAllParticipants(vararg participants: Participant) =
        db.participantDao().updateAll(*participants)
        
    fun updateMaxParticipantLevel(lvl: Int) =
        db.participantDao().updateMaxLevel(lvl)

    fun deleteParticipants(vararg ids: Long) =
        db.participantDao().delete(*ids)

    fun clearParticipants() = db.participantDao().clearParticipants()

    fun getAllTeams() = db.teamDao().getTeams()

    fun addTeam(team: Team) =
        db.teamDao().insert(team)

    fun addAllTeams(vararg teams: TeamWithParticipants) {
        db.runInTransaction {
            for (t in teams) {
                val teamId = db.teamDao().insert(t.team)
                val participants = t.participants.mapIndexed {i, it -> it.affectToTeam(teamId, i) }
                db.participantDao().updateAll(*participants.toTypedArray())
            }
        }
    }

    fun updateTeam(team: Team) =
        db.teamDao().update(team)

    fun deleteTeam(team: Team) =
        db.teamDao().delete(team)

    fun clearTeams() = db.teamDao().clearTeams()

    fun insertRaceWithTeams(raceWithTeams: RaceWithTeams): Long {
        var raceId = 0L
        db.runInTransaction {
            raceId = db.raceDao().insertRace(raceWithTeams.toRace())

            for (t in raceWithTeams.teams) {
                val teamId = db.raceDao().insertRacingTeam(t.toRacingTeamEntity(raceId))
                val links = t.participants.map {
                    RacingTeamLinkEntity(
                        id = 0,
                        racingTeamId = teamId,
                        participantId = it.id_participant
                    )
                }

                val times = t.times.map { it.toRaceTimeEntity(teamId) }
                db.raceDao().insertRacingTeamLinks(*links.toTypedArray())
                db.raceDao().insertRaceTimes(*times.toTypedArray())
            }
        }

        return raceId
    }

    fun getRaces() = db.raceDao().getRaces()

    fun getRaceWithTeamsById(raceId: Long): RaceWithTeams {
        var config: RaceConfig = RaceConfig(0,0)
        var racingTeams: ArrayList<RacingTeam> = arrayListOf()

        db.runInTransaction {
            val race = db.raceDao().getRaceById(raceId)
            val racingTeamEntities = db.raceDao().getRacingTeamsByRaceId(raceId)
            config = RaceConfig(race.nbTurnPerParticipant, race.nbOfRelay)
            racingTeams = ArrayList(racingTeamEntities.size)

            for (racingTeam in racingTeamEntities) {
                val racingTeamLinks = db.raceDao().getParticipantsForTeam(racingTeam.id)
                val participantIds = racingTeamLinks.map { it.participantId }.toLongArray()
                val participants = db.participantDao().getParticipantByIds(*participantIds)
                val rowTimes = db.raceDao().getTimesForTeam(racingTeam.id)
                val times = rowTimes.map {time -> time.toRacingTime(
                        participants.find { it.id_participant == time.participantId }!!
                    ) }

                racingTeams.add(RacingTeam.Factory.fromRacingTeamEntity(
                    racingTeam,
                    participants,
                    times
                ))

            }
        }

        return RaceWithTeams(config, racingTeams)
    }

    fun clear() = db.clearAllTables()
}