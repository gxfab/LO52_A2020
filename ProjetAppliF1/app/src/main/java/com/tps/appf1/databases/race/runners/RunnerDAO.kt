package com.tps.appf1.databases.race.runners

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.tps.appf1.databases.race.race.RaceEntity

@Dao
interface RunnerDAO {
    @Insert
    fun insertRunner(runnerEntity: RunnerEntity) : Long //Take an instance of the Runner class as arg, return rowID value

    @Update
    fun updateRunnerTime(runnerEntity: RunnerEntity) {  //The entity that's updated is the entity that has the same key as the one that's passed in
        //Pour ajouter les temps du joueur dans la db
        //Peut etre passer en argument un identifier du numéro atelier
    }
    @Query("SELECT RunnerID FROM `runners table` WHERE Niveau = :level")
    fun getRunnerID(level: Int): Int {/*Random pick to do if 2 same level*/return 0}

    @Query("SELECT Niveau FROM `runners table` ORDER BY Niveau")  //get the list of runners level, and order it. Used to create fair teams
    fun getLevelList(): List<Int>

    @Query("DELETE FROM `runners table`")
    fun deleteALL()

    @Query("SELECT COUNT(*) FROM `runners table`")  //
    fun count(): Int


    //@Query("SELECT * FROM todoentity WHERE title LIKE :title")
    //fun findByTitle(title: String): LiveData<List<TodoEntity>>
    // -> Use LiveData to view the database in an activity (pour les classements par exemple)
}