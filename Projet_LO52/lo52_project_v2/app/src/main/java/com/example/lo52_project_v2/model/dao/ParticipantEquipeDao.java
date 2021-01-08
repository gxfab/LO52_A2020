package com.example.lo52_project_v2.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;

import com.example.lo52_project_v2.model.bo.ParticipantEquipe;

import java.util.List;

@Dao
public interface ParticipantEquipeDao {
    @Insert
    public void insertParticipantEquipe(ParticipantEquipe pe);

    @Insert
    public long[] insertParticipantEquipe(ParticipantEquipe... pes);

    @Insert
    public long[] insertParticipantEquipe(List<ParticipantEquipe> participant_equipes);

    @Delete
    public void delete(ParticipantEquipe participant_equipe);

    @Delete
    public void delete(ParticipantEquipe... participant_equipes);

    @Delete
    public void delete(List<ParticipantEquipe> participant_equipes);
}
