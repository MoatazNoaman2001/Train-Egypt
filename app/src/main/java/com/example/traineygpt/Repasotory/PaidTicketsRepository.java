package com.example.traineygpt.Repasotory;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.traineygpt.DataBase.Dao.HotelDao;
import com.example.traineygpt.DataBase.Dao.ReservedHotelDao;
import com.example.traineygpt.DataBase.Dao.TicketDao;
import com.example.traineygpt.DataBase.PaidTickets;
import com.example.traineygpt.Model.Hotel;
import com.example.traineygpt.Model.ReservedHotel;
import com.example.traineygpt.Model.Ticket;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class PaidTicketsRepository {
    private PaidTickets tickets;
    private TicketDao ticketDao;
    private ReservedHotelDao hotelDao;
    private LiveData<List<Ticket>> getPaidTickets;
    private LiveData<List<ReservedHotel>> getPaidHotelReservation;


    public PaidTicketsRepository (Application application){
        tickets = PaidTickets.getInstance(application);
        ticketDao = tickets.ticketDao();
        hotelDao = tickets.hotelDao();
        getPaidTickets = ticketDao.getAllTickect();
        getPaidHotelReservation = hotelDao.getHotels();
    }

    public boolean InsertPaidTicket(Ticket ticket) throws InterruptedException, ExecutionException, TimeoutException {
        return new InsertPaidTicket().execute(ticket).get(20 , TimeUnit.MILLISECONDS);
    }
    public boolean DeleteTicket(Ticket ticket) throws InterruptedException, ExecutionException, TimeoutException {
        return new DeletePaidTicket().execute(ticket).get(20 , TimeUnit.MILLISECONDS);
    }

    public void InsertHotelReservation(ReservedHotel hotel){
        new InsertReservedHotel().execute(hotel);
    }

    public boolean DeleteReservation(ReservedHotel hotel) throws InterruptedException, ExecutionException, TimeoutException {
        return new DeleteReservation().execute(hotel).get(20 , TimeUnit.MILLISECONDS);
    }
    public LiveData<List<Ticket>> getGetPaidTickets() {
        return getPaidTickets;
    }

    public LiveData<List<ReservedHotel>> getGetPaidHotelReservation() {
        return getPaidHotelReservation;
    }

    class InsertPaidTicket extends AsyncTask<Ticket, Void , Boolean>{
        @Override
        protected Boolean doInBackground(Ticket... tickets) {
            try {
                ticketDao.InsertTicket(tickets[0]);
                return true;
            }catch (Exception e){
                return false;
            }
        }
    }
    class DeletePaidTicket extends AsyncTask<Ticket, Void , Boolean>{
        @Override
        protected Boolean doInBackground(Ticket... tickets) {
            try {
                ticketDao.DeleteTicket(tickets[0]);
                return true;
            }catch (Exception e){
                return false;
            }
        }
    }
    class InsertReservedHotel extends AsyncTask<ReservedHotel, Void , Void>{
        @Override
        protected Void doInBackground(ReservedHotel... ReservedHotels) {
            hotelDao.Insert(ReservedHotels[0]);
            return null;
        }
    }

    class DeleteReservation extends AsyncTask<ReservedHotel, Void , Boolean>{
        @Override
        protected Boolean doInBackground(ReservedHotel... ReservedHotels) {
            try {
                hotelDao.Delete(ReservedHotels[0]);
                return true;
            }catch (Exception e){
                return false;
            }
        }
    }
}
