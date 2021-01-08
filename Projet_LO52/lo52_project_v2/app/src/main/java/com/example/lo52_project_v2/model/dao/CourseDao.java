package com.example.lo52_project_v2.model.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.lo52_project_v2.model.bo.Course;

import java.util.List;

@Dao
public interface CourseDao {
    @Insert
    public long insertCourse(Course course);

    @Query("SELECT * FROM Course")
    public List<Course> getCourses();

    @Query("SELECT * FROM Course where idCourse Like :idCourse Limit 1")
    public Course getCourse(int idCourse);

    @Query("SELECT * FROM Course Where finie = 0")
    public List<Course> getAllunFinished();

    @Query("SELECT * FROM Course Where finie = 1")
    public List<Course> getAllFinished();

    @Update
    public void update(Course race);

    @Update
    public void update(Course... races);

    @Update
    public void update(List<Course> races);
}
