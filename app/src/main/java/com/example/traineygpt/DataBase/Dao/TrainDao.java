package com.example.traineygpt.DataBase.Dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.traineygpt.Model.Ticket;
import com.example.traineygpt.Model.Train;

import java.util.List;

@Dao
public interface TrainDao {

    @Insert
    void AddTrain(Train train);

    @Delete
    void DeleteTrain(Train train);

    @Query("DELETE FROM train")
    Void DeleteAll();

    @Query("SELECT * FROM train")
    LiveData<List<Train>> getAllTrain();

    @Query("SELECT * FROM train WHERE Start_Destination == 'CAIRO' ORDER BY Start_Time ASC")
    LiveData<List<Train>> getCairoTickets();

    @Query("SELECT * FROM train WHERE Start_Destination == 'ALEXANDRIA' ORDER BY Start_Time ASC")
    LiveData<List<Train>> getALEXANDRIATickets();

    @Query("SELECT * FROM train WHERE Start_Destination == 'ASWAN' ORDER BY Start_Time ASC")
    LiveData<List<Train>> getASWANTickets();


}
