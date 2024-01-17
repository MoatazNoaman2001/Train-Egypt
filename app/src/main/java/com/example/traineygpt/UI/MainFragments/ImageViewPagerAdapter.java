package com.example.traineygpt.UI.MainFragments;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.traineygpt.databinding.ImagelistCardViewItemBinding;

import java.util.ArrayList;
import java.util.Arrays;

public class ImageViewPagerAdapter extends RecyclerView.Adapter<ImageViewPagerAdapter.ViewHolder>{
    String[] urls;

    public ImageViewPagerAdapter(String[] urls) {
        this.urls = urls;
    }

    @NonNull
    @Override
    public ImageViewPagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ImagelistCardViewItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewPagerAdapter.ViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext())
                .asBitmap()
                .thumbnail(0.3f)
                .load(urls[position])
                .centerCrop()
                .placeholder(android.R.drawable.stat_sys_download)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.binding.imageView3);
        holder.running(holder);
    }

    @Override
    public int getItemCount() {
        return urls.length;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        ImagelistCardViewItemBinding binding;
        public ViewHolder(ImagelistCardViewItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void running(ViewHolder holder){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (holder.getAdapterPosition() == urls.length - 2){
                        ArrayList<String> newUrlsSize = new ArrayList<>(Arrays.asList(urls));
                        newUrlsSize.addAll(Arrays.asList(urls));
                        urls = newUrlsSize.toArray(new String[0]);
                        new Handler().postDelayed(this , 300);
                    }
                }
            } , 100);
        }
    }
}
