package com.example.traineygpt.DataBase.Dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.traineygpt.Model.Ticket;

import java.util.List;

@Dao
public interface TicketDao {

    @Insert
    void InsertTicket(Ticket ticket);

    @Update
    void UpdateTicket(Ticket ticket);

    @Delete
    void DeleteTicket(Ticket ticket);

    @Query("DELETE FROM Tickets")
    void DeleteAll();

    @Query("SELECT * FROM Tickets")
    LiveData<List<Ticket>> getAllTickect();

    @Query("SELECT * FROM Tickets WHERE Start_Destination == 'ASSIUT' ORDER BY Start_Time ASC")
    LiveData<List<Ticket>> getAssuitTickets();

    @Query("SELECT * FROM Tickets WHERE Start_Destination == 'CAIRO' ORDER BY Start_Time ASC")
    LiveData<List<Ticket>> getCairoTickets();

    @Query("SELECT * FROM Tickets WHERE Start_Destination == 'ALEXANDRIA' ORDER BY Start_Time ASC")
    LiveData<List<Ticket>> getALEXANDRIATickets();

    @Query("SELECT * FROM Tickets WHERE Start_Destination == 'ASWAN' ORDER BY Start_Time ASC")
    LiveData<List<Ticket>> getASWANTickets();

    @Query("SELECT * FROM Tickets WHERE Start_Destination == 'SOHAG' ORDER BY Start_Time ASC")
    LiveData<List<Ticket>> getSOHAGTickets();

    @Query("SELECT * FROM Tickets WHERE Start_Destination == 'LUXOR' ORDER BY Start_Time ASC")
    LiveData<List<Ticket>> getLUXORTickets();


    @Query("SELECT * FROM Tickets WHERE Start_Destination = :start  AND Termination_Station = :end ORDER BY TrainTicketDateTime ASC")
    LiveData<List<Ticket>> searchQuary(String start , String end);


}
