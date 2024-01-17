package com.example.traineygpt.DataBase.Dao;


import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.traineygpt.Model.Hotel;

import java.util.List;
import java.util.ListIterator;

@Dao
public interface HotelDao {

    @Insert
    Void Insert(Hotel hotel);

    @Delete
    Void Delete(Hotel hotel);

    @Update
    Void Update(Hotel hotel);

    @Query("DELETE FROM hotal")
    Void DeleteAll();

    @Query("SELECT * FROM hotal")
    LiveData<List<Hotel>> getHotels();

    @Query("SELECT * FROM  hotal WHERE City =:searchQuery OR name =:searchQuery")
    LiveData<List<Hotel>> getHotels(String searchQuery);
}
