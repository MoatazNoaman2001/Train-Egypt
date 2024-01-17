package com.example.traineygpt.DataBase;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.traineygpt.DataBase.Conerters.ArrayConverter;
import com.example.traineygpt.DataBase.Conerters.DateConverter;
import com.example.traineygpt.DataBase.Conerters.DurationConverter;
import com.example.traineygpt.DataBase.Dao.HotelDao;
import com.example.traineygpt.DataBase.Dao.ReservedHotelDao;
import com.example.traineygpt.DataBase.Dao.TicketDao;
import com.example.traineygpt.Model.Hotel;
import com.example.traineygpt.Model.ReservedHotel;
import com.example.traineygpt.Model.Ticket;

@Database(entities = {Ticket.class, ReservedHotel.class}, version = 1)
@TypeConverters({DurationConverter.class , DateConverter.class , ArrayConverter.class})
public abstract class PaidTickets extends RoomDatabase {
    private static PaidTickets tickets;

    public abstract TicketDao ticketDao();
    public abstract ReservedHotelDao hotelDao();

    public static synchronized PaidTickets getInstance(Context context) {
        if (tickets == null) {
            tickets = Room.databaseBuilder(context
                    , PaidTickets.class, "paid tickets")
                    .fallbackToDestructiveMigration()
                    .addTypeConverter(new ArrayConverter())
                    .addTypeConverter(new DateConverter())
                    .addTypeConverter(new DurationConverter())
                    .build();
        }

        return tickets;
    }

}
