package com.example.gestion_course.dao

import androidx.room.*
import com.example.gestion_course.entities.Participant


@Dao
interface ParticipantDao {
    @Query("SELECT * FROM participant")
    fun getAll(): List<Participant>

    @Query("SELECT * FROM participant WHERE num_equipe_participant = :numEquipe")
    fun getParticipantsParEquipe(numEquipe: Int): List<Participant>

    @Query("SELECT * FROM participant WHERE num_participant IN (:numParticipant)")
    fun loadAllByIds(numParticipant: IntArray): List<Participant>

    @Query("SELECT * FROM participant WHERE num_participant = :numParticipant")
    fun getParticipantById(numParticipant: Int): Participant

    @Update
    fun updateList(participants: List<Participant?>?): Int

    @Query("UPDATE Participant SET num_etape_participant = :numEtape WHERE num_participant = :id")
    fun updateParticipant(id: Int, numEtape: Int)

    @Insert
    fun insertAll(vararg participants: Participant)

    @Delete
    fun delete(participant: Participant)
}