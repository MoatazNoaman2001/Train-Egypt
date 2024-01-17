package com.example.traineygpt.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.traineygpt.R;
import com.example.traineygpt.databinding.FragmentSignalBinding;


public class SignalFragment extends Fragment {
    private FragmentSignalBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignalBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}