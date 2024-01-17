package com.example.traineygpt.UI;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.traineygpt.Model.Hotel;
import com.example.traineygpt.Repasotory.HotelRepasitory;

import java.util.List;

public class HotelMVVM extends AndroidViewModel {
    private HotelRepasitory repasitory;
    private LiveData<List<Hotel>> Hotels;
    private LiveData<List<Hotel>> SearchedHotels;

    public HotelMVVM(@NonNull Application application) {
        super(application);
        repasitory = new HotelRepasitory(application);
        Hotels = repasitory.getGetAllHotels();
    }

    public LiveData<List<Hotel>> SearchHotels(String str) {
        SearchedHotels = repasitory.SearchQuery(str);
        return SearchedHotels;
    }

    public LiveData<List<Hotel>> getHotels() {
        return Hotels;
    }

    public void InsertHotel(Hotel hotel) {
        repasitory.InsertHotel(hotel);
    }

    public void UpdateHotel(Hotel hotel) {
        repasitory.UpdateHotel(hotel);
    }

    public void DeleteHotel(Hotel hotel) {
        repasitory.DeleteHotel(hotel);
    }
}
