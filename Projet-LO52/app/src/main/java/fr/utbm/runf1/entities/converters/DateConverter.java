package fr.utbm.runf1.entities.converters;

import java.util.Date;

import androidx.room.TypeConverter;

/**
 * Created by Yosef B.I.
 */
public class DateConverter {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
