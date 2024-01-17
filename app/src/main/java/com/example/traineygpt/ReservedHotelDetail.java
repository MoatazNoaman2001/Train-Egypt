package com.example.traineygpt;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.traineygpt.Model.ReservedHotel;
import com.example.traineygpt.databinding.FragmentReservedHotelDetailBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.time.Duration;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReservedHotelDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReservedHotelDetail extends Fragment {
    private FragmentReservedHotelDetailBinding binding;
    private ReservedHotel hotel;

    public static ReservedHotelDetail newInstance(ReservedHotel hotel) {
        ReservedHotelDetail fragment = new ReservedHotelDetail();
        Bundle args = new Bundle();
        args.putSerializable("hotel", hotel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hotel = (ReservedHotel) requireArguments().getSerializable("hotel");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReservedHotelDetailBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindElements();
    }

    private void bindElements() {
        binding.HotelName.setText(hotel.getHotel().getName());
        binding.textView19.setText(hotel.getHotel().getAddress());
        binding.NameTextView.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        binding.NumberOfNights.setText(
                Duration.between(hotel.getStart().toInstant() , hotel.getEnd().toInstant())
                .toDays() + " Nights"
        );
        binding.TicketID.setText(hotel.getTicketID().substring(0 , 7));
        binding.Class.setText(hotel.getClassType());
        binding.textView13.setText(hotel.getDinnerType());
        binding.StartDate.setText(new SimpleDateFormat("dd/MM").format(hotel.getStart()));
        binding.EndDate.setText(new SimpleDateFormat("dd/MM").format(hotel.getEnd()));

        Glide.with(requireActivity())
                .asBitmap()
                .load(hotel.getHotel().getHotelImage()[0])
                .apply(new RequestOptions().transform(new MultiTransformation<>(
                        new CenterCrop(),
                        new RoundedCornersTransformation(20 , 0)
                )))
                .into(binding.imageView5);
    }
}