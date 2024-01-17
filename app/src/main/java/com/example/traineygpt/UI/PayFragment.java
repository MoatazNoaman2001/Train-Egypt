package com.example.traineygpt.UI;

import android.content.Intent;
import android.nfc.tech.NfcA;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.traineygpt.Model.Hotel;
import com.example.traineygpt.Model.Ticket;
import com.example.traineygpt.PaidTrainTicketDetailsFragment;
import com.example.traineygpt.R;
import com.example.traineygpt.databinding.FragmentPayBinding;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;


public class PayFragment extends Fragment {
    private FragmentPayBinding binding;
    private ActivityResultLauncher<Intent> launcher;
    private String resultDisplayStr;
    private Ticket ticket;
    private NavController controller;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPayBinding.inflate(getLayoutInflater());

        controller = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView2);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("SMS required on this number")
                .setup(requireActivity());

        binding.cardForm.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getData() != null && result.getData().hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = result.getData().getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

                // Never log a raw card number. Avoid displaying it, but if necessary use getFormattedCardNumber()
                resultDisplayStr = "Card Number: " + scanResult.getRedactedCardNumber() + "\n";

                // Do something with the raw number, e.g.:
                // myService.setCardNumber( scanResult.cardNumber );

                if (scanResult.isExpiryValid()) {
                    resultDisplayStr += "Expiration Date: " + scanResult.expiryMonth + "/" + scanResult.expiryYear + "\n";
                }

                if (scanResult.cvv != null) {
                    // Never log or display a CVV
                    resultDisplayStr += "CVV has " + scanResult.cvv.length() + " digits.\n";
                }

                if (scanResult.postalCode != null) {
                    resultDisplayStr += "Postal Code: " + scanResult.postalCode + "\n";
                }
            } else {
                resultDisplayStr = "Scan was canceled.";
            }
        });

        binding.floatingActionButton.setOnClickListener(v -> {
            onScanPress(requireView());
        });
        binding.submitButton.setOnClickListener(v -> {
            if (binding.cardForm.isValid()) {
                Toast.makeText(requireContext(), "completed !!", Toast.LENGTH_SHORT).show();
                if (controller.getPreviousBackStackEntry().getDestination()
                        .getLabel().toString().equals("more details")) {
                    controller.getPreviousBackStackEntry().getSavedStateHandle()
                            .set("isPaid", true);
                } else if (controller.getPreviousBackStackEntry()
                        .getDestination().getLabel().toString().equals("reservation details")) {
                    controller.getPreviousBackStackEntry().getSavedStateHandle()
                            .set("isReserved", true);
                }
                controller.popBackStack();
            } else {
                Toast.makeText(requireContext(), "complete form", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onScanPress(View v) {
        Intent scanIntent = new Intent(requireActivity(), CardIOActivity.class);
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false
        launcher.launch(scanIntent);
    }
}