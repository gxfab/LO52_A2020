package com.example.lo52_project_v2.model.bo;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class ParticipantEquipes {
    @Embedded
    public Participant participant;
    @Relation(
            //idParticipants
            parentColumn = "id",
            entityColumn = "idEquipe",
            associateBy = @Junction(ParticipantEquipe.class)
    )
    public List<Equipe> equipes;
}
