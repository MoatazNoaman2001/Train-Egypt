package com.example.traineygpt.DataBase.Conerters;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;

import java.time.Duration;

@ProvidedTypeConverter
public class DurationConverter {

    @TypeConverter
    public String toString(Duration duration){
        return duration.toString();
    }

    @TypeConverter
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Duration toDuration(String str){
        return Duration.parse(str);
    }

}
