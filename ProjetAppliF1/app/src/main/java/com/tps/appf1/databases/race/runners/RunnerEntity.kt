package com.tps.appf1.databases.race.runners

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Runners Table")
data class RunnerEntity (
    @PrimaryKey(autoGenerate = true)  //This guarantees that the ID for each entity is unique.
    val RunnerID: Long = 0L,

    @ColumnInfo(name = "Ordre")
    var RunnerOrder: Int = 0,

    @ColumnInfo(name = "Nom")
    var RunnerName: String = "none",

    @ColumnInfo(name = "Prénom")
    var RunnerSurname: String = "none",

    @ColumnInfo(name = "Niveau")
    var RunnerLevel: Int = 0,

    @ColumnInfo(name = "Tps Sprint 1")
    var RunnerSprint1: String = "none",         //Temps en seconde converti en string formattée avant d'être stocké dans la db

    @ColumnInfo(name = "Tps Obstacle 1")
    var RunnerObstacle1: String = "none",

    @ColumnInfo(name = "Tps Pitstop 1")
    var RunnerPitstop1: String = "none",

    @ColumnInfo(name = "Tps Sprint 2")
    var RunnerSprint2: String = "none",

    @ColumnInfo(name = "Tps Obstacle 2")
    var RunnerObstacle2: String = "none",

    @ColumnInfo(name = "Tps Cycle")
    var RunnerCycle: String = "none",      // = somme de tous les ateliers.
)