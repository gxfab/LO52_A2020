package com.utbm.nouassi.manou.coursecodep25.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Equipe (
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "numero")
    val numero: String,

    var niveau: Int,

    val courseId: Int
)