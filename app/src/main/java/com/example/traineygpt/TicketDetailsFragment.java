package com.example.traineygpt;

import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.traineygpt.Model.Ticket;
import com.example.traineygpt.UI.PaidTicketsMVVM;
import com.example.traineygpt.UI.PayFragment;
import com.example.traineygpt.UI.TicketMVVM;
import com.example.traineygpt.databinding.FragmentTicketDetailsBinding;
import com.example.traineygpt.databinding.MiniTrainTiketRecycleViewItemBinding;
import com.example.traineygpt.databinding.SimpleTextViewBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TicketDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TicketDetailsFragment extends Fragment {
    private FragmentTicketDetailsBinding binding;
    private Ticket ticket;
    private TicketMVVM mvvm;
    private PaidTicketsMVVM ticketsMVVM;
    private NavController controller;

    public static TicketDetailsFragment newInstance(Ticket ticket) {
        Bundle args = new Bundle();
        TicketDetailsFragment fragment = new TicketDetailsFragment();
        args.putSerializable("ticket", ticket);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTicketDetailsBinding.inflate(getLayoutInflater());
        ticket = (Ticket) requireArguments().getSerializable("ticket");
        ticketsMVVM = new ViewModelProvider(this).get(PaidTicketsMVVM.class);
        controller = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView2);

        Transition transition = TransitionInflater.from(requireContext()).inflateTransition(
                android.R.transition.move
        );
        setSharedElementEnterTransition(transition);
        setSharedElementReturnTransition(transition);

        return binding.getRoot();
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.startReserve.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "enough", Toast.LENGTH_SHORT).show();
        });

        applyTicketDetails(ticket);

        mvvm = new ViewModelProvider(this).get(TicketMVVM.class);
        mvvm.SearchTickets(ticket.getTrain().Start_Destination
                , ticket.getTrain().Termination_Station)
                .observe(getViewLifecycleOwner(), tickets -> binding.recyclerView2.setAdapter(
                        new TrainTicketMineRecycleView(new ArrayList<>(new LinkedHashSet<>(tickets)),
                                TicketDetailsFragment.this)));

        controller.getCurrentBackStackEntry().getSavedStateHandle()
                .getLiveData("isPaid", false).observe(getViewLifecycleOwner(), o -> {
            if (o) {
                if (ticketsMVVM.InsertTicket(ticket)) {
                    FirebaseDatabase.getInstance().getReference(
                            "UsersReservations"
                    ).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .updateChildren(Map.of("Ticket" + ticket.Ticket_ID.substring(0, 5), ticket))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(@NonNull Void unused) {

                                }
                            }).addOnFailureListener(e -> {
                        Toast.makeText(requireContext(), "Failed to upload ticket to cloud database but its\n " +
                                "inserted in offline data on your device\n " +
                                "once you connected it will uploaded", Toast.LENGTH_SHORT).show();
                    });
                    ticket.setTicket_ID(UUID.randomUUID().toString());
                    mvvm.UpdateTicket(ticket);
                    controller.navigate(R.id.action_ticketDetailsFragment_to_paidTrainTicketDetailsFragment,
                            PaidTrainTicketDetailsFragment.newInstance(ticket).requireArguments());
                }
            }
        });
        binding.startReserve.setOnClickListener(v -> {
            controller.navigate(R.id.action_ticketDetailsFragment_to_payFragment);
        });
    }

    private void applyTicketDetails(Ticket ticket) {
        binding.Duration.setText(
                ticket.duration.toMinutes() / 60 + "h " + ticket.duration.toMinutes() % 60 + "m"
        );
        binding.TerminationTime.setText(new SimpleDateFormat("hh:mm a").format(
                ticket.train.End_Time
        ));
        binding.TackOfTime.setText(new SimpleDateFormat("hh:mm a").format(
                ticket.train.Start_Time
        ));
        binding.startDestination.setText(ticket.train.Start_Destination);
        binding.TerminationStation.setText(ticket.train.Termination_Station);
        binding.distance.setText(String.valueOf(ticket.train.getDistance()));
        binding.stationOnRoadRecycleView.setAdapter(new TrainStopStation(
                ticket.train.Train_Dis
        ));

        binding.Price.setText(ticket.Type.equals("FIRST") ?
                ticket.train.getDistance() * 0.15 + "L.E"
                : ticket.train.getDistance() * 0.1 + "L.E");

        binding.Day.setText(
                new SimpleDateFormat("EEE dd/MM").format(ticket.getTrain().getStart_Time())
        );
        binding.Class.setText(ticket.Type);
    }

    class TrainTicketMineRecycleView extends RecyclerView.Adapter<TrainTicketMineRecycleView.ViewHolder> {

        ArrayList<Ticket> tickets;
        TicketDetailsFragment fragment;

        public TrainTicketMineRecycleView(ArrayList<Ticket> tickets, TicketDetailsFragment ticketDetailsFragment) {
            this.tickets = tickets;
            this.fragment = ticketDetailsFragment;
        }

        @NonNull
        @Override
        public TrainTicketMineRecycleView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(MiniTrainTiketRecycleViewItemBinding.inflate(LayoutInflater.from(parent.getContext())
                    , parent, false));
        }

        @SuppressLint("SimpleDateFormat")
        @Override
        public void onBindViewHolder(@NonNull TrainTicketMineRecycleView.ViewHolder holder, int position) {
            holder.binding.startCity.setText(tickets.get(position).train.Start_Destination);
            holder.binding.EndCity.setText(tickets.get(position).train.Termination_Station);
            holder.binding.startTime.setText(
                    new SimpleDateFormat("hh:mm a").format(
                            tickets.get(position).train.Start_Time
                    )
            );
            holder.binding.endTime.setText(
                    new SimpleDateFormat("hh:mm a").format(
                            tickets.get(position).train.End_Time
                    )
            );
            holder.binding.classTextView.setText(tickets.get(position).Type);

            holder.binding.getRoot().setOnClickListener(v -> {
                fragment.ticket = tickets.get(position);
                fragment.applyTicketDetails(tickets.get(position));
            });


        }

        @Override
        public int getItemCount() {
            return tickets.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            MiniTrainTiketRecycleViewItemBinding binding;

            public ViewHolder(MiniTrainTiketRecycleViewItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }

    class TrainStopStation extends RecyclerView.Adapter<TrainStopStation.ViewHolder> {
        String[] Stations;

        public TrainStopStation(String[] stations) {
            Stations = stations;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(SimpleTextViewBinding.inflate(LayoutInflater.from(parent.getContext())
                    , parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.binding.textview.setText(Stations[position]);
        }

        @Override
        public int getItemCount() {
            return Stations.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            SimpleTextViewBinding binding;

            public ViewHolder(SimpleTextViewBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }
}
