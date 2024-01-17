package com.example.traineygpt.UI;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.traineygpt.Model.Hotel;
import com.example.traineygpt.Model.ReservedHotel;
import com.example.traineygpt.Model.Ticket;
import com.example.traineygpt.PaidTrainTicketDetailsFragment;
import com.example.traineygpt.R;
import com.example.traineygpt.ReservedHotelDetail;
import com.example.traineygpt.UI.MainFragments.TrainListRecycleViewAdapter;
import com.example.traineygpt.UI.SignIn.BlogRecycleViewAdatpter;
import com.example.traineygpt.databinding.BlogCardViewHotelRecycleItemBinding;
import com.example.traineygpt.databinding.FragmentPaymentBinding;
import com.example.traineygpt.databinding.RecycleViewItemBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class PaymentFragment extends Fragment {

    private FragmentPaymentBinding binding;
    private PaidTicketsMVVM mvvm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaymentBinding.inflate(getLayoutInflater());
        mvvm = new ViewModelProvider(this).get(PaidTicketsMVVM.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mvvm.getGetPaidTickets().observe(getViewLifecycleOwner(), tickets -> {
            mvvm.getGetPaidHotelReservation().observe(getViewLifecycleOwner(), reservedHotels -> {
                ArrayList<Object> objects = new ArrayList<>(tickets);
                objects.addAll(reservedHotels);
                binding.getRoot().setAdapter(new PaidTicketsRecycleView(objects, requireActivity(), mvvm));
            });
        });
    }

    static class PaidTicketsRecycleView extends RecyclerView.Adapter<PaidTicketsRecycleView.ViewHolder> {
        ArrayList<Object> tickets;
        Activity activity;
        PaidTicketsMVVM ticketsMVVM;

        public PaidTicketsRecycleView(ArrayList<Object> tickets, Activity activity, PaidTicketsMVVM ticketsMVVM) {
            this.tickets = tickets;
            this.activity = activity;
            this.ticketsMVVM = ticketsMVVM;
        }

        @Override
        public int getItemViewType(int position) {
            if (tickets.get(position).getClass().getName().contains(
                    "Ticket"
            )) return 0;
            else return 1;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == 0)
                return new ViewHolder(RecycleViewItemBinding.inflate(
                        LayoutInflater.from(parent.getContext()), parent, false
                ));
            else if (viewType == 1) {
                return new ViewHolder(BlogCardViewHotelRecycleItemBinding.inflate(
                        LayoutInflater.from(parent.getContext()), parent, false
                ));
            } else {
                return null;
            }
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Log.d("TAG", "onBindViewHolder: " + tickets.get(position).getClass().getCanonicalName());
            if (holder.getItemViewType() == 0) {
                Ticket ticket = (Ticket) tickets.get(position);
                if (ticket == null) return;
                holder.binding1.TrainNumber.setText(ticket.train.getTrain_number());
                holder.binding1.TackOffStation.setText(ticket.train.getStart_Destination());
                holder.binding1.ArrivalStation.setText(ticket.train.Termination_Station);
                holder.binding1.TakeOffTime.setText(
                        new SimpleDateFormat("hh:mm a").format(ticket.train.getStart_Time())
                );
                holder.binding1.ArrivalTime.setText(
                        new SimpleDateFormat("hh:mm a").format(ticket.train.getEnd_Time())
                );
                holder.binding1.TrainType.setText(ticket.Type);
                holder.binding1.TravelDuration.setText(!Duration.between(
                        ticket.getTrain().End_Time.toInstant(), Calendar.getInstance().getTime().toInstant()
                ).isNegative()? "Expired" : ticket.duration.toMinutes() / 60
                        + "h " + ticket.duration.toMinutes() % 60 + "m");
                holder.binding1.Expand.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (holder.binding1.ExtraLayout.getVisibility() == View.GONE) {
                            try {
                                TransitionManager.beginDelayedTransition(holder.binding1.getRoot(), new AutoTransition());
                                holder.binding1.ExtraLayout.setVisibility(View.VISIBLE);
                                holder.binding1.Expand.setText("narrows");
                                holder.binding1.Expand.setCompoundDrawablesRelativeWithIntrinsicBounds(
                                        null,
                                        null,
                                        ResourcesCompat.getDrawable(holder.itemView.getResources(),
                                                R.drawable.ic_up_keyboard_arrow, activity.getTheme()),
                                        null
                                );

                                Glide.with(holder.binding1.TrainImage.getContext())
                                        .asBitmap()
                                        .load(ticket.Type.equals("FIRST") ?
                                                "https://tse1.mm.bing.net/th/id/OIP.1uv1MfQQLcbgXTm1jxKwHgHaFc?pid=ImgDet&rs=1" :
                                                "https://th.bing.com/th/id/R.ebcc947c9cbf0dbc07665edc5d11f007?rik=0owESffyq78UiQ&riu=http%3a%2f%2fenglish.ahram.org.eg%2fMedia%2fNews%2f2020%2f6%2f24%2f2020-637286039589669667-966.jpg&ehk=RPJFBgO1a5U3d2O79W95lwfxwyo94tNOo1nE6xBTmSI%3d&risl=&pid=ImgRaw&r=0")
                                        .apply(new RequestOptions().transform(
                                                new MultiTransformation<>(
                                                        new CenterCrop(),
                                                        new RoundedCornersTransformation(30, 15)
                                                )
                                        ))
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .placeholder(R.drawable.start_img)
                                        .error(android.R.drawable.stat_notify_error)
                                        .into(holder.binding1.TrainImage);
                            } catch (Exception e) {

                            }
                        } else {
                            try {
                                TransitionManager.beginDelayedTransition(holder.binding1.getRoot(), new AutoTransition());
                                ScaleAnimation animation = new ScaleAnimation(1f, 1f, 1f, 0f);
                                animation.setDuration(300);
                                holder.binding1.ExtraLayout.setAnimation(animation);
                                holder.binding1.ExtraLayout.postDelayed(() -> holder.binding1.ExtraLayout.setVisibility(View.GONE), 300);
                                holder.binding1.Expand.setText("Expand");
                                holder.binding1.Expand.setCompoundDrawablesRelativeWithIntrinsicBounds(
                                        null,
                                        null, ResourcesCompat.getDrawable(holder.itemView.getResources(),
                                                R.drawable.ic_down_keyboard_arrow, activity.getTheme()), null
                                );
                            } catch (Exception e) {

                            }
                        }
                    }
                });
                holder.binding1.moreInformation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Navigation.findNavController(v)
                                .navigate(R.id.action_paymentFragment_to_paidTrainTicketDetailsFragment
                                        , PaidTrainTicketDetailsFragment.newInstance(ticket).requireArguments());
                    }
                });


                holder.binding1.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                        popupMenu.inflate(R.menu.cancle_menu);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            popupMenu.setForceShowIcon(true);
                        }
                        popupMenu.show();

                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                if (item.getItemId() == R.id.cancel) {
                                    ticketsMVVM.DeleteTicket(ticket);
                                    notifyItemRemoved(tickets.indexOf(ticket));
                                }else if (item.getItemId() == R.id.uploadComment){
                                    if (!Duration.between(ticket.train.getStart_Time().toInstant(), Calendar.getInstance().toInstant()).isNegative()){
                                        FirebaseDatabase.getInstance().getReference("TrainTableComments")
                                                .child("LateTrains")
                                                .updateChildren(Map.of(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()
                                                        , "Hi , My Reserved Train num" + ticket.train.Train_number + "is late for"
                                                                + Duration.between(ticket.train.Start_Time.toInstant(), Calendar.getInstance().toInstant()).toMinutes()
                                                                + "minutes"));
                                    }else{
                                        Toast.makeText(holder.itemView.getContext(), "train take off time has not come yet\n" +
                                                "please be patient", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                return true;
                            }
                        });

                        return true;
                    }
                });
            } else {
                ReservedHotel hotel = (ReservedHotel) tickets.get(position);
                Glide.with(holder.itemView.getContext())
                        .asBitmap()
                        .load(hotel.getHotel().getHotelImage()[0])
                        .transform(new MultiTransformation<>(new RoundedCornersTransformation(40, 10)))
                        .transition(new BitmapTransitionOptions().crossFade())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.binding2.HotelImageView);
                holder.binding2.HotelRate.setText(String.valueOf(hotel.getHotel().getRate()));
                holder.binding2.HotelName.setText(hotel.getHotel().getName());
                holder.binding2.HotelAddress.setText(hotel.getHotel().getAddress());
                holder.binding2.city.setText(hotel.getHotel().getCity());
                holder.binding2.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Navigation.findNavController(v)
                                .navigate(R.id.action_paymentFragment_to_reservedHotelDetail,
                                        ReservedHotelDetail.newInstance(hotel).requireArguments());
                    }
                });
                holder.binding2.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                        popupMenu.inflate(R.menu.hotel_cancle_menu);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            popupMenu.setForceShowIcon(true);
                        }

                        popupMenu.show();

                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                if (item.getItemId() == R.id.cancel) {
                                    ticketsMVVM.DeleteReservation(hotel);
                                    notifyItemRemoved(tickets.indexOf(hotel));
                                }
                                return true;
                            }
                        });

                        return true;
                    }
                });
            }

        }

        @Override
        public int getItemCount() {
            return tickets.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            RecycleViewItemBinding binding1;
            BlogCardViewHotelRecycleItemBinding binding2;

            public ViewHolder(RecycleViewItemBinding binding) {
                super(binding.getRoot());
                binding1 = binding;
            }

            public ViewHolder(BlogCardViewHotelRecycleItemBinding binding) {
                super(binding.getRoot());
                binding2 = binding;
            }
        }
    }
}