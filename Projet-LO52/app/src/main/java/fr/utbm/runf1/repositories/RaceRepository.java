package fr.utbm.runf1.repositories;

import java.util.List;

import androidx.lifecycle.LiveData;
import fr.utbm.runf1.database.dao.RaceDao;
import fr.utbm.runf1.entities.Race;

/**
 * Created by Yosef B.I.
 */
public class RaceRepository {

    private final RaceDao raceDao;

    public RaceRepository(RaceDao raceDao) {
        this.raceDao = raceDao;
    }

    public long insertRace(Race race) {
        return this.raceDao.insertRace(race);
    }

    public LiveData<Race> getLastInsertedRace() {
        return this.raceDao.getLastInsertedRace();
    }

    public LiveData<List<Race>> getAllRaces() {
        return this.raceDao.getAllRaces();
    }

    public void clearTable() {
        this.raceDao.clearTable();
    }
}
