package com.utbm.nouassi.manou.coursecodep25.database.dao

import androidx.room.*
import com.utbm.nouassi.manou.coursecodep25.database.entity.Equipe
import com.utbm.nouassi.manou.coursecodep25.database.entity.EquipeAndCoureur

@Dao
interface EquipeDao {
    @Insert
    fun insertAll(vararg equipes: Equipe)

    @Insert
    fun insert(equipe: Equipe): Long

    @Delete
    fun delete(equipe: Equipe)

    @Update
    fun updateEquipes(vararg equipes: Equipe)

    @Transaction
    @Query("SELECT * FROM equipe")
    fun getAll(): List<EquipeAndCoureur>

    @Transaction
    @Query("SELECT * FROM equipe where courseId = :id")
    fun getByCourseId(id: Int): List<EquipeAndCoureur>

}