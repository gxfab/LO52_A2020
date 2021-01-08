package com.utbm.nouassi.manou.coursecodep25.database.dao

import androidx.room.*
import com.utbm.nouassi.manou.coursecodep25.database.entity.Coureur

@Dao
interface CoureurDao {
    @Insert
    fun insertAll(vararg coureur: Coureur)

    @Insert
    fun insert(coureur: Coureur)

    @Delete
    fun delete(coureur: Coureur)

    @Update
    fun updateCoureur(vararg coureur: Coureur)

    @Query("SELECT * FROM coureur where participantId = :id ")
    fun getByParticipantId(id: Int): List<Coureur>

}