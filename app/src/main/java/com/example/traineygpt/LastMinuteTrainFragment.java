package com.example.traineygpt;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.traineygpt.Model.Ticket;
import com.example.traineygpt.UI.MainFragments.TrainListRecycleViewAdapter;
import com.example.traineygpt.UI.TicketMVVM;
import com.example.traineygpt.databinding.FragmentLastMinuteTrainFramentBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class LastMinuteTrainFragment extends Fragment {

    private FragmentLastMinuteTrainFramentBinding binding;
    private TicketMVVM mvvm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLastMinuteTrainFramentBinding.inflate(getLayoutInflater());
        mvvm = new ViewModelProvider(this).get(TicketMVVM.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.lastMinuteRecycleView.setLayoutManager(new LinearLayoutManager(requireContext()));
        mvvm.getCairoTickets().observe(getViewLifecycleOwner(), tickets -> {
            mvvm.getAswanTickets().observe(getViewLifecycleOwner(), tickets1 -> {
                mvvm.getAlexandriaTickets().observe(getViewLifecycleOwner(), tickets2 -> {
                    ArrayList<Ticket> ticketArrayList = new ArrayList<>();
                    ticketArrayList.addAll(tickets.subList(0, 10));
                    ticketArrayList.addAll(tickets1.subList(0, 10));
                    ticketArrayList.addAll(tickets2.subList(0, 10));
                    binding.lastMinuteRecycleView.setAdapter(new TrainListRecycleViewAdapter(
                            requireActivity(),
                            ticketArrayList
                    ));
                });
            });
        });
    }
}