package BDD.interfaceDAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;

@Dao
public interface RunnerDao
{
    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
}
