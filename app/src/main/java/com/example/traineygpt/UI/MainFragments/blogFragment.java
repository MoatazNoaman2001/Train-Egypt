package com.example.traineygpt.UI.MainFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.traineygpt.Model.Hotel;
import com.example.traineygpt.Model.Hotel_services;
import com.example.traineygpt.UI.HotelMVVM;
import com.example.traineygpt.UI.SignIn.BlogRecycleViewAdatpter;
import com.example.traineygpt.databinding.FragmentBlogFramentBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class blogFragment extends Fragment {

    FragmentBlogFramentBinding binding;
    private HotelMVVM mvvm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBlogFramentBinding.inflate(getLayoutInflater());
        mvvm = new ViewModelProvider(this).get(HotelMVVM.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        Hotel[] hotelsArr = HotelsEditor.getAllHotels();

        mvvm.getHotels().observe(getViewLifecycleOwner(), hotels -> {
            if (hotels.isEmpty()) {
                for (int i = 0; i <hotelsArr.length; i++) {
                    mvvm.InsertHotel(hotelsArr[i]);
                }
                binding.recyclerView.setAdapter(new BlogRecycleViewAdatpter(new ArrayList<Hotel>(
                        Arrays.asList(hotelsArr)
                )));
            }  else {
                binding.recyclerView.setAdapter(new BlogRecycleViewAdatpter(new ArrayList<>(hotels)));
            }
        });
    }
}