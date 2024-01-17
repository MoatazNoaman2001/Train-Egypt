package com.example.traineygpt.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.traineygpt.DataBase.Conerters.ArrayConverter;
import com.example.traineygpt.DataBase.Dao.HotelDao;
import com.example.traineygpt.Model.Hotel;

import java.util.Arrays;

@Database(entities = {Hotel.class} , version = 2)
@TypeConverters({ArrayConverter.class})
public abstract class BlogDataBase extends RoomDatabase {
    private static BlogDataBase dataBase;
    public abstract HotelDao hotelDao();

    public static synchronized BlogDataBase getDataBase(Context context){
        if (dataBase == null){
            dataBase = Room.databaseBuilder(context ,
                    BlogDataBase.class , "Blog DataBase")
                    .fallbackToDestructiveMigration()

                    .addTypeConverter(new ArrayConverter())
                    .build();
        }
        return dataBase;
    }


}
