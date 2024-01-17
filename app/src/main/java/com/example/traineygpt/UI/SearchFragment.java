package com.example.traineygpt.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.traineygpt.LastMinuteTrainFragment;
import com.example.traineygpt.Model.Ticket;
import com.example.traineygpt.MyTrainListRecyclerViewAdapter;
import com.example.traineygpt.R;
import com.example.traineygpt.TrainListFragment;
import com.example.traineygpt.UI.MainFragments.ImageViewPagerAdapter;
import com.example.traineygpt.UI.MainFragments.TrainListRecycleViewAdapter;
import com.example.traineygpt.UI.MainFragments.blogFragment;
import com.example.traineygpt.UI.SignIn.FragmentViewPagerAdapter;
import com.example.traineygpt.UI.MainFragments.TrainList;
import com.example.traineygpt.databinding.FragmentSearchBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private ImageViewPagerAdapter adapter;
    private String dis, ter;
    private TicketMVVM mvvm;
    private ArrayList<Ticket> First, Second;

    public static SearchFragment newInstance(String Destination, String TerminationStation) {
        Bundle args = new Bundle();
        SearchFragment fragment = new SearchFragment();
        args.putString("Destination", Destination);
        args.putString("type", TerminationStation);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new ImageViewPagerAdapter(new String[]{
                "https://images.pexels.com/photos/5098043/pexels-photo-5098043.jpeg?cs=srgb&dl=pexels-elena-saharova-5098043.jpg&fm=jpg",
                "https://images.pexels.com/photos/2928780/pexels-photo-2928780.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",
                "https://images.pexels.com/photos/3787405/pexels-photo-3787405.jpeg?cs=srgb&dl=pexels-jerry-wang-3787405.jpg&fm=jpg",
                "https://images.pexels.com/photos/5949745/pexels-photo-5949745.jpeg?cs=srgb&dl=pexels-sarah-ching-5949745.jpg&fm=jpg",
                "https://images.pexels.com/photos/6100146/pexels-photo-6100146.jpeg?cs=srgb&dl=pexels-mike-6100146.jpg&fm=jpg",
                "https://images.pexels.com/photos/1658967/pexels-photo-1658967.jpeg?cs=srgb&dl=pexels-senuscape-1658967.jpg&fm=jpg",
                "https://images.pexels.com/photos/1548693/pexels-photo-1548693.jpeg?cs=srgb&dl=pexels-david-bartus-1548693.jpg&fm=jpg",
                "https://images.pexels.com/photos/258510/pexels-photo-258510.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",
                "https://images.pexels.com/photos/258510/pexels-photo-258510.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",
                "https://images.pexels.com/photos/302692/pexels-photo-302692.jpeg?cs=srgb&dl=pexels-pixabay-302692.jpg&fm=jpg"
        });
        binding.imagesViewPager.setAdapter(adapter);

        run(binding.imagesViewPager);
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("First"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Second"));

        NavController controller = Navigation.findNavController(requireView());
        mvvm = new ViewModelProvider(this).get(TicketMVVM.class);
        controller.getCurrentBackStackEntry()
                .getSavedStateHandle()
                .getLiveData("Dis")
                .observe(getViewLifecycleOwner(), o -> {
                    dis = String.valueOf(o);
                    Log.d("TAG", "onViewCreated: " + dis);
                    binding.SearchButton.setVisibility(View.VISIBLE);
                    binding.DepartureStation.setText(String.valueOf(o));
                });
        controller.getCurrentBackStackEntry()
                .getSavedStateHandle()
                .getLiveData("Tir")
                .observe(getViewLifecycleOwner(), o -> {
                    ter = String.valueOf(o);
                    Log.d("TAG", "onViewCreated: " + ter);
                    binding.SearchButton.setVisibility(View.VISIBLE);
                    binding.DestinationStation.setText(String.valueOf(o));
                });

        binding.SearchButton.setOnClickListener(v -> {
            mvvm.SearchTickets(dis, ter).observe(getViewLifecycleOwner(),
                    tickets -> {

                        for (Ticket ticket : tickets) {
                            Log.d("TAG", "onViewCreated: " + ticket.toString());
                        }
                        if (tickets.isEmpty()) {
                            Toast.makeText(requireContext(), "no tickets in this destination", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Second = (ArrayList<Ticket>) tickets.stream().filter(ticket -> ticket.Type.equals("SECOND"))
                                .collect(Collectors.toList());
                        First = (ArrayList<Ticket>) tickets.stream().filter(ticket -> ticket.Type.equals("FIRST"))
                                .collect(Collectors.toList());
                        binding.SearchButton.setVisibility(View.GONE);
                        if (binding.tabLayout.getSelectedTabPosition() == 0) {
                            binding.searchRecycleView.setAdapter(
                                    new TrainListRecycleViewAdapter(requireActivity(),
                                            First));
                        } else if (binding.tabLayout.getSelectedTabPosition() == 1) {
                            binding.searchRecycleView.setAdapter(
                                    new TrainListRecycleViewAdapter(requireActivity(),
                                            Second));
                        }
                    });

            binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    if (tab.getPosition() == 0) {
                        binding.searchRecycleView.setAdapter(
                                new TrainListRecycleViewAdapter(requireActivity(),
                                        First));
                    } else if (tab.getPosition() == 1) {
                        binding.searchRecycleView.setAdapter(
                                new TrainListRecycleViewAdapter(requireActivity(),
                                        Second));
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        });


        binding.DepartureStation.setOnClickListener(v -> {
            Navigation.findNavController(requireView())
                    .navigate(R.id.action_search_Train_to_trainListFragment,
                            TrainListFragment.newInstance(1, TrainListFragment.clickEventTypeForSearchTrain.START_DISTINCTION).requireArguments()
                    );
        });

        binding.DestinationStation.setOnClickListener(v -> {
            Navigation.findNavController(requireView())
                    .navigate(R.id.action_search_Train_to_trainListFragment,
                            TrainListFragment.newInstance(1, TrainListFragment.clickEventTypeForSearchTrain.TERMINATED_STATION).requireArguments());
        });
    }

    private void run(ViewPager2 imagesViewPager) {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (imagesViewPager.getCurrentItem() < imagesViewPager.getAdapter().getItemCount()) {
                    imagesViewPager.setCurrentItem(imagesViewPager.getCurrentItem() + 1);
                    handler.postDelayed(this, 5000);
                } else if (imagesViewPager.getCurrentItem() >= imagesViewPager.getAdapter().getItemCount()) {
                    imagesViewPager.setAdapter(adapter);
                    handler.postDelayed(this, 5000);
                }
            }
        };
        handler.postDelayed(runnable, 5000);
    }

}