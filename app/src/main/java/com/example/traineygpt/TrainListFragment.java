package com.example.traineygpt;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.traineygpt.Model.train_distinction;
import com.example.traineygpt.databinding.FragmentTrainListListBinding;
import com.example.traineygpt.placeholder.PlaceholderContent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;

/**
 * A fragment representing a list of Items.
 */
public class TrainListFragment extends Fragment {

    private FragmentTrainListListBinding binding;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    public String type;
    NavController controller;
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */


    public enum clickEventTypeForSearchTrain {START_DISTINCTION, TERMINATED_STATION};

    public TrainListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TrainListFragment newInstance(int columnCount, clickEventTypeForSearchTrain event) {
        TrainListFragment fragment = new TrainListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putString("event", event.name());
        fragment.setArguments(args);
        return fragment;
    }

    public static TrainListFragment newInstance(int columnCount, String dis, clickEventTypeForSearchTrain event) {
        TrainListFragment fragment = new TrainListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putString("event", event.name());
        args.putString("dis", dis);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            type = requireArguments().getString("event");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentTrainListListBinding.inflate(getLayoutInflater());
        // Set the adapter
        binding.getRoot();
        if (mColumnCount <= 1) {
            binding.getRoot().setLayoutManager(new LinearLayoutManager(requireContext()));
        } else {
            binding.getRoot().setLayoutManager(new GridLayoutManager(requireContext(), mColumnCount));
        }
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<PlaceholderContent.PlaceholderItem> placeholderContents = new ArrayList<>();

        int t = 0;
        for (train_distinction distinction: train_distinction.values()){
            placeholderContents.add(new PlaceholderContent.PlaceholderItem(
                         t , distinction.name() , distinction.getDescription()
            ));
            t++;
        }
        controller = Navigation.findNavController(requireView());
        Log.d("TAG", "onViewCreated: " + type);
        if (type.equals(
                TrainListFragment.clickEventTypeForSearchTrain.START_DISTINCTION.name()
        )) {
        }
        else {
            String dis = requireArguments().getString("dis");
            PlaceholderContent.PlaceholderItem item
                    = new PlaceholderContent.PlaceholderItem();
            for (int i = 0; i < placeholderContents.size(); i++)
                if (placeholderContents.get(i).content.equals(dis)) {
                    item = placeholderContents.get(i);
                    break;
                }
            placeholderContents.remove(item);

        }
        binding.getRoot().setAdapter(new MyTrainListRecyclerViewAdapter(
                placeholderContents,this, type, controller));
    }
}