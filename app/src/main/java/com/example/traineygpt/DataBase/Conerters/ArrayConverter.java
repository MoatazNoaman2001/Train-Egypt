package com.example.traineygpt.DataBase.Conerters;

import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.Arrays;

@ProvidedTypeConverter
public class ArrayConverter {
    @TypeConverter
    public String ToString(String[] strs) {
        return Arrays.toString(strs);
    }

    @TypeConverter
    public String[] toArray(String str) {
        String[]strings = str.substring(1
                , str.length() - 1).split(", ");
        return strings;
    }
}
