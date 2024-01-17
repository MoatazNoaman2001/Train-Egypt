package com.example.traineygpt;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.traineygpt.Model.Hotel;
import com.example.traineygpt.Model.ReservedHotel;
import com.example.traineygpt.UI.PaidTicketsMVVM;
import com.example.traineygpt.databinding.FragmentPaidHotelReservationDetailBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class PaidHotelReservationDetailFragment extends Fragment {

    private FragmentPaidHotelReservationDetailBinding binding;
    private ReservedHotel hotel;
    private NavController controller;
    private PaidTicketsMVVM ticketsMVVM;
    private double price;

    public static PaidHotelReservationDetailFragment newInstance(ReservedHotel hotel) {
        Bundle args = new Bundle();
        args.putSerializable("hotel", hotel);
        PaidHotelReservationDetailFragment fragment = new PaidHotelReservationDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hotel = (ReservedHotel) requireArguments().getSerializable("hotel");
        ticketsMVVM = new ViewModelProvider(this).get(PaidTicketsMVVM.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaidHotelReservationDetailBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(requireView());

        controller.getCurrentBackStackEntry().getSavedStateHandle()
                .getLiveData("isReserved", false)
                .observe(getViewLifecycleOwner(), aBoolean -> {
                    if (aBoolean) {
                        ticketsMVVM.InsertHotelReservation(hotel);
                        FirebaseDatabase.getInstance().getReference(
                                "UsersReservations"
                        ).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .updateChildren(new HashMap<>(){{put("1" , 2);}})
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(@NonNull Void unused) {

                                    }
                                }).addOnFailureListener(e -> {
                            Toast.makeText(requireContext(), "failed to upload reservation to cloud database but its\n " +
                                    "inserted in offline data on your device\n " +
                                    "once you connected it will uploaded", Toast.LENGTH_SHORT).show();
                        });
                        controller.getPreviousBackStackEntry().getSavedStateHandle()
                                .set("isPaid", true);
                        controller.popBackStack();
                    } else {

                    }
                });

        SetPrice();

        binding.button.setOnClickListener(v -> {
            hotel.setPrice(price);
            controller.navigate(R.id.action_paidHotelReservationDetailFragment_to_payFragment);
        });
    }

    private void SetPrice() {
        price = (100 * hotel.getHotel().getRate()) *
                (hotel.getAdultNumber() + hotel.getChildrenNumber()) *
                Duration.between(hotel.getStart().toInstant(), hotel.getEnd().toInstant()).toDays();
        binding.textView4.setText(
                price + " LE"
        );

        Glide.with(requireActivity())
                .asBitmap()
                .load(hotel.getHotel().getHotelImage()[0])
                .apply(new RequestOptions().transform(
                        new MultiTransformation<>(
                                new CenterCrop(),
                                new RoundedCornersTransformation(40, 0)
                        )
                ))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imageView2);
    }
}