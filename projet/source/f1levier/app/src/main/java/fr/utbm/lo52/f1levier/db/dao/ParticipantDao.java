package fr.utbm.lo52.f1levier.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import fr.utbm.lo52.f1levier.db.entity.Participant;

@Dao
public interface ParticipantDao {
    @Query("SELECT * FROM Participant ORDER BY name")
    LiveData<List<Participant>> getAll();

    @Query("SELECT * FROM Participant WHERE id IN (:ids) ORDER BY name")
    List<Participant> getAllByIds(final int[] ids);

    @Query("SELECT * FROM Participant WHERE id = :id")
    Participant getById(final int id);

    @Insert
    void insert(Participant participant);
}
