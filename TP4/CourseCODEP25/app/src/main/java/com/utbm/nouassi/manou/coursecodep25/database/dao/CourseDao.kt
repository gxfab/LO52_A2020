package com.utbm.nouassi.manou.coursecodep25.database.dao

import androidx.room.*
import com.utbm.nouassi.manou.coursecodep25.database.entity.Course
import com.utbm.nouassi.manou.coursecodep25.database.entity.CourseAndEquipe

@Dao
interface CourseDao {
    @Insert
    fun insertAll(vararg courses: Course)

    @Insert
    fun insert(course: Course): Long

    @Delete
    fun delete(course: Course)

    @Update
    fun updateCourses(vararg courses: Course)

    @Query("SELECT min(c.duree) FROM course c ")
    fun getDureeMax(): Int

    @Query("SELECT max(c.duree) FROM course c")
    fun getDureeMin(): Int

    @Transaction
    @Query("SELECT * FROM course order by id desc")
    fun getAll(): List<CourseAndEquipe>


    @Query("SELECT * FROM course where id = :id")
    fun getById(id : Int): Course
}