package com.example.traineygpt.DataBase.Dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.traineygpt.Model.Hotel;
import com.example.traineygpt.Model.ReservedHotel;

import java.util.List;

@Dao
public interface ReservedHotelDao {

    @Insert
    void Insert(ReservedHotel hotel);

    @Delete
    void Delete(ReservedHotel hotel);

    @Update
    void Update(ReservedHotel hotel);

    @Query("DELETE FROM ReservedHotels")
    void DeleteAll();

    @Query("SELECT * FROM ReservedHotels")
    LiveData<List<ReservedHotel>> getHotels();

}
