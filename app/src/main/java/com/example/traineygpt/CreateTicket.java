package com.example.traineygpt;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.traineygpt.Model.Ticket;
import com.example.traineygpt.Model.Train;
import com.example.traineygpt.Model.train_distinction;
import com.example.traineygpt.Model.train_type;
import com.example.traineygpt.UI.TicketMVVM;
import com.example.traineygpt.UI.TrainMVVM;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class CreateTicket extends Fragment {

    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_ticket, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(requireView());
        TicketMVVM ticketMVVM = new ViewModelProvider(this).get(TicketMVVM.class);
        TrainMVVM trainMVVM = new ViewModelProvider(this).get(TrainMVVM.class);
        trainMVVM.getAllTrains().observe(getViewLifecycleOwner(), trains -> {
            if (trains.isEmpty()) {
                CreateTickets ticket = new CreateTickets(
                        trainMVVM, ticketMVVM
                );
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (ticket.execute().get(5000, TimeUnit.MILLISECONDS)) {
                                navController.getPreviousBackStackEntry()
                                        .getSavedStateHandle().set("isFinished", true);
                                navController.popBackStack();
                            }
                        } catch (ExecutionException | InterruptedException | TimeoutException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                navController.getPreviousBackStackEntry().getSavedStateHandle().set("isFinished", true);
                navController.popBackStack();
            }
        });


    }

    static class CreateTickets extends AsyncTask<Void, Void, Boolean> {
        TrainMVVM trainMVVM;
        TicketMVVM ticketMVVM;

        public CreateTickets(TrainMVVM trainMVVM, TicketMVVM ticketMVVM) {
            this.trainMVVM = trainMVVM;
            this.ticketMVVM = ticketMVVM;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return CreateData();
        }


        synchronized boolean CreateData() {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.WEEK_OF_MONTH, 1);
            Date End = calendar.getTime();
            System.out.println(new Date() + "\t" + End);
            ArrayList<ArrayList<Ticket>> ticketsArray = new ArrayList<>();
            ArrayList<Ticket> tickets = new ArrayList<>();
            Train train;
            for (Date e = Calendar.getInstance().getTime(); e.getTime() < End.getTime(); ) {
                for (int j = 0; j < train_distinction.values().length; j++) {
                    for (int p = 0; p < train_distinction.values().length; p++) {
                        if (j == p)
                            continue;
                        for (train_type type : train_type.values()) {
                            if (type.name().equals(train_type.SECOND.name())) {
                                Calendar calendar2 = Calendar.getInstance();
                                calendar2.setTime(e);
                                calendar2.add(Calendar.MINUTE, 40);
                                e = calendar2.getTime();
                                train = new Train(
                                        UUID.randomUUID().toString(),
                                        type.name(),
                                        train_distinction.values()[j],
                                        train_distinction.values()[p],
                                        "Wifi , air conditioner ",
                                        String.valueOf(Math.abs((new Random().nextInt() % 1050) + 750)),
                                        e,
                                        new Date(e.getTime() + TimeUnit.HOURS.toMillis((long) (Math.abs(
                                                train_distinction.values()[p].getOrdinal()
                                                        - train_distinction.values()[j].getOrdinal()
                                        ) / 100))),
                                        Math.abs(train_distinction.values()[j].getOrdinal() -
                                                train_distinction.values()[p].getOrdinal())
                                );
                                for (int i = 0; i < 200; i++) {
                                    tickets.add(new Ticket(
                                            UUID.randomUUID().toString(),
                                            type.name(),
                                            train.getStart_Time().toGMTString(),
                                            train
                                    ));
                                }
                            } else {
                                train = new Train(
                                        UUID.randomUUID().toString(),
                                        type.name(),
                                        train_distinction.values()[j],
                                        j != train_distinction.values().length - 1 ? train_distinction.values()[j + 1] : train_distinction.values()[0],
                                        "Wifi , air conditioner , Tv , Dinner Meal",
                                        String.valueOf(Math.abs((new Random().nextInt() % 1050) + 750)),
                                        e,
                                        new Date(e.getTime() + TimeUnit.HOURS.toMillis((long) (Math.abs(
                                                train_distinction.values()[p].getOrdinal()
                                                        - train_distinction.values()[j].getOrdinal()
                                        ) / 100))),
                                        Math.abs(train_distinction.values()[p].getOrdinal() -
                                                train_distinction.values()[j].getOrdinal())
                                );
                                for (int i = 0; i < 200; i++) {
                                    tickets.add(
                                            new Ticket(
                                                    UUID.randomUUID().toString(),
                                                    type.name(),
                                                    train.getStart_Time().toGMTString(),
                                                    train
                                            )
                                    );
                                }
                            }
                        }
                    }
                }
                ticketsArray.add(tickets);
                for (Ticket ticket : new LinkedHashSet<>(tickets)) {
                    ticketMVVM.InsertTicket(ticket);
                    trainMVVM.AddTrain(ticket.getTrain());
                }

                tickets = new ArrayList<>();
                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTime(e);
                calendar2.add(Calendar.MINUTE, 80);
                e = calendar2.getTime();
                System.out.println(e);
            }

            Log.d("TAG", "CreateData: " + ticketsArray.size());
            return true;
        }


    }
}