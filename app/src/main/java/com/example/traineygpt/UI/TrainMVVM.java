package com.example.traineygpt.UI;

import android.app.Application;
import android.app.job.JobInfo;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.traineygpt.Model.Train;
import com.example.traineygpt.Repasotory.TrainRepository;

import java.util.List;

public class TrainMVVM extends AndroidViewModel {
    private TrainRepository repository;
    private LiveData<List<Train>> AllTrains;

    public TrainMVVM(@NonNull Application application) {
        super(application);
        repository = new TrainRepository(application);
        AllTrains = repository.getAllTrains();
    }

    public LiveData<List<Train>> getAllTrains() {
        return AllTrains;
    }

    public void AddTrain(Train train){
        repository.AddTrain(train);
    }

    public void DeleteTrain(Train train){
        repository.DeleteTrain(train);
    }
}

