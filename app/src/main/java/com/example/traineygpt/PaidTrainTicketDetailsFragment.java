package com.example.traineygpt;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.traineygpt.Model.Ticket;
import com.example.traineygpt.databinding.FragmentPaidHotelReservationDetailBinding;
import com.example.traineygpt.databinding.FragmentPaidTrainTicketDetailsBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class PaidTrainTicketDetailsFragment extends Fragment {

    private Ticket ticket;
    private FragmentPaidTrainTicketDetailsBinding binding;

    public static PaidTrainTicketDetailsFragment newInstance(Ticket ticket) {
        PaidTrainTicketDetailsFragment fragment = new PaidTrainTicketDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("ticket", ticket);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ticket = (Ticket) requireArguments().getSerializable("ticket");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaidTrainTicketDetailsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTicketDetails();

        binding.qrButton.setOnClickListener(v -> {
            BottomSheetDialog dialog = new BottomSheetDialog(
                    requireActivity(), R.style.BottomSheetDiagramStyle
            );
            View BottomSheetView = LayoutInflater.from(requireContext())
                    .inflate(R.layout.qr_bottom_sheet , (LinearLayout) requireActivity().findViewById(R.id.bottomsheetlayout));
            MultiFormatWriter writer = new MultiFormatWriter();
            try {
                BitMatrix matrix = writer.encode(ticket.Ticket_ID , BarcodeFormat.QR_CODE
                , 350 , 350);
                BarcodeEncoder encoder = new BarcodeEncoder();
                Bitmap bitmap = encoder.createBitmap(matrix);

                ((TextView) BottomSheetView.findViewById(R.id.startBig)).setText(ticket.train.getStart_Destination().substring(0 , 2));
                ((TextView) BottomSheetView.findViewById(R.id.TerBig)).setText(ticket.train.getTermination_Station().substring(0 , 2));
                ((TextView) BottomSheetView.findViewById(R.id.startSmall)).setText(ticket.train.getStart_Destination());
                ((TextView) BottomSheetView.findViewById(R.id.TerSmall)).setText(ticket.train.getTermination_Station());

                ((TextView) BottomSheetView.findViewById(R.id.PassengerName)).setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                ((TextView) BottomSheetView.findViewById(R.id.TrainId)).setText(ticket.getTicket_ID().substring(0 , 7));
                Glide.with(requireContext())
                        .asBitmap()
                        .load(bitmap)
                        .apply(new RequestOptions().transform(
                                new MultiTransformation<>(
                                        new CenterCrop(),
                                        new RoundedCornersTransformation(40 , 10)
                                )
                        ))
                        .into((ImageView) BottomSheetView.findViewById(R.id.QrCodeForTicket));
            }catch (Exception e){
                e.printStackTrace();
            }

            dialog.setContentView(BottomSheetView);
            dialog.show();
        });
    }

    @SuppressLint("SimpleDateFormat")
    private void setTicketDetails() {
        binding.TreDisLarge.setText(ticket.train.Termination_Station.substring(0, 2));
        binding.StartDisLarge.setText(ticket.train.Start_Destination.substring(0, 2));
        binding.startDestinationSmall.setText(ticket.train.Start_Destination);
        binding.TerminationStationSmall.setText(ticket.train.Termination_Station);

        binding.DateOfTicket.setText(
                new SimpleDateFormat("EEE dd/MM").format(ticket.train.getStart_Time())
        );

        binding.textView9.setText(ticket.Ticket_ID.substring(0, 12));
        binding.TrainClass.setText(ticket.train.Train_type);
        binding.textView11.setText(ticket.train.getTrain_number());
        binding.TackOffTime.setText(
                new SimpleDateFormat("hh:mm").format(ticket.train.getStart_Time())
        );
        binding.ArrivalTime.setText(
                new SimpleDateFormat("hh:mm").format(ticket.train.getEnd_Time())
        );

        binding.Duration.setText(ticket.duration.toMinutes() / 60 +
                "hours and " + ticket.duration.toMinutes() % 60 + "minute");
        binding.PassengerName.setText(
                FirebaseAuth.getInstance().getCurrentUser().getDisplayName()
        );
        AppendProgressBar();
    }

    private void AppendProgressBar() {
        binding.ProgressParIndicator.setMax(
                (int) Duration.between(
                        ticket.train.Start_Time.toInstant(),
                        ticket.train.End_Time.toInstant()
                ).toMillis()
        );

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                Date date = Calendar.getInstance().getTime();
                if (Duration.between(ticket.train.getStart_Time().toInstant(),
                        date.toInstant()).isNegative()) {
                    binding.ProgressParIndicator.setProgress(0);
                    handler.removeCallbacks(this);
                } else if (!Duration.between(ticket.train.getStart_Time().toInstant(),
                        date.toInstant()).isNegative() &&
                        Duration.between(ticket.train.getEnd_Time().toInstant(),
                                date.toInstant()).isNegative()) {
                    Log.d("TAG", "run: " + Duration.between(
                            date.toInstant(),
                            ticket.train.getEnd_Time().toInstant()
                    ).toMinutes());
                    binding.ProgressParIndicator.setProgress(
                            (int) Duration.between(
                                    ticket.train.getStart_Time().toInstant(),
                                    date.toInstant()
                            ).toMillis()
                    );
                }else{
                    binding.ProgressParIndicator.setProgress((int) Duration.between(
                            ticket.train.Start_Time.toInstant(),
                            ticket.train.End_Time.toInstant()
                    ).toMillis());
                }
                handler.postDelayed(this , 300);
            }
        };

        handler.postDelayed(runnable, 10);
    }
}