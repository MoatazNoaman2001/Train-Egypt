package com.example.traineygpt.UI.SignIn;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.traineygpt.R;
import com.example.traineygpt.databinding.FragmentSignInBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseAuthWebException;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignInFragment extends Fragment {

    private FragmentSignInBinding binding;
    private GoogleSignInClient client;
    private ActivityResultLauncher<Intent> launcher;
    private NavController navController;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.webClient))
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(requireActivity(), gso);

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
            task
                    .addOnSuccessListener(googleSignInAccount -> {
                        Toast.makeText(requireContext(), "google success", Toast.LENGTH_SHORT).show();
                        fireBaseAuthWithGoogle(googleSignInAccount);
                    })
                    .addOnFailureListener(e -> Toast.makeText(requireContext(), "sign failed" + e.getMessage(), Toast.LENGTH_SHORT).show());
        });

        binding = FragmentSignInBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.TextInputLayoutPassWord.setEndIconTintList(ColorStateList.valueOf(
                ResourcesCompat.getColor(getResources(), R.color.primaryLightColor, requireActivity().getTheme())
        ));
        binding.RegristButton.setOnClickListener(v -> Navigation.findNavController(v)
                .navigate(R.id.action_First2Fragment_to_Second2Fragment));

        navController = Navigation.findNavController(requireView());
        navController.getCurrentBackStackEntry().getSavedStateHandle()
                .getLiveData("isFinished" , false)
                .observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if (aBoolean){
                            navController.navigate(R.id.action_SignInFragment_to_mainActivity);
                            navController.getCurrentBackStackEntry().getSavedStateHandle()
                                    .set("isFinished" , false);
                        }
                    }
                });


        binding.GoogleIcon.setOnClickListener(v -> launcher.launch(new Intent(client.getSignInIntent())));

        binding.signInIcon.setOnClickListener(v -> {

            String Email = binding.TextInputEditTextEmail.getText().toString().trim();
            String PassWord = binding.TextInputEditTextPassWord.getText().toString().trim();
            if (Email.isEmpty()
                    || PassWord.isEmpty()) {
                Toast.makeText(v.getContext(), "you must fill empty field", Toast.LENGTH_SHORT).show();
            } else if (!Email.contains("@")
                    || !Email.endsWith(".com")
                    || IsAllLowerCase(Email)
                    || IsAllUpper(Email)) {
                Toast.makeText(v.getContext(), "Invalid Email", Toast.LENGTH_SHORT).show();
            } else {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(Email, PassWord)
                        .addOnSuccessListener(authResult -> navController.navigate(R.id.action_SignInFragment_to_createTicket))
                        .addOnFailureListener(e -> {
                            if (e instanceof FirebaseAuthEmailException) {
                                Toast.makeText(requireContext(), "invalid email", Toast.LENGTH_SHORT).show();
                            } else if (e instanceof FirebaseAuthWeakPasswordException) {
                                Toast.makeText(requireContext(), "invalid Password", Toast.LENGTH_SHORT).show();
                            } else if (e instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(requireContext(), "invalid Email already in use", Toast.LENGTH_SHORT).show();
                            } else if (e instanceof FirebaseAuthWebException) {
                                Toast.makeText(requireContext(), "Connection error", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(requireContext(), e.getMessage() + Email, Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }
    private void fireBaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnSuccessListener(authResult -> {
                    UserProfileChangeRequest.Builder builder = new UserProfileChangeRequest.Builder()
                            .setDisplayName(account.getDisplayName())
                            .setPhotoUri(account.getPhotoUrl());
                    UpdateAcount(builder);
                })
                .addOnFailureListener(e -> {

                });
    }
    private void UpdateAcount(UserProfileChangeRequest.Builder request) {
        FirebaseAuth.getInstance().getCurrentUser()
                .updateProfile(request.build()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(@NonNull Void unused) {
                navController.navigate(R.id.action_SignInFragment_to_createTicket);
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        requireView().setFocusableInTouchMode(true);
        requireView().requestFocus();
        requireView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    requireActivity().finishAffinity();
                    return true;
                }
                return false;
            }
        });
    }


    private boolean IsAllUpper(String str) {
        for (int i = 0; i < str.length(); i++)
            if (Character.isLowerCase(str.charAt(i)))
                return false;
        return true;
    }

    private boolean IsAllLowerCase(String str) {
        for (int i = 0; i < str.length(); i++)
            if (Character.isUpperCase(str.charAt(i)))
                return false;
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}