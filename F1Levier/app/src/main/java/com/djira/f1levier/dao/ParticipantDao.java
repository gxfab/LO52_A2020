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
public interface ParticipantDao {
    //Insert query
    @Insert(onConflict = REPLACE)
    void insert(Participant participant);

    //Delete query
    @Delete
    void delete(Participant participant);

    //Delete all query
    @Delete
    void reset(List<Participant> participant);

    //Update query
    @Query("UPDATE Participant SET name = :sName, level = :sLevel WHERE ID = :sID")
    void update(int sID, String sName, Integer sLevel);

    //Update query
    @Query("UPDATE Participant SET equipe_id = :sEquipeID WHERE ID = :sID")
    void updateEquipe(int sID, Integer sEquipeID);

    //Update query
    @Query("UPDATE Participant SET equipe_id = 0 WHERE equipe_id != 0")
    void deleteFromEquipe();

    //Update query
    @Query("UPDATE Participant SET t1 = :sT1, t1 = :sT2, ps = :sPs, t3 = :sT3, t4 = :sT4 WHERE ID = :sID")
    void updateTemps(int sID, int sT1,int sT2,int sPs,int sT3,int sT4 );

    //Get all data query
    @Query("SELECT * FROM Participant")
    List<Participant> getAll();

    //Get all data query
    @Query("SELECT * FROM Participant ORDER BY level ASC")
    List<Participant> getAllByLevel();

    //Get participant by equipe
    @Query("SELECT Participant.* FROM Participant " +
            "INNER JOIN Equipe " +
            "ON Participant.equipe_id = Equipe.ID WHERE Equipe.name = :eqName")
    List<Participant> getAllByEquipe(String eqName);

    //Get participant by equipe
    @Query("SELECT Participant.*, " +
            "SUM(Participant.t1 + Participant.t2 + Participant.ps + Participant.t3 + Participant.t4) AS temps FROM Participant " +
            "INNER JOIN Equipe " +
            "ON Participant.equipe_id = Equipe.ID WHERE Equipe.ID = :eqID " +
            "GROUP BY temps, Participant.ID, Participant.name, " +
            "Participant.level, Participant.t1, Participant.t2," +
            " Participant.ps, Participant.t3, Participant.t4, Participant.equipe_id " +
            "ORDER BY temps ASC" +
            "")
    List<Participant> getAllByEquipeID(Integer eqID);

    //Get participant by equipe
    @Query("SELECT Equipe.* FROM Participant " +
            "INNER JOIN Equipe " +
            "ON Participant.equipe_id = Equipe.ID WHERE Participant.ID = :sID")
    Equipe getEquipeByParticipantID(Integer sID);

}
