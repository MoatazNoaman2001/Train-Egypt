package com.example.traineygpt.UI;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.traineygpt.Model.Ticket;
import com.example.traineygpt.Repasotory.TicketRepository;

import java.util.List;

public class TicketMVVM  extends AndroidViewModel {

    private TicketRepository repository;
    private LiveData<List<Ticket>> AllTickets , AssuitTicket , CairoTickets , AlexandriaTickets , AswanTickets , SohagTickets , LukorTickets;


    public TicketMVVM(@NonNull Application application) {
        super(application);
        repository = new TicketRepository(application);
        AllTickets = repository.getAllTickets();
        AssuitTicket = repository.getAssuitTicket();
        CairoTickets = repository.getCairoTickets();
        AlexandriaTickets = repository.getAlexandriaTickets();
        AswanTickets = repository.getAswanTickets();
        SohagTickets = repository.getSohagTickets();
        LukorTickets = repository.getLukorTickets();
    }
    public LiveData<List<Ticket>> SearchTickets(String start , String End){
        return repository.getSearchTickets(start , End);
    }

    public void InsertTicket(Ticket ticket){
        repository.InsertTicket(ticket);
    }
    public void DeleteTicket(Ticket ticket){
        repository.DeleteTicket(ticket);
    }
    public void UpdateTicket(Ticket ticket){
        repository.UpdateTicket(ticket);
    }

    public LiveData<List<Ticket>> getAllTickets() {
        return AllTickets;
    }

    public LiveData<List<Ticket>> getAssuitTicket() {
        return AssuitTicket;
    }

    public LiveData<List<Ticket>> getCairoTickets() {
        return CairoTickets;
    }

    public LiveData<List<Ticket>> getAlexandriaTickets() {
        return AlexandriaTickets;
    }

    public LiveData<List<Ticket>> getAswanTickets() {
        return AswanTickets;
    }

    public LiveData<List<Ticket>> getSohagTickets() {
        return SohagTickets;
    }

    public LiveData<List<Ticket>> getLukorTickets() {
        return LukorTickets;
    }
}
