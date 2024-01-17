package com.example.traineygpt.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.AutoTransition;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.traineygpt.ChooseAvailableTimeFragment;
import com.example.traineygpt.Model.Hotel;
import com.example.traineygpt.R;
import com.example.traineygpt.databinding.FragmentHotelDetailFragmentBinding;
import com.example.traineygpt.databinding.HotelsImageRecycleViewItemBinding;
import com.example.traineygpt.databinding.SimpleTextViewBinding;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class HotelDetailFrgament extends Fragment {

    private FragmentHotelDetailFragmentBinding binding;
    private HotelMVVM mvvm;


    public static HotelDetailFrgament newInstance(Hotel hotel) {
        Bundle args = new Bundle();
        args.putSerializable("hotel", hotel);
        HotelDetailFrgament fragment = new HotelDetailFrgament();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHotelDetailFragmentBinding.inflate(getLayoutInflater());
        mvvm = new ViewModelProvider(this).get(HotelMVVM.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Transition transition = TransitionInflater.from(requireContext()).inflateTransition(
                android.R.transition.move
        );
        setSharedElementEnterTransition(transition);
        setSharedElementReturnTransition(transition);

        SetHotelDetails((Hotel) requireArguments().getSerializable("hotel"));
    }

    private void SetHotelDetails(Hotel hotel2) {
        Hotel hotel = hotel2;
        binding.hotelsRecycleView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.ServicesRecycleView.setAdapter(new Hotel_ServicesAdapter(hotel.getSupplies()));

        Glide.with(requireActivity())
                .asBitmap()
                .load(hotel.getHotelImage()[0])
                .apply(new RequestOptions().transform(new RoundedCornersTransformation(80, 0)))
                .into(binding.mainImage);

        Glide.with(requireActivity())
                .asBitmap()
                .load(hotel.getHotelImage()[1])
                .apply(new RequestOptions().transform(new RoundedCornersTransformation(40, 0)))
                .into(binding.secondImage);

        Glide.with(requireActivity())
                .asBitmap()
                .load(hotel.getHotelImage()[2])
                .apply(new RequestOptions().transform(new RoundedCornersTransformation(40, 0)))
                .into(binding.ThirdImage);
        binding.HotelName.setText(hotel.getName() + " (" + hotel.getCity() + ")");
        binding.HotelRate.setText(hotel.getRate() + "/5");
        binding.HotelDetails.setText(hotel.getDiscreption());
        binding.HotelAddress.setText(hotel.getAddress());

        mvvm.getHotels().observe(getViewLifecycleOwner(), hotels -> {
            ArrayList<Hotel> hotels1 = new ArrayList<>(hotels);
            if (hotels1.remove(hotel))
                binding.hotelsRecycleView.setAdapter(new Hotels_image(hotels1 , this));
        });
        binding.RegristButton.setOnClickListener(v -> {
            Navigation.findNavController(requireView())
                    .navigate(R.id.action_hotelDetailFrgament_to_chooseAvailbaleTimeFragment
                    , ChooseAvailableTimeFragment.newInstance(hotel).requireArguments());
        });
    }


    class Hotels_image extends RecyclerView.Adapter<Hotels_image.ViewHolder> {


        ArrayList<Hotel> hotels;
        HotelDetailFrgament frgament;

        public Hotels_image(ArrayList<Hotel> hotels, HotelDetailFrgament frgament) {
            this.hotels = hotels;
            this.frgament = frgament;
        }

        @NonNull
        @Override
        public Hotels_image.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(HotelsImageRecycleViewItemBinding.inflate(LayoutInflater
                    .from(parent.getContext()), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull Hotels_image.ViewHolder holder, int position) {
            Glide.with(holder.binding.getRoot().getContext())
                    .asBitmap()
                    .load(hotels.get(position).getHotelImage()[0])
                    .apply(new RequestOptions().transform(new MultiTransformation<>(
                            new CenterCrop(),
                            new RoundedCornersTransformation(40, 0)
                    )))
                    .into(holder.binding.ImagesLayout
                    );
            if (hotels.get(position).getName().length() > 18)
                holder.binding.hotelN.setTextSize(7);
            else if (hotels.get(position).getName().length() > 10)
                holder.binding.hotelN.setTextSize(10);
            holder.binding.hotelN.setText(hotels.get(position).getName());
            holder.binding.getRoot().setOnClickListener(v -> {
                TransitionManager.beginDelayedTransition(frgament.binding.getRoot(),
                        new AutoTransition());
                frgament.SetHotelDetails(hotels.get(position));
            });
        }

        @Override
        public int getItemCount() {
            return hotels.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            HotelsImageRecycleViewItemBinding binding;

            public ViewHolder(HotelsImageRecycleViewItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }

    class Hotel_ServicesAdapter extends RecyclerView.Adapter<Hotel_ServicesAdapter.ViewHolder> {
        String[] services;

        public Hotel_ServicesAdapter(String[] services) {
            this.services = services;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(SimpleTextViewBinding.inflate(LayoutInflater.from(parent.getContext())
                    , parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.textView.setText(services[position]);
        }

        @Override
        public int getItemCount() {
            return services.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            public ViewHolder(SimpleTextViewBinding binding) {
                super(binding.getRoot());
                textView = binding.textview;
            }
        }
    }

}