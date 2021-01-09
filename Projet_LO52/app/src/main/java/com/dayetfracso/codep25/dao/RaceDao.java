package com.dayetfracso.codep25.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.dayetfracso.codep25.entity.Race;
import com.dayetfracso.codep25.entity.RaceTeamCrossRef;
import com.dayetfracso.codep25.entity.RaceWithTeams;

import java.util.List;

@Dao
public interface RaceDao {
    @Insert
    long insertRace(Race race);

    @Insert
    long insertRaceTeamCrossRef(RaceTeamCrossRef crossRef);

    @Transaction
    @Query("SELECT * FROM races")
    public List<Race> getAllRaces();

    @Transaction
    @Query("DELETE FROM races")
    public void deleteAllRaces();

    @Transaction
    @Query("SELECT * FROM races where raceId = :raceId")
    public Race getRace(long raceId);

    @Transaction
    @Query("SELECT * FROM races ORDER BY raceId DESC LIMIT 1")
    public RaceWithTeams getLastRaceWithTeams();

    @Transaction
    @Query("SELECT * FROM races")
    public List<RaceWithTeams> getRacesWithTeams();

    @Transaction
    @Query("SELECT * FROM races where raceId = :raceId")
    public List<RaceWithTeams> getRaceWithTeams(long raceId);
}
