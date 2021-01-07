package com.example.gestion_course.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.gestion_course.entities.Etape
import com.example.gestion_course.entities.Participant
import com.example.gestion_course.entities.Temps

@Dao
interface TempsDao {
    @Insert
    fun insertTemps(temps: Temps)

    @Query("DELETE FROM Temps")
    fun deleteData()
}