package com.example.traineygpt.Repasotory;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.traineygpt.DataBase.BlogDataBase;
import com.example.traineygpt.DataBase.Dao.HotelDao;
import com.example.traineygpt.Model.Hotel;
import com.google.android.material.appbar.AppBarLayout;

import java.util.List;

public class HotelRepasitory {

    private BlogDataBase dataBase;
    private HotelDao hotelDao;
    private LiveData<List<Hotel>> getAllHotels;

    public HotelRepasitory(Application application) {
        dataBase = BlogDataBase.getDataBase(application);
        hotelDao = dataBase.hotelDao();
        getAllHotels = hotelDao.getHotels();
    }

    public LiveData<List<Hotel>> SearchQuery(String str){
        return hotelDao.getHotels(str);
    }
    public void InsertHotel(Hotel hotel) {
        new InsertHotel().execute(hotel);
    }

    public void UpdateHotel(Hotel hotel) {
        new UpdateHotel().execute(hotel);
    }

    public void DeleteHotel(Hotel hotel) {
        new DeleteHotel().execute(hotel);
    }

    public LiveData<List<Hotel>> getGetAllHotels() {
        return getAllHotels;
    }

    class InsertHotel extends AsyncTask<Hotel, Void, Void> {
        @Override
        protected Void doInBackground(Hotel... hotels) {
            hotelDao.Insert(hotels[0]);
            return null;
        }
    }

    class UpdateHotel extends AsyncTask<Hotel, Void, Void> {
        @Override
        protected Void doInBackground(Hotel... hotels) {
            hotelDao.Update(hotels[0]);
            return null;
        }
    }

    class DeleteHotel extends AsyncTask<Hotel, Void, Void> {
        @Override
        protected Void doInBackground(Hotel... hotels) {
            hotelDao.Delete(hotels[0]);
            return null;
        }
    }
}
