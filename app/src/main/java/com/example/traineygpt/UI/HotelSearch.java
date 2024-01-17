package com.example.traineygpt.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.traineygpt.Model.Hotel;
import com.example.traineygpt.UI.MainFragments.ImageViewPagerAdapter;
import com.example.traineygpt.UI.SignIn.BlogRecycleViewAdatpter;
import com.example.traineygpt.databinding.FragmentHotelSearchBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HotelSearch#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HotelSearch extends Fragment {

    private FragmentHotelSearchBinding binding;
    private HotelMVVM mvvm;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HotelSearch() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HotelSearch.
     */
    // TODO: Rename and change types and number of parameters
    public static HotelSearch newInstance(String param1, String param2) {
        HotelSearch fragment = new HotelSearch();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHotelSearchBinding.inflate(getLayoutInflater());
        mvvm = new ViewModelProvider(this).get(HotelMVVM.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.imagesViewPager.setUserInputEnabled(false);
        binding.imagesViewPager.setAdapter(new ImageViewPagerAdapter(new String[]{
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
        }));
        run(binding.imagesViewPager);

        binding.searchTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.SearchTextInputLayout.setStartIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvvm.SearchHotels(binding.searchTextView.getText().toString())
                        .observe(getViewLifecycleOwner(), new Observer<List<Hotel>>() {
                            @Override
                            public void onChanged(List<Hotel> hotels) {
                                if (!hotels.isEmpty())
                                    binding.recycleView.setAdapter(new BlogRecycleViewAdatpter(new ArrayList<>(hotels)));
                                else
                                    Toast.makeText(requireContext(), "no hotel in " + binding.searchTextView.getText().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private void run(ViewPager2 imagesViewPager) {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (imagesViewPager.getCurrentItem() < imagesViewPager.getAdapter().getItemCount()) {
                    imagesViewPager.setCurrentItem(imagesViewPager.getCurrentItem() + 1);
                    handler.postDelayed(this, 7000);
                }
            }
        };
        handler.postDelayed(runnable, 5000);
    }

}