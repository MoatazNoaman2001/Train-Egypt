package com.example.traineygpt.Repasotory;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.traineygpt.DataBase.Dao.TicketDao;
import com.example.traineygpt.DataBase.TicketsDataBase;
import com.example.traineygpt.Model.Ticket;

import java.util.List;

public class TicketRepository {
    private TicketsDataBase dataBase;
    private TicketDao ticketDao;
    private LiveData<List<Ticket>> AllTickets , AssuitTicket , CairoTickets , AlexandriaTickets , AswanTickets , SohagTickets , LukorTickets;


    public TicketRepository(Application application) {
        dataBase = TicketsDataBase.getDataBase(application);
        ticketDao = dataBase.ticketDao();
        AllTickets = ticketDao.getAllTickect();
        AssuitTicket = ticketDao.getAssuitTickets();
        CairoTickets = ticketDao.getCairoTickets();
        AlexandriaTickets = ticketDao.getALEXANDRIATickets();
        AswanTickets = ticketDao.getASWANTickets();
        SohagTickets = ticketDao.getSOHAGTickets();
        LukorTickets = ticketDao.getLUXORTickets();
    }
    public LiveData<List<Ticket>> getSearchTickets(String start , String End){
        return ticketDao.searchQuary(start , End);
    }

    public void InsertTicket(Ticket ticket){
        new TicketInsert(ticketDao).execute(ticket);
    }
    public void DeleteTicket(Ticket ticket){
        new TicketDelete(ticketDao).execute(ticket);
    }
    public void UpdateTicket(Ticket ticket){
        new TicketUpdate(ticketDao).execute(ticket);
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

    static class TicketInsert extends AsyncTask<Ticket , Void ,Void>{
        TicketDao ticketDao;

        public TicketInsert(TicketDao ticketDao) {
            this.ticketDao = ticketDao;
        }

        @Override
        protected Void doInBackground(Ticket... tickets) {
            ticketDao.InsertTicket(tickets[0]);
            return null;
        }
    }

    static class TicketDelete extends AsyncTask<Ticket , Void ,Void>{
        TicketDao ticketDao;

        public TicketDelete(TicketDao ticketDao) {
            this.ticketDao = ticketDao;
        }

        @Override
        protected Void doInBackground(Ticket... tickets) {
            ticketDao.DeleteTicket(tickets[0]);
            return null;
        }
    }


    static class TicketUpdate extends AsyncTask<Ticket , Void ,Void>{
        TicketDao ticketDao;

        public TicketUpdate(TicketDao ticketDao) {
            this.ticketDao = ticketDao;
        }

        @Override
        protected Void doInBackground(Ticket... tickets) {
            ticketDao.UpdateTicket(tickets[0]);
            return null;
        }
    }
}
