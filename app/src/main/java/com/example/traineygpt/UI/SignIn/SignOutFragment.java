package com.example.traineygpt.UI.SignIn;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.traineygpt.MainActivity;
import com.example.traineygpt.R;
import com.example.traineygpt.databinding.FragmentSignOutBinding;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseAuthWebException;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.facebook.login.widget.LoginButton;

public class SignOutFragment extends Fragment {

    private FragmentSignOutBinding binding;
    private GoogleSignInClient client;
    private ActivityResultLauncher<Intent> launcher;
    private CallbackManager mcallbackmanger;
    private NavController navController;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.webClient))
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(requireActivity(), gso);

        FacebookSdk.sdkInitialize(requireContext());
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                fireBaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
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

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSignOutBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.signInIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });

        navController = Navigation.findNavController(requireView());
        navController.getCurrentBackStackEntry().getSavedStateHandle()
                .getLiveData("isFinished" , false)
                .observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if (aBoolean){
                            navController.navigate(R.id.action_SignOutFragment_to_mainActivity);
                        }
                    }
                });

        mcallbackmanger = CallbackManager.Factory.create();

        LoginButton button = (LoginButton) binding.facebookIcon;
        button.setReadPermissions("email", "public_profile");
        button.registerCallback(mcallbackmanger, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("TAG", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("TAG", "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("TAG", "facebook:onError", error);
            }
        });

        binding.signOutIcon.setOnClickListener(v -> {

            String Email = binding.TextInputEditTextEmail.getText().toString().trim();
            String Name = binding.TextInputEditTextName.getText().toString().trim();
            String PassWord = binding.TextInputEditTextPassWord.getText().toString().trim();
            if (Email.isEmpty()
                    || Name.isEmpty()
                    || PassWord.isEmpty()) {
                Toast.makeText(v.getContext(), "you must fill empty field", Toast.LENGTH_SHORT).show();
            } else if (!Email.contains("@")
                    || !Email.endsWith(".com")
                    || IsAllLowerCase(Email)
                    || IsAllUpper(Email)) {
                Toast.makeText(v.getContext(), "Invalid Email", Toast.LENGTH_SHORT).show();
            } else {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(Email, PassWord)
                        .addOnSuccessListener(authResult -> {
                            UserProfileChangeRequest.Builder request = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(Name);
                            UpdateAcount(request);
                            navController.navigate(R.id.action_SignOutFragment_to_createTicket);
                        })
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

        binding.googleIcon.setOnClickListener(v -> launcher.launch(new Intent(client.getSignInIntent())));
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(@NonNull AuthResult authResult) {
                        UserProfileChangeRequest.Builder builder = new UserProfileChangeRequest.Builder();
                        UpdateAcount(builder);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "onFailure: " + e.getMessage());
                    }
                });
    }

    private void UpdateAcount(UserProfileChangeRequest.Builder request) {
        FirebaseAuth.getInstance().getCurrentUser()
                .updateProfile(request.build())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(@NonNull Void unused) {
                        navController.navigate(R.id.action_SignOutFragment_to_createTicket);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mcallbackmanger.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}