package com.example.traineygpt.UI;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.traineygpt.DataBase.PaidTickets;
import com.example.traineygpt.Model.Hotel;
import com.example.traineygpt.Model.ReservedHotel;
import com.example.traineygpt.Model.Ticket;
import com.example.traineygpt.Repasotory.PaidTicketsRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class PaidTicketsMVVM extends AndroidViewModel {
    private PaidTicketsRepository repository;
    private LiveData<List<Ticket>> getPaidTickets;
    private LiveData<List<ReservedHotel>> getPaidHotelReservation;

    public PaidTicketsMVVM(@NonNull Application application) {
        super(application);
        repository = new PaidTicketsRepository(application);
        getPaidTickets = repository.getGetPaidTickets();
        getPaidHotelReservation = repository.getGetPaidHotelReservation();
    }


    public boolean InsertTicket(Ticket ticket){
        try {
            return repository.InsertPaidTicket(ticket);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean DeleteTicket(Ticket ticket){
        try {
            return repository.DeleteTicket(ticket);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void InsertHotelReservation(ReservedHotel hotel){
        repository.InsertHotelReservation(hotel);
    }

    public boolean DeleteReservation(ReservedHotel hotel){
        try {
            return repository.DeleteReservation(hotel);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
            return false;
        }
    }

    public LiveData<List<Ticket>> getGetPaidTickets() {
        return getPaidTickets;
    }

    public LiveData<List<ReservedHotel>> getGetPaidHotelReservation() {
        return getPaidHotelReservation;
    }
}
