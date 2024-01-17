package com.example.traineygpt;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traineygpt.Model.Hotel;
import com.example.traineygpt.Model.ReservedHotel;
import com.example.traineygpt.databinding.CalenderRecycleViewItemBinding;
import com.example.traineygpt.databinding.FragmentChooseAvailbaleTimeBinding;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class ChooseAvailableTimeFragment extends Fragment {

    private FragmentChooseAvailbaleTimeBinding binding;
    private ReservedHotel reservedHotel;
    private Hotel hotel;
    Date start, End;
    String ClassType, DinnerType;

    public static ChooseAvailableTimeFragment newInstance(Hotel hotel) {
        ChooseAvailableTimeFragment fragment = new ChooseAvailableTimeFragment();
        Bundle args = new Bundle();
        args.putSerializable("hotel", hotel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChooseAvailbaleTimeBinding.inflate(getLayoutInflater());
        hotel = (Hotel) requireArguments().getSerializable("hotel");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        DatePacker();
        List<String> list = Arrays.asList(getResources().getStringArray(R.array.numOfAdult));


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_expandable_list_item_1
                , list), adapter1 = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_expandable_list_item_1
                , Arrays.asList(getResources().getStringArray(R.array.numOfChildren)));
        binding.adultMenuTextView.setAdapter(adapter);
        binding.ChildrenMenuTextView.setAdapter(adapter1);

        binding.calenderDateSelect.setOnClickListener(v -> DatePacker());
        binding.classA.setOnClickListener(v -> {
            ClassType = "Class A";
            binding.classType.setText("Class A");
            binding.classA.setSelected(true);
            binding.classB.setSelected(false);
            binding.classC.setSelected(false);
            binding.classD.setSelected(false);
            binding.classE.setSelected(false);
        });
        binding.classB.setOnClickListener(v -> {
            binding.classType.setText("Class B");
            ClassType = "Class B";
            binding.classB.setSelected(true);
            binding.classA.setSelected(false);
            binding.classC.setSelected(false);
            binding.classD.setSelected(false);
            binding.classE.setSelected(false);
        });
        binding.classC.setOnClickListener(v -> {
            binding.classType.setText("Class C");
            ClassType = "Class C";
            binding.classC.setSelected(true);
            binding.classB.setSelected(false);
            binding.classA.setSelected(false);
            binding.classD.setSelected(false);
            binding.classE.setSelected(false);
        });
        binding.classD.setOnClickListener(v -> {
            binding.classType.setText("Class D");
            ClassType = "Class D";
            binding.classD.setSelected(true);
            binding.classB.setSelected(false);
            binding.classC.setSelected(false);
            binding.classA.setSelected(false);
            binding.classE.setSelected(false);
        });
        binding.classE.setOnClickListener(v -> {
            binding.classType.setText("Class E");
            ClassType = "Class E";
            binding.classE.setSelected(true);
            binding.classB.setSelected(false);
            binding.classC.setSelected(false);
            binding.classD.setSelected(false);
            binding.classA.setSelected(false);
        });
        binding.halfDinner.setOnClickListener(v -> {
            binding.dinnerType.setText("half dinner");
            DinnerType = "half dinner";
            binding.halfDinner.setSelected(true);
            binding.fullDinner.setSelected(false);
        });
        binding.fullDinner.setOnClickListener(v -> {
            binding.dinnerType.setText("full dinner");
            DinnerType = "full dinner";
            binding.halfDinner.setSelected(false);
            binding.fullDinner.setSelected(true);
        });

        binding.recycleViewCalender.setAdapter(new CalenderRecycleView(binding.startDate, binding.endDate, this));
    }

    private void DatePacker() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        MaterialDatePicker<androidx.core.util.Pair<Long, Long>> picker = MaterialDatePicker.Builder.dateRangePicker()
                .setCalendarConstraints(new CalendarConstraints.Builder()
                        .setStart(Calendar.getInstance().getTime().getTime())
                        .setEnd(calendar.getTime().getTime()).build())
                .setTitleText("Select Wanted Range")
                .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR).build();

        picker.show(requireActivity().getSupportFragmentManager(), "tag");

        picker.addOnPositiveButtonClickListener(selection -> {

            binding.startDate.setText(
                    new SimpleDateFormat("dd/MM").format(new Date(selection.first))
            );
            start = new Date(selection.first);
            binding.endDate.setText(
                    new SimpleDateFormat("dd/MM").format(new Date(selection.second))
            );
            End = new Date(selection.second);
        });
        picker.addOnNegativeButtonClickListener(v -> {
        });
        picker.addOnCancelListener(dialog -> {
        });
        picker.addOnDismissListener(dialog -> {

        });


        Navigation.findNavController(requireView())
                .getCurrentBackStackEntry().getSavedStateHandle().getLiveData("isPaid", false)
                .observe(getViewLifecycleOwner(), aBoolean -> {
                    if (aBoolean) {
                        Toast.makeText(requireContext(), "hotel reserved successfully", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(requireView()).popBackStack();
                    }
                });
        binding.button2.setOnClickListener(v -> {
            if (start != null || End != null || ClassType != null || DinnerType != null) {
                reservedHotel = new ReservedHotel(start, End, ClassType, DinnerType,
                        Integer.parseInt(binding.adultMenuTextView.getText().toString()),
                        Integer.parseInt(binding.ChildrenMenuTextView.getText().toString()),
                        UUID.randomUUID().toString().substring(0, 9),
                        hotel);
                Navigation.findNavController(requireView())
                        .navigate(R.id.action_chooseAvailbaleTimeFragment_to_paidHotelReservationDetailFragment,
                                PaidHotelReservationDetailFragment.newInstance(reservedHotel).requireArguments());
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("classType",
                binding.classA.isSelected() ? "Class A" :
                        binding.classB.isSelected() ? "Class B" :
                                binding.classC.isSelected() ? "Class C" :
                                        binding.classD.isSelected() ? "Class D" :
                                                binding.classE.isSelected() ? "Class E" : "");

        outState.putString("dinnerType",
                binding.halfDinner.isSelected() ? "Class A" :
                        binding.fullDinner.isSelected() ? "Class B" : "");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            binding.classType.setText(savedInstanceState.getString("classType"));
            binding.dinnerType.setText(savedInstanceState.getString("dinnerType"));
        }
    }


    class CalenderRecycleView extends RecyclerView.Adapter<CalenderRecycleView.ViewHolder> {
        Date[] dates = new Date[30];
        Date StartDate, EndDate;
        int StartPos = -1, EndPos = -1;
        ViewHolder start, end;
        TextView startDateTextView, EndDateTextView;
        private ChooseAvailableTimeFragment fragment;

        public CalenderRecycleView(TextView startDate, TextView endDate, ChooseAvailableTimeFragment fragment) {
            dates[0] = Calendar.getInstance().getTime();
            for (int i = 1; i < 30; i++) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, i);
                dates[i] = calendar.getTime();
            }
            this.fragment = fragment;

            startDateTextView = startDate;
            EndDateTextView = endDate;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(CalenderRecycleViewItemBinding.inflate(LayoutInflater.from(parent.getContext())
                    , parent, false));
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @SuppressLint("SimpleDateFormat")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.binding.dateDayNum.setText(new SimpleDateFormat("dd").format(dates[position]));
            holder.binding.dateDayText.setText(new SimpleDateFormat("EEE").format(dates[position]));

            holder.binding.getRoot().setOnClickListener(v -> {
                if (StartDate == null) {
                    StartDate = dates[position];
                    StartPos = position;
                    start = holder;
                    lightingMethod(holder);
                    startDateTextView.setText(
                            new SimpleDateFormat("dd/MM").format(dates[position])
                    );
                } else {
                    if (EndDate == null) {
                        if (!Duration.between(dates[position].toInstant(), StartDate.toInstant()).isNegative()
                                && !Duration.between(dates[position].toInstant(), StartDate.toInstant()).isZero()) {
                            EndDate = StartDate;
                            EndPos = StartPos;
                            end = start;

                            StartDate = dates[position];
                            StartPos = position;
                            start = holder;

                            startDateTextView.setText(
                                    new SimpleDateFormat("dd/MM").format(StartDate)
                            );
                            EndDateTextView.setText(
                                    new SimpleDateFormat("dd/MM").format(EndDate)
                            );
                        } else {
                            EndDate = dates[position];
                            EndPos = position;
                            end = holder;
                            EndDateTextView.setText(
                                    new SimpleDateFormat("dd/MM").format(dates[position])
                            );
                        }
                        lightingMethod(holder);
                    } else {
                        if (dates[StartPos] == dates[position]) {
                            StartPos = -1;
                            StartDate = null;
                            startDateTextView.setText(
                                    "StartDate"
                            );
                            DeLightingMethod(start);
                        } else if (dates[EndPos] == dates[position]) {
                            EndPos = -1;
                            EndDate = null;
                            startDateTextView.setText(
                                    "End Date"
                            );
                            DeLightingMethod(end);
                        } else {
                            if (!Duration.between(dates[position].toInstant(), StartDate.toInstant()).isNegative()
                                    && !Duration.between(dates[position].toInstant(), StartDate.toInstant()).isZero()) {
                                EndDate = StartDate;
                                EndPos = StartPos;
                                end = start;

                                StartDate = dates[position];
                                StartPos = position;
                                start = holder;

                                startDateTextView.setText(
                                        new SimpleDateFormat("dd/MM").format(StartDate)
                                );
                                EndDateTextView.setText(
                                        new SimpleDateFormat("dd/MM").format(EndDate)
                                );
                            } else {
                                DeLightingMethod(end);
                                EndPos = position;
                                EndDate = dates[position];
                                end = holder;
                                EndDateTextView.setText(
                                        new SimpleDateFormat("dd/MM").format(dates[position])
                                );
                                lightingMethod(holder);
                            }
                        }
                    }
                }
                fragment.start = StartDate;
                fragment.End = EndDate;

            });
        }

        private void lightingMethod(@NonNull ViewHolder holder) {
            holder.binding.dateDayText.setTextColor(
                    ResourcesCompat.getColor(getResources(), R.color.primaryLightColor, requireActivity().getTheme())
            );
            holder.binding.dateDayNum.setTextColor(
                    ResourcesCompat.getColor(getResources(), R.color.primaryLightColor, requireActivity().getTheme())
            );
        }

        private void DeLightingMethod(@NonNull ViewHolder holder) {
            holder.binding.dateDayText.setTextColor(
                    ResourcesCompat.getColor(getResources(), R.color.black, requireActivity().getTheme())
            );
            holder.binding.dateDayNum.setTextColor(
                    ResourcesCompat.getColor(getResources(), R.color.black, requireActivity().getTheme())
            );
        }

        @Override
        public int getItemCount() {
            return dates.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            CalenderRecycleViewItemBinding binding;

            public ViewHolder(CalenderRecycleViewItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }


}