package com.example.traineygpt.UI.MainFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.traineygpt.R;
import com.example.traineygpt.UI.MainFragments.placeholder.PlaceholderContent;
import com.example.traineygpt.UI.TrainMVVM;
import com.example.traineygpt.databinding.FragmentTrainItemListBinding;

import java.util.Collections;

/**
 * A fragment representing a list of Items.
 */
public class TrainItemsFragment extends Fragment {

    private FragmentTrainItemListBinding binding;
    private TrainMVVM mvvm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      binding = FragmentTrainItemListBinding.inflate(getLayoutInflater());
      mvvm = new ViewModelProvider(this).get(TrainMVVM.class);
      return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mvvm.getAllTrains().observe(getViewLifecycleOwner() , trains -> {
            binding.getRoot().setAdapter(new MyTrainItemsRecyclerViewAdapter(
               trains
            ));
        });
    }
}