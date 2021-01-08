package com.utbm.nouassi.manou.coursecodep25.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Course(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "titre")
    val titre: String,

    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name = "duree")
    var duree: Int
)