package com.example.traineygpt.Repasotory;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.traineygpt.DataBase.TicketsDataBase;
import com.example.traineygpt.DataBase.Dao.TrainDao;
import com.example.traineygpt.Model.Train;

import java.util.List;

public class TrainRepository {
    private TicketsDataBase dataBase;
    private TrainDao trainDao;
    private LiveData<List<Train>> AllTrains;

    public TrainRepository(Application application) {
        dataBase = TicketsDataBase.getDataBase(application);
        trainDao = dataBase.trainDao();
        AllTrains = trainDao.getAllTrain();
    }

    public LiveData<List<Train>> getAllTrains() {
        return AllTrains;
    }

    public void AddTrain(Train train){
        new AddTrain(trainDao).execute(train);
    }

    public void DeleteTrain(Train train){
        new DeleteTrain(trainDao).execute(train);
    }
    class AddTrain extends AsyncTask<Train , Void ,Void>{
        TrainDao trainDao;

        public AddTrain(TrainDao trainDao) {
            this.trainDao = trainDao;
        }

        @Override
        protected Void doInBackground(Train... trains) {
            trainDao.AddTrain(trains[0]);
            return null;
        }
    }

    class DeleteTrain extends AsyncTask<Train ,Void ,Void >{
        TrainDao trainDao;

        public DeleteTrain(TrainDao trainDao) {
            this.trainDao = trainDao;
        }

        @Override
        protected Void doInBackground(Train... trains) {
            trainDao.DeleteTrain(trains[0]);
            return null;
        }
    }
}
