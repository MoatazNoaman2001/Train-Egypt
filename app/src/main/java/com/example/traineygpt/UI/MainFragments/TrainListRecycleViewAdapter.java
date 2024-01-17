package com.example.traineygpt.UI.MainFragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.traineygpt.Model.Ticket;
import com.example.traineygpt.R;
import com.example.traineygpt.TicketDetailsFragment;
import com.example.traineygpt.databinding.RecycleViewItemBinding;
import com.google.android.gms.dynamic.IFragmentWrapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TrainListRecycleViewAdapter extends RecyclerView.Adapter<TrainListRecycleViewAdapter.ViewHolder> {

    Activity activity;
    boolean[] is_opened;
    ArrayList<Ticket> tickets;

    public TrainListRecycleViewAdapter(Activity requireActivity, List<Ticket> tickets) {
        activity = requireActivity;
        this.tickets = (ArrayList<Ticket>) tickets;
        is_opened = new boolean[tickets.size()];
    }

    @NonNull
    @Override
    public TrainListRecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RecycleViewItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        ));
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onBindViewHolder(@NonNull TrainListRecycleViewAdapter.ViewHolder holder, int position) {
        Ticket ticket = tickets.get(position);
        holder.binding.moreInformation.setOnClickListener(v -> {
            holder.binding.ArrivalStation.setTransitionName(
                    holder.binding.ArrivalStation.getTransitionName() + "_" + position
            );

            holder.binding.ArrivalTime.setTransitionName(
                    holder.binding.ArrivalTime.getTransitionName() + "_" + position
            );

            holder.binding.TackOffStation.setTransitionName(
                    holder.binding.TackOffStation.getTransitionName() + "_" + position
            );

            holder.binding.TakeOffTime.setTransitionName(
                    holder.binding.TakeOffTime.getTransitionName() + "_" + position
            );

            holder.binding.TravelDuration.setTransitionName(
                    holder.binding.TravelDuration.getTransitionName() + "_" + position
            );

            HashMap<View, String> views = new HashMap<>() {{
                put(holder.binding.ArrivalStation, "ArrivaleS_F");
                put(holder.binding.TackOffStation, "TakeOffS_F");
                put(holder.binding.TakeOffTime, "TakeOffT_F");
                put(holder.binding.ArrivalTime, "ArrivalTime_F");
                put(holder.binding.TravelDuration, "Dur_F");
            }};

            if (Objects.equals(Navigation.findNavController(v).getCurrentBackStackEntry().getDestination().getLabel(), "Search"))
                Navigation.findNavController(v)
                        .navigate(R.id.action_search_Train_to_ticketDetailsFragment
                                , TicketDetailsFragment.newInstance(ticket).requireArguments()
                                , null
                                , new FragmentNavigator.Extras.Builder()
                                        .addSharedElements(views)
                                        .build());
            else if (Objects.equals(Navigation.findNavController(v).getCurrentBackStackEntry().getDestination().getLabel(), "Train Egypt"))
                Navigation.findNavController(v)
                        .navigate(R.id.action_mainFragment_to_ticketDetailsFragment
                                , TicketDetailsFragment.newInstance(ticket).requireArguments()
                                , null
                                , new FragmentNavigator.Extras.Builder()
                                        .addSharedElements(views)
                                        .build());
        });
        holder.binding.Expand.setOnClickListener(v -> {
            try {
                if (holder.binding.ExtraLayout.getVisibility() == View.GONE) {
                    try {
                        TransitionManager.beginDelayedTransition(holder.binding.getRoot(), new AutoTransition());
                        holder.binding.ExtraLayout.setVisibility(View.VISIBLE);
                        holder.binding.Expand.setText("narrows");
                        holder.binding.Expand.setCompoundDrawablesRelativeWithIntrinsicBounds(
                                null,
                                null,
                                ResourcesCompat.getDrawable(holder.itemView.getResources(),
                                        R.drawable.ic_up_keyboard_arrow, activity.getTheme()),
                                null
                        );

                        Glide.with(holder.binding.TrainImage.getContext())
                                .asBitmap()
                                .load(ticket.Type.equals("FIRST") ?
                                        "https://tse1.mm.bing.net/th/id/OIP.1uv1MfQQLcbgXTm1jxKwHgHaFc?pid=ImgDet&rs=1" :
                                        "https://th.bing.com/th/id/R.ebcc947c9cbf0dbc07665edc5d11f007?rik=0owESffyq78UiQ&riu=http%3a%2f%2fenglish.ahram.org.eg%2fMedia%2fNews%2f2020%2f6%2f24%2f2020-637286039589669667-966.jpg&ehk=RPJFBgO1a5U3d2O79W95lwfxwyo94tNOo1nE6xBTmSI%3d&risl=&pid=ImgRaw&r=0")
                                .apply(new RequestOptions().transform(
                                        new MultiTransformation<>(
                                                new CenterCrop(),
                                                new RoundedCornersTransformation(30, 5)
                                        )
                                ))
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .placeholder(R.drawable.start_img)
                                .error(android.R.drawable.stat_notify_error)
                                .into(holder.binding.TrainImage);
                    } catch (Exception e) {

                    }
                } else {
                    TransitionManager.beginDelayedTransition(holder.binding.getRoot(), new AutoTransition());
                    ScaleAnimation animation = new ScaleAnimation(1f, 1f, 1f, 0f);
                    animation.setDuration(300);
                    holder.binding.ExtraLayout.setAnimation(animation);
                    holder.binding.ExtraLayout.postDelayed(() -> holder.binding.ExtraLayout.setVisibility(View.GONE), 300);
                    holder.binding.Expand.setText("Expand");
                    holder.binding.Expand.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            null,
                            null, ResourcesCompat.getDrawable(holder.itemView.getResources(),
                                    R.drawable.ic_down_keyboard_arrow, activity.getTheme()), null
                    );
                }
            } catch (IndexOutOfBoundsException ignored) {
                Log.d("TAG", "onBindViewHolder: " + ignored.getMessage());
            }
        });

        holder.binding.TravelDuration.setText(ticket.duration.toMinutes() / 60
                + "h " + ticket.duration.toMinutes() % 60 + "m");
        holder.binding.TrainType.setText(ticket.train.Train_type);
        holder.binding.TakeOffTime.setText(
                new SimpleDateFormat("hh:mm a").format(
                        ticket.train.Start_Time
                )
        );
        holder.binding.ArrivalTime.setText(
                new SimpleDateFormat("hh:mm a").format(
                        ticket.train.End_Time
                )
        );
        holder.binding.ArrivalStation.setText(ticket.train.Termination_Station);
        holder.binding.TackOffStation.setText(ticket.train.Start_Destination);

        holder.binding.TrainNumber.setText(ticket.Ticket_ID.substring(0, 4));
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.binding.ExtraLayout.setVisibility(View.GONE);
        is_opened[holder.getLayoutPosition()] = false;
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RecycleViewItemBinding binding;

        public ViewHolder(RecycleViewItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
