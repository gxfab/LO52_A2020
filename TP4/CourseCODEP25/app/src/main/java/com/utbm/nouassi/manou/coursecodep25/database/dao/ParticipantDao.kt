package com.utbm.nouassi.manou.coursecodep25.database.dao

import androidx.room.*
import com.utbm.nouassi.manou.coursecodep25.database.entity.Participant

@Dao
interface ParticipantDao {
    @Insert
    fun insertAll(vararg participants: Participant)

    @Insert
    fun insert(participant: Participant) : Long

    @Delete
    fun delete(participant: Int)

    @Query("SELECT * FROM participant ORDER bY id desc")
    fun getAll(): List<Participant>

    @Update
    fun updateParticipants(vararg participants: Participant)

    @Query("SELECT * FROM participant where id = :id")
    fun getById(id : Int): Participant

    @Query("SELECT * FROM participant order by niveau desc")
    fun getAllOrderNiveauDesc(): List<Participant>
}