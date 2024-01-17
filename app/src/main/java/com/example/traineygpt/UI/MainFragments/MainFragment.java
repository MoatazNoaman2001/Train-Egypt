package com.example.traineygpt.UI.MainFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.traineygpt.LastMinuteTrainFragment;
import com.example.traineygpt.UI.SignIn.FragmentViewPagerAdapter;
import com.example.traineygpt.databinding.FragmentMainBinding;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {


    FragmentMainBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMainBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.imagesViewPager.setAdapter(new ImageViewPagerAdapter(new String[]{
                "https://images.pexels.com/photos/5098043/pexels-photo-5098043.jpeg?cs=srgb&dl=pexels-elena-saharova-5098043.jpg&fm=jpg",
                "https://images.pexels.com/photos/2928780/pexels-photo-2928780.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",
                "https://images.pexels.com/photos/3787405/pexels-photo-3787405.jpeg?cs=srgb&dl=pexels-jerry-wang-3787405.jpg&fm=jpg",
                "https://images.pexels.com/photos/5949745/pexels-photo-5949745.jpeg?cs=srgb&dl=pexels-sarah-ching-5949745.jpg&fm=jpg",
                "https://images.pexels.com/photos/6100146/pexels-photo-6100146.jpeg?cs=srgb&dl=pexels-mike-6100146.jpg&fm=jpg",
                "https://images.pexels.com/photos/1658967/pexels-photo-1658967.jpeg?cs=srgb&dl=pexels-senuscape-1658967.jpg&fm=jpg",
                "https://images.pexels.com/photos/1548693/pexels-photo-1548693.jpeg?cs=srgb&dl=pexels-david-bartus-1548693.jpg&fm=jpg",
                "https://images.pexels.com/photos/258510/pexels-photo-258510.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",
                "https://images.pexels.com/photos/870799/pexels-photo-870799.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                "https://images.pexels.com/photos/302692/pexels-photo-302692.jpeg?cs=srgb&dl=pexels-pixabay-302692.jpg&fm=jpg",
                "https://images.pexels.com/photos/1459518/pexels-photo-1459518.jpeg?cs=srgb&dl=pexels-felix-mittermeier-1459518.jpg&fm=jpg",
                "https://images.pexels.com/photos/96409/pexels-photo-96409.jpeg?cs=srgb&dl=pexels-kasuma-96409.jpg&fm=jpg"
        }));
        try {
            run(binding.imagesViewPager);
        } catch (Exception ignored) {
        }
        FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(requireActivity().getSupportFragmentManager(), getLifecycle(), new Fragment[]{
                new TrainItemsFragment() ,   new TrainList(),new blogFragment(),
        });
        binding.fragmentViewPager.setAdapter(adapter);

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("On Board"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Last Minute Train"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Blog"));


        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.fragmentViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.fragmentViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        requireView().setFocusableInTouchMode(true);
        requireView().requestFocus();
        requireView().setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                requireActivity().finishAffinity();
                return true;
            }
            return false;
        });
    }

    private void run(ViewPager2 imagesViewPager) {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (imagesViewPager.getCurrentItem() < imagesViewPager.getAdapter().getItemCount()) {
                    imagesViewPager.setCurrentItem(imagesViewPager.getCurrentItem() + 1);
                    handler.postDelayed(this, 5000);
                }
            }
        };
        handler.postDelayed(runnable, 5000);
    }

}