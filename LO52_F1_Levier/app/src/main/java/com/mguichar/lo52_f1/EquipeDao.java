package com.mguichar.lo52_f1;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface EquipeDao {

    @Insert(onConflict = REPLACE)
    void insert(Equipe equipe);

    @Delete
    void reset(List<Equipe> equipes);

    @Query("SELECT * FROM equipes")
    List<Equipe> getAll();

    @Query("SELECT * FROM equipes WHERE nom = :nom")
    List<Equipe> getEquipeByName(String nom);

}