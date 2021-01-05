package com.tps.appf1.databases.race.teams

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update

@Dao
interface TeamDAO {
    @Insert
    fun insert(teamEntity: TeamEntity)  //Take an instance of the Race class as arg

    @Update
    fun update(teamEntity: TeamEntity)  //The entity that's updated is the entity that has the same key as the one that's passed in

    //@Query("")
    //fun get(key: Long): Race?  //fun for specific SQL query, to add later if necessary. Use ":key" to refer the function argument in the query.
}