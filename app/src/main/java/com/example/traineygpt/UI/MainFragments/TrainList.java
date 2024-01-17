package com.example.traineygpt.UI.MainFragments;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.traineygpt.CreateTicket;
import com.example.traineygpt.Model.Ticket;
import com.example.traineygpt.Model.Train;
import com.example.traineygpt.Model.train_distinction;
import com.example.traineygpt.Model.train_type;
import com.example.traineygpt.UI.TicketMVVM;
import com.example.traineygpt.UI.TrainMVVM;
import com.example.traineygpt.databinding.FragmentSecondBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class TrainList extends Fragment {
    private static final String TAG = "TrainList";

    private FragmentSecondBinding binding;
    private TicketMVVM mvvm;
    private TrainMVVM trainMVVM;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<Ticket> ticketArrayList = new ArrayList<>();
        mvvm = new ViewModelProvider(this).get(TicketMVVM.class);
        trainMVVM = new ViewModelProvider(this).get(TrainMVVM.class);
        mvvm.getCairoTickets().observe(getViewLifecycleOwner(), tickets -> {
            mvvm.getAswanTickets().observe(getViewLifecycleOwner(), tickets1 -> {
                mvvm.getAlexandriaTickets().observe(getViewLifecycleOwner(), tickets2 -> {
                    ArrayList<Ticket> ticketList = tickets.stream().filter(ticket ->
                            ticket.train.Start_Destination.equals("CAIRO") && !ticket.train.Termination_Station.equals("Tanta"))
                            .collect(Collectors.toCollection(ArrayList::new));
                    ticketArrayList.addAll(
                            ticketList.size() > 20?
                            ticketList.subList(0, 20) : ticketList);
                    ticketArrayList.addAll(
                            tickets1.size() > 20?
                                    tickets1.subList(0, 20) : tickets1);
                    ticketArrayList.addAll(
                            tickets2.size() > 20?
                                    tickets2.subList(0, 20) : tickets2);

                    binding.recyclerView.setAdapter(new TrainListRecycleViewAdapter(requireActivity(), ticketArrayList));
                    for (Ticket ticket : new ArrayList<>(ticketArrayList)) {
                        if (Duration.between(Calendar.getInstance().getTime().toInstant(),
                                ticket.getTrain().Start_Time.toInstant()).isNegative()) {
                            mvvm.DeleteTicket(ticket);
                            ticketArrayList.remove(ticket);
                            binding.recyclerView.getAdapter().notifyItemRemoved(ticketArrayList.indexOf(ticket));
                        }
                    }
                });
            });
        });


        mvvm.getAllTickets().observe(getViewLifecycleOwner(), tickets -> {
            if (tickets.isEmpty()) {
                return;
            } else {
                Date date = Calendar.getInstance().getTime();
                for (int i = 0; i < tickets.size(); i++) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        if (Duration.between(date.toInstant(), tickets.get(i).getTrain().Start_Time.toInstant()).isNegative()) {
                            mvvm.DeleteTicket(tickets.get(i));
                            tickets.remove(i);
                        }
                    }
                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}