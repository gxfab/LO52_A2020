package com.codep25.codep25.model.converter

import androidx.room.TypeConverter
import com.codep25.codep25.model.entity.RacingState

class RacingStateConverter {
    @TypeConverter
    fun toRacingState(value: String) = enumValueOf<RacingState>(value)

    @TypeConverter
    fun fromRacingState(value: RacingState) = value.name
}