package com.utbm.nouassi.manou.coursecodep25.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.util.ArrayList

@Entity(primaryKeys = ["participantId", "equipeId"])
data class Coureur (
    
    @ColumnInfo(name = "nom")
    val nom: String,

    @ColumnInfo(name = "niveau")
    val niveau: Int,

    //Les valeurs sont en seconde
    @ColumnInfo(name = "nb_tour_sans_atelier1")
    var nbTourSansAtelier1: Int,

    @ColumnInfo(name = "nb_tour_avec_atelier1")
    var nbTourAvecAtelier1: Int,

    @ColumnInfo(name = "nb_pit_stop")
    var nbPitStop: Int,

    @ColumnInfo(name = "nb_tour_sans_atelier2")
    var nbTourSansAtelier2: Int,

    @ColumnInfo(name = "nb_tour_avec_atelier2")
    var nbTourAvecAtelier2: Int,

    val participantId: Int,

    val equipeId: Int
)