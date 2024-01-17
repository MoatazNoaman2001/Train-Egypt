package com.example.traineygpt.DataBase.Conerters;

import androidx.room.Database;
import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;

import java.util.Date;

@ProvidedTypeConverter
public class DateConverter {

    @TypeConverter
    public String toString(Date date){
        return String.valueOf(date.getTime());
    }

    @TypeConverter
    public Date toDate(String str){
        return new Date(Long.parseLong(str));
    }

}
