package dk.witzell.jobsearchonline.rooms;

/*  This is heavily inspired from the teachers example (Situation Room) */

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static Date fromTimestamp(Long value){
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
