package com.example.lo52_project_v2.model.bo;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ParticipantTours {
    @Embedded
    public Participant participant;

    @Relation(
            parentColumn = "idParticipant",
            entityColumn = "idTour"
    )
    public List<Tour> tours;
}
