package fr.utbm.lo52.f1levier.db.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.Date;
import java.util.List;

import fr.utbm.lo52.f1levier.db.entity.Race;

@Dao
public interface RaceDao {

    @Query("SELECT * FROM Race WHERE id = :id")
    Race getById(final int id);

    @Query("SELECT * FROM Race WHERE started_at IS NULL ORDER BY name")
    LiveData<List<Race>> getAllNotStarted();

    @Query("SELECT * FROM Race WHERE started_at IS NOT NULL ORDER BY name")
    LiveData<List<Race>> getAllStarted();

    @Query("SELECT * FROM Race WHERE finished_at IS NOT NULL ORDER BY name")
    LiveData<List<Race>> getAllFinished();

    @Insert
    Long insert(Race race);

    @Query("UPDATE Race SET started_at = :datetime WHERE id = :raceId")
    void startRace(int raceId, Date datetime);

    @Query("UPDATE Race SET finished_at = :datetime WHERE id = :raceId")
    void finishRace(int raceId, Date datetime);
}
