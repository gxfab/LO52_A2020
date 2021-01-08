package com.example.lo52_project_v2.model.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.lo52_project_v2.model.bo.EquipeParticipants;
import com.example.lo52_project_v2.model.bo.Tour;

import java.util.List;

@Dao
public interface TourDao {
    @Insert
    public void insertTour(Tour tour);

    @Insert
    public void insertTour(Tour... tours);

    @Insert
    public void insertTour(List<Tour> tours);

    @Transaction
    @Query("SELECT * From Tour  "+
            "Inner Join ParticipantEquipe on ParticipantEquipe.id = Tour.idParticipant"+
            " where ParticipantEquipe.idEquipe LIKE :idTeam "+
            "Order by ParticipantEquipe.ordreDePassage ")
    List<Tour> getCorrespondingsRounds(int idTeam);

    @Transaction
    @Query("SELECT * From Tour  "+
            "where Tour.idParticipant Like :idParticipant"+
    " And Tour.idCourse Like :idCourse")
    List<Tour> getCorr_Rounds(int idParticipant, int idCourse);
}
