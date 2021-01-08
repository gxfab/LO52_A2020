package com.codep25.codep25.model.storage.dao

import androidx.room.*
import com.codep25.codep25.model.entity.Team
import com.codep25.codep25.model.entity.TeamWithParticipants

@Dao
interface TeamDao {

    @Query("SELECT * FROM teams")
    @Transaction
    fun getTeams(): List<TeamWithParticipants>

    @Query("SELECT * FROM teams WHERE id_team == :id")
    fun getTeamById(id: Long): TeamWithParticipants

    @Insert
    fun insert(team: Team): Long

    @Insert
    fun insertAll(vararg team: Team)

    @Update
    fun update(team: Team)

    @Delete
    fun delete(team: Team)

    @Query("DELETE FROM teams")
    fun clearTeams()
}