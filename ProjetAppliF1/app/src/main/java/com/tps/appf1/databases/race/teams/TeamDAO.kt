package com.tps.appf1.databases.race.teams

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update

@Dao
interface TeamDAO {
    @Insert
    fun insert(teamEntity: TeamEntity) : Long  //Take an instance of the Team class as arg and return the rowID value

    @Update
    fun update(teamEntity: TeamEntity)  //The entity that's updated is the entity that has the same key as the one that's passed in

}