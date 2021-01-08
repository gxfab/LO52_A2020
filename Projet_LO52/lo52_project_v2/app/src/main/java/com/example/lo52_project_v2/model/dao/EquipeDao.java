package com.example.lo52_project_v2.model.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.lo52_project_v2.model.bo.Equipe;

import java.util.List;

@Dao
public interface EquipeDao {
    @Insert
    public long insertEquipe(Equipe equipe);

    @Query("SELECT * FROM Equipe")
    List<Equipe> getAll();

    @Query("SELECT * FROM Equipe WHERE idCourse LIKE :idCourse ")
    List<Equipe> getCorrespondingTeams(int idCourse);

    @Update
    public void update(Equipe team);

    @Update
    public void update(Equipe... teams);

    @Update
    public void update(List<Equipe> teams);
}
