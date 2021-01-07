package com.example.gestion_course.dao

import androidx.room.*
import com.example.gestion_course.entities.Equipe
import com.example.gestion_course.entities.EquipeAvecParticipants
import com.example.gestion_course.entities.Participant

@Dao
interface EquipeDao {
    @Query("SELECT * FROM equipe")
    fun getAll(): List<Equipe>

    @Transaction
    @Query("SELECT * FROM Equipe INNER JOIN Participant WHERE Equipe.num_equipe = Participant.num_equipe_participant GROUP BY num_equipe_participant")
    fun getEquipeAvecParticipants(): List<EquipeAvecParticipants>

    @Insert
    suspend fun insertAll(vararg equipes: Equipe)

    @Insert
    suspend fun insertEquipeAvecParticipants(equipe: Equipe, participants: List<Participant>)

    @Insert
    fun insertParticipants(participants: List<Participant>)

    @Delete
    fun delete(equipe: Equipe)

}