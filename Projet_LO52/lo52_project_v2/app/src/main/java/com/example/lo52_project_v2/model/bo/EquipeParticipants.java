package com.example.lo52_project_v2.model.bo;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class EquipeParticipants {

    @Embedded
    public Equipe equipe;

    @Relation(
            parentColumn = "idEquipe",
            //idParticipants
            entityColumn = "id",
            associateBy = @Junction(ParticipantEquipe.class)
    )
    public List<Participant> participants;
}
