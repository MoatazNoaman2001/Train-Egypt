package com.example.traineygpt.UI.SignIn;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.BitmapTransitionFactory;
import com.example.traineygpt.MainActivity;
import com.example.traineygpt.R;
import com.example.traineygpt.databinding.FragmentStartBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

import jp.wasabeef.glide.transformations.BlurTransformation;


public class StartFragment extends Fragment {
    private FragmentStartBinding binding;


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentStartBinding.inflate(getLayoutInflater());
        Glide.with(requireContext())
                .asDrawable()
                .load(R.drawable.start_img)
                .apply(new RequestOptions().transform(
                        new MultiTransformation<>(
                                new BlurTransformation(8, 2),
                                new CenterCrop()
                        )
                ))
                .addListener(new RequestListener<>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        binding.mainLayout.setImageDrawable(resource);
                        binding.logoImage.setImageDrawable(
                                ResourcesCompat.getDrawable(getResources(), R.drawable.train, requireActivity().getTheme())
                        );
                        MiddleImageAnimation();
                        return true;
                    }
                })
                .into(binding.mainLayout);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private void MiddleImageAnimation() {


        AnimationSet newSet = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
        AlphaAnimation alphaAnimation2 = new AlphaAnimation(1f, 0f);

        newSet.addAnimation(alphaAnimation);
//        newSet.addAnimation(alphaAnimation2);
        newSet.setDuration(1200);

        binding.logoImage.setAnimation(newSet);
        new Handler().postDelayed(() -> {
            if (GoogleSignIn.getLastSignedInAccount(requireContext()) == null) {
                if (FirebaseAuth.getInstance().getCurrentUser() == null)
                Navigation.findNavController(requireView())
                        .navigate(R.id.action_FirstFragment_to_SignInFragment);
                else{
                    Navigation.findNavController(requireView()).navigate(R.id.action_FirstFragment_to_mainActivity);
                }
            } else {
                Navigation.findNavController(requireView()).navigate(R.id.action_FirstFragment_to_mainActivity);
            }
        }, 1200);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}