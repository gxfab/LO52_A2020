package com.djira.f1levier.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.djira.f1levier.entity.Equipe;
import com.djira.f1levier.entity.Participant;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface EquipeDao {
    //Insert query
    @Insert(onConflict = REPLACE)
    void insert(Equipe equipe);

    //Delete query
    @Delete
    void delete(Equipe equipe);

    //Delete all query
    @Delete
    void reset(List<Equipe> equipe);

    //Get all data query
    @Query("SELECT Equipe.* FROM Equipe WHERE name = :sNom")
    Equipe getByNom(String sNom);

    //Get all data query
    @Query("SELECT Equipe.* FROM Equipe WHERE temps = :sTemps")
    Equipe getByTemps(Integer sTemps);

    //Update query
    @Query("UPDATE Equipe SET level = :sLevel WHERE ID = :sID")
    void updateLevel(int sID, Integer sLevel);

    //Update query
    @Query("UPDATE Equipe SET temps = :sTemps WHERE ID = :sID")
    void updateTemps(int sID, Integer sTemps);

    //Get all data query
    @Query("SELECT * FROM Equipe ORDER BY temps ASC")
    List<Equipe> getAllByTemps();

    //Get all data query
    @Query("SELECT * FROM Equipe ORDER BY level ASC")
    List<Equipe> getAllByLevel();

    //Get all data query
    @Query("SELECT * FROM Equipe")
    List<Equipe> getAll();

}
