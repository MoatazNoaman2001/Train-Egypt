package com.example.traineygpt.UI.SignIn;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.traineygpt.Model.Hotel;
import com.example.traineygpt.R;
import com.example.traineygpt.UI.HotelDetailFrgament;
import com.example.traineygpt.databinding.BlogCardViewCarsRecycleItemBinding;
import com.example.traineygpt.databinding.BlogCardViewHotelRecycleItemBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class BlogRecycleViewAdatpter extends RecyclerView.Adapter<BlogRecycleViewAdatpter.ViewHolder> {
    View view;
    ArrayList<Hotel> hotels;

//    public BlogRecycleViewAdatpter(int[] views, RecyclerView view) {
//        this.views = views;
//        this.view = view;
//    }

    public BlogRecycleViewAdatpter(ArrayList<Hotel> hotels) {
        this.hotels = hotels;
    }

    @Override
    public int getItemViewType(int position) {
//        if (hotels )
//            if (views[position] == 1) {
//                return 0;
//            } else {
//                return 1;
//            }
//        else{
//           return 0;
//        }

        return 0;
    }

    @NonNull
    @Override
    public BlogRecycleViewAdatpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new ViewHolder(BlogCardViewHotelRecycleItemBinding.inflate(
                    LayoutInflater.from(parent.getContext()), parent, false
            ));
        } else {
            return new ViewHolder(BlogCardViewCarsRecycleItemBinding.inflate(
                    LayoutInflater.from(parent.getContext()), parent, false
            ));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BlogRecycleViewAdatpter.ViewHolder holder, int position) {
        Log.d("TAG", "onBindViewHolder: " + hotels.get(position).getHotelImage()[0]);
        Glide.with(holder.itemView.getContext())
                .asBitmap()
                .load(hotels.get(position).getHotelImage()[0])
                .transition(new BitmapTransitionOptions().crossFade())
                .apply(new RequestOptions().transform(
                        new MultiTransformation<>(
                                new CenterCrop(),
                                new RoundedCornersTransformation(40, 20)
                        )
                ))
                .placeholder(android.R.drawable.stat_sys_download)
                .error(android.R.drawable.stat_notify_error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.binding.HotelImageView);

        holder.binding.HotelName.setText(hotels.get(position).getName());
        holder.binding.HotelAddress.setText(hotels.get(position).getAddress());
        holder.binding.HotelRate.setText(hotels.get(position).getRate() + "/ 5");
        holder.binding.city.setText(hotels.get(position).getCity());
        holder.binding.getRoot().setOnClickListener(v -> {
            holder.binding.HotelAddress.setTransitionName(
                    holder.binding.HotelAddress.getTransitionName() + "_" + position
            );

            holder.binding.HotelImageView.setTransitionName(
                    holder.binding.HotelImageView.getTransitionName() + "_" + position
            );
            holder.binding.cardView.setTransitionName(
                    holder.binding.cardView.getTransitionName() + "_" + position
            );
            holder.binding.HotelName.setTransitionName(
                    holder.binding.HotelName.getTransitionName() + "_" + position
            );

            holder.binding.rateLayout.setTransitionName(
                    holder.binding.rateLayout.getTransitionName() + "_" + position
            );
            holder.binding.city.setTransitionName(
                    holder.binding.city.getTransitionName() + "_" + position
            );
            Map<View, String> viewStringMap = new HashMap<>(){{
                put(holder.binding.HotelImageView, "hotel_im2");
                put(holder.binding.cardView, "mainCardView");
                put(holder.binding.HotelName, "hotelName_trans");
                put(holder.binding.HotelAddress, "hotelAddress_trans");
                put(holder.binding.rateLayout, "rate_trans");
                put(holder.binding.city, "Price_Large");
            }};
//                    viewStringMap.put(view , "recycleView_mini");
            try {
                if (Navigation.findNavController(v).getCurrentDestination().getLabel()
                        .equals("Hotel search"))
                    Navigation.findNavController(v)
                            .navigate(R.id.action_HotelSearch_to_hotelDetailFrgament
                                    , HotelDetailFrgament.newInstance(hotels.get(position)).requireArguments()
                                    , null,
                                    new FragmentNavigator.Extras.Builder()
                                            .addSharedElements(viewStringMap)
                                            .build());
                else
                    Navigation.findNavController(v)
                            .navigate(R.id.action_mainFragment_to_hotelDetailFrgament
                                    , HotelDetailFrgament.newInstance(hotels.get(position)).requireArguments()
                                    , null,
                                    new FragmentNavigator.Extras.Builder()
                                            .addSharedElements(viewStringMap)
                                            .build());
            } catch (IllegalArgumentException e) {

            }
        });
//
//        switch (holder.getItemViewType()) {
//            case 0 -> {
//                Glide.with(holder.binding.HotelImageView.getContext())
//                        .asBitmap()
//                        .load("https://th.bing.com/th/id/R.dc49b7af8e11ae0d718265ad357aca9f?rik=yTJ2tc5TpCtnlg&riu=http%3a%2f%2fluxurylaunches.com%2fwp-content%2fuploads%2f2017%2f06%2fMonarch-Beach-Resort-4.jpg&ehk=R8kckHeXlRGJd8I53nBh%2bJUm4HRD5xwHTsr%2fbx%2f7L2c%3d&risl=&pid=ImgRaw&r=0")
//                        .transition(new BitmapTransitionOptions().crossFade())
//                        .apply(new RequestOptions().transform(
//                                new MultiTransformation<>(
//                                        new CenterCrop(),
//                                        new RoundedCornersTransformation(40, 20)
//                                )
//                        ))
//                        .placeholder(android.R.drawable.stat_sys_download)
//                        .error(android.R.drawable.stat_notify_error)
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .into(holder.binding.HotelImageView);
//                holder.binding.getRoot().setOnClickListener(v -> {
//                    Map<View, String> viewStringMap = new HashMap<>();
//                    viewStringMap.put(holder.binding.HotelImageView, "hotel_img2");
//                    viewStringMap.put(holder.binding.cardView, "mainCardView");
//                    viewStringMap.put(holder.binding.HotelName, "hotelName_trans");
//                    viewStringMap.put(holder.binding.HotelAddress, "hotelAddress_trans");
//                    viewStringMap.put(holder.binding.rateLayout, "rate_trans");
//                    viewStringMap.put(holder.binding.Price, "Price_Large");
////                    viewStringMap.put(view , "recycleView_mini");
//                    Navigation.findNavController(v)
//                            .navigate(R.id.action_mainFragment_to_hotelDetailFrgament
//                                    , null, null,
//                                    new FragmentNavigator.Extras.Builder()
//                                            .addSharedElements(viewStringMap)
//                                            .build());
//                });
//            }
//            default -> Glide.with(holder.binding2.HotelImageView.getContext())
//                    .asDrawable()
//                    .load("https://i.pinimg.com/564x/72/16/c8/7216c8d8812d219bc4c43a1fb276069d.jpg")
//                    .transition(new DrawableTransitionOptions().crossFade())
//                    .apply(new RequestOptions().transform(
//                            new MultiTransformation<>(
//                                    new CenterCrop(),
//                                    new RoundedCornersTransformation(40, 20)
//                            )
//                    ))
//                    .placeholder(R.drawable.start_img)
//                    .error(android.R.drawable.stat_notify_error)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(holder.binding2.HotelImageView);
//        }


    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        BlogCardViewHotelRecycleItemBinding binding;
        BlogCardViewCarsRecycleItemBinding binding2;

        public ViewHolder(BlogCardViewHotelRecycleItemBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        public ViewHolder(BlogCardViewCarsRecycleItemBinding binding) {
            super(binding.getRoot());

            this.binding2 = binding;
        }
    }
}
