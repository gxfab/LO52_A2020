package com.codep25.codep25.model.storage.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.codep25.codep25.model.converter.RacingStateConverter
import com.codep25.codep25.model.entity.*
import com.codep25.codep25.model.storage.dao.ParticipantDao
import com.codep25.codep25.model.storage.dao.RaceDao
import com.codep25.codep25.model.storage.dao.TeamDao

@Database(entities = [
    Participant::class,
    Team::class,
    Race::class,
    RaceTimeEntity::class,
    RacingTeamEntity::class,
    RacingTeamLinkEntity::class
], version = 1)
@TypeConverters(RacingStateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun participantDao(): ParticipantDao
    abstract fun teamDao(): TeamDao
    abstract fun raceDao(): RaceDao
}