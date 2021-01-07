package com.example.gestion_course.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.gestion_course.entities.Equipe
import com.example.gestion_course.entities.Etape
import com.example.gestion_course.entities.Participant

@Dao
interface EtapeDao {

    @Query("SELECT * FROM etape")
    fun getAll(): List<Etape>

    @Insert
    fun insertEtapes(etapes: List<Etape>)

    @Query("DELETE FROM Etape")
    fun clearEtape()

    @Query("INSERT INTO Etape VALUES\n" +
            "(1, 'Sprint 1'),\n" +
            "(2, 'Obstacle 1'),\n" +
            "(3, 'pitStop'),\n" +
            "(4, 'Sprint 2'),\n" +
            "(5, 'Obstacle 2'),\n" +
            "(6, 'Repos');\n")
            fun insertAllEtapes()
}