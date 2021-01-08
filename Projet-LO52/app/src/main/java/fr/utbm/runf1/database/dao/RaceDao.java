package fr.utbm.runf1.database.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import fr.utbm.runf1.entities.Race;

/**
 * Created by Yosef B.I.
 */
@Dao
public interface RaceDao {
    @Insert
    long insertRace(Race race);

    @Query("SELECT * FROM Race")
    LiveData<List<Race>> getAllRaces();

    @Query("SELECT * FROM race ORDER BY race_id DESC LIMIT 1")
    LiveData<Race> getLastInsertedRace();

    @Query("DELETE FROM Race; ")
    void clearTable();
}
