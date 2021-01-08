package com.example.lo52_project_v2.model.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.lo52_project_v2.model.bo.Participant;
import com.example.lo52_project_v2.model.bo.EquipeParticipants;
import com.example.lo52_project_v2.model.bo.ParticipantEquipe;

import java.util.List;


@Dao
public interface ParticipantDao {
    @Insert
    public void insertParticipant(Participant participant);

    @Query("SELECT * FROM participant")
    List<Participant> getAll();


    @Transaction
    @Query("SELECT * From Equipe where idEquipe LIKE :idEquipe")
    EquipeParticipants getCorrespondingParticipants(int idEquipe);

    @Query("SELECT * From ParticipantEquipe where idEquipe LIKE :idEquipe ")
    List<ParticipantEquipe> getCorrespondingsParticipantsOrder(int idEquipe);

    @Transaction
    @Query("SELECT * From Equipe INNER JOIN ParticipantEquipe ON Equipe.idEquipe = ParticipantEquipe.idEquipe where Equipe.idEquipe LIKE :idEquipe Order by ParticipantEquipe.ordreDePassage ")
    EquipeParticipants getCorrespondingsParticipantsinOrder(int idEquipe);

    @Update
    public void updateParticipantOrder(ParticipantEquipe ParticipantEquipe);

    @Query("SELECT * FROM participant WHERE nomParticipant LIKE :nomParticipant " +
            " LIMIT 1")
    Participant findByName(String nomParticipant);

    @Query("SELECT * FROM participant WHERE id LIKE :id " +
            " LIMIT 1")
    Participant findById(int id);

    @Update
    public void updateParticipant(Participant participant);

    @Delete
    public void deleteParticipant(Participant participant);
}
