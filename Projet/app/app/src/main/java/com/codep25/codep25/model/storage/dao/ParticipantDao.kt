package com.codep25.codep25.model.storage.dao

import androidx.room.*
import com.codep25.codep25.model.entity.Participant
import com.codep25.codep25.model.entity.ParticipantWithTeam

@Dao
interface ParticipantDao {

    @Query("SELECT * FROM participants")
    fun getParticipants(): List<Participant>

    @Query("SELECT COUNT(*) FROM participants")
    fun getNumberOfParticipants(): Int

    @Query("SELECT * FROM participants WHERE id_participant == :id")
    fun getParticipantById(id: Long): Participant

    @Query("SELECT * FROM participants WHERE id_participant IN (:ids)")
    fun getParticipantByIds(vararg ids: Long): List<Participant>
    
    @Query("SELECT * FROM participants WHERE participant_level > :lvl")
    fun getParticipantsOverLevel(lvl: Int): List<Participant>

    @Query("SELECT * FROM participants ORDER BY participant_level ASC")
    fun getParticipantsOrderedBySkill(): List<Participant>

    @Insert
    fun insert(participant: Participant)

    @Insert
    fun insertAll(vararg participant: Participant): List<Long>

    @Update
    fun update(participant: Participant)

    @Update
    fun updateAll(vararg participant: Participant)
    
    @Query("UPDATE participants SET participant_level = :lvl WHERE participant_level > :lvl")
    fun updateMaxLevel(lvl: Int)

    @Query("DELETE FROM participants where id_participant IN (:ids)")
    fun delete(vararg ids: Long)

    @Query("DELETE FROM participants")
    fun clearParticipants()
}