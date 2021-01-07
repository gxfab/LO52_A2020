package com.mguichar.lo52_f1;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface CoureurDao {

    @Insert(onConflict = REPLACE)
    void insert(Coureur coureur);

    @Delete
    void delete(Coureur coureur);

    @Delete
    void reset(List<Coureur> coureurs);

    @Query("UPDATE coureurs SET nom = :cnom , prenom = :cprenom, niveau = :cniveau WHERE coureur_ID = :cID")
    void update(int cID, String cnom, String cprenom, int cniveau);

    @Query("UPDATE coureurs SET sprint1 = :csprint1 WHERE coureur_ID = :cID")
    void updateSprint1(int cID, int csprint1);

    @Query("UPDATE coureurs SET nom_equipe = :cequipe WHERE coureur_ID = :cID")
    void setEquipeName(int cID, String cequipe);

    @Query("UPDATE coureurs SET sprint2 = :csprint2 WHERE coureur_ID = :cID")
    void updateSprint2(int cID, int csprint2);

    @Query("UPDATE coureurs SET pitstop = :cpitstop WHERE coureur_ID = :cID")
    void updatePitStop(int cID, int cpitstop);

    @Query("UPDATE coureurs SET obstacle1 = :cobstacle1 WHERE coureur_ID = :cID")
    void updateObstacle1(int cID, int cobstacle1);

    @Query("UPDATE coureurs SET obstacle2 = :cobstacle2 WHERE coureur_ID = :cID")
    void updateObstacle2(int cID, int cobstacle2);

    @Query("UPDATE COUREURS SET hasrun = :chasrun WHERE coureur_ID = :cID")
    void heHasRun(int cID, boolean chasrun);

    @Query("SELECT * FROM coureurs")
    List<Coureur> getAll();

    @Query("SELECT * from coureurs WHERE coureur_ID = :cID")
    List<Coureur> getCoureurById(int cID);
}
