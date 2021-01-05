package com.tps.appf1.databases.race.runners

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Runners Table")
data class RunnerEntity (
    @PrimaryKey(autoGenerate = true)  //This guarantees that the ID for each entity is unique.
    var RunnerID: Long = 0L,

    @ColumnInfo(name = "Nom")
    val RunnerName: String = "none",

    @ColumnInfo(name = "Prénom")
    val RunnerSurname: String = "none",

    @ColumnInfo(name = "Niveau")
    val RunnerLevel: Int = 0,

    @ColumnInfo(name = "Tps Sprint 1")
    val RunnerSprint1: String = "none",         //Temps en seconde converti en string formattée avant d'être stocké dans la db

    @ColumnInfo(name = "Tps Obstacle 1")
    val RunnerObstacle1: String = "none",

    @ColumnInfo(name = "Tps Pitstop 1")
    val RunnerPitstop1: String = "none",

    @ColumnInfo(name = "Tps Sprint 2")
    val RunnerSprint2: String = "none",

    @ColumnInfo(name = "Tps Obstacle 2")
    val RunnerObstacle2: String = "none",

    @ColumnInfo(name = "Tps Cycle")
    val RunnerCycle: String = "none",      // = somme de tous les ateliers.
)