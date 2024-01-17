package com.example.traineygpt.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.traineygpt.DataBase.Conerters.ArrayConverter;
import com.example.traineygpt.DataBase.Conerters.DateConverter;
import com.example.traineygpt.DataBase.Conerters.DurationConverter;
import com.example.traineygpt.DataBase.Dao.TicketDao;
import com.example.traineygpt.DataBase.Dao.TrainDao;
import com.example.traineygpt.Model.Ticket;
import com.example.traineygpt.Model.Train;

@Database(entities = {Ticket.class , Train.class}, version = 1)
@TypeConverters({DurationConverter.class , DateConverter.class , ArrayConverter.class})
public abstract class TicketsDataBase extends RoomDatabase {

    private static TicketsDataBase dataBase;
    public abstract TicketDao ticketDao();
    public abstract TrainDao trainDao();

    public static synchronized TicketsDataBase getDataBase(Context context) {
        if (dataBase == null) {
            dataBase = Room.databaseBuilder(context, TicketsDataBase.class
                    , "TicketDataBase")
                    .addTypeConverter(new DurationConverter())
                    .addTypeConverter(new DateConverter())
                    .addTypeConverter(new ArrayConverter())
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return dataBase;
    }

}
