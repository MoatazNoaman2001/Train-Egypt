package com.example.traineygpt.UI.MainFragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.traineygpt.Model.Train;
import com.example.traineygpt.UI.MainFragments.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.traineygpt.databinding.FragmentTrainItemBinding;

import java.text.SimpleDateFormat;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyTrainItemsRecyclerViewAdapter extends RecyclerView.Adapter<MyTrainItemsRecyclerViewAdapter.ViewHolder> {
    private final List<Train> mValues;

    public MyTrainItemsRecyclerViewAdapter(List<Train> items) {
        mValues = items;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentTrainItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Glide.with(holder.itemView.getContext())
                .asBitmap()
                .load(mValues.get(position).Train_type.equals("FIRST")?
                        "https://tse1.mm.bing.net/th/id/OIP.1uv1MfQQLcbgXTm1jxKwHgHaFc?pid=ImgDet&rs=1":
                        "https://th.bing.com/th/id/R.ebcc947c9cbf0dbc07665edc5d11f007?rik=0owESffyq78UiQ&riu=http%3a%2f%2fenglish.ahram.org.eg%2fMedia%2fNews%2f2020%2f6%2f24%2f2020-637286039589669667-966.jpg&ehk=RPJFBgO1a5U3d2O79W95lwfxwyo94tNOo1nE6xBTmSI%3d&risl=&pid=ImgRaw&r=0")
                .apply(new RequestOptions().transform(new RoundedCornersTransformation(20 , 5)))
                .transition(new BitmapTransitionOptions().crossFade())
                .into(holder.binding.trainImage);

        holder.binding.startDate.setText(
                new SimpleDateFormat("hh:mm a").format(mValues.get(position).Start_Time)
        );

        holder.binding.endDate.setText(
                new SimpleDateFormat("hh:mm a").format(mValues.get(position).End_Time)
        );
        holder.binding.degree.setText(mValues.get(position).Train_type);
        holder.binding.support.setText(mValues.get(position).Train_Supports);
        holder.binding.startDis.setText(mValues.get(position).Start_Destination);
        holder.binding.endDis.setText(mValues.get(position).Termination_Station);
        holder.binding.TrainNumber.setText(mValues.get(position).Train_number);
        holder.binding.more.setOnClickListener(v -> {
            if (holder.binding.moreLayout.getVisibility() == View.VISIBLE){
                TransitionManager.beginDelayedTransition(
                        holder.binding.getRoot() , new AutoTransition()
                );
                holder.binding.moreLayout.setVisibility(View.GONE);
            }else{
                TransitionManager.beginDelayedTransition(
                        holder.binding.getRoot() , new AutoTransition()
                );
                holder.binding.moreLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.binding.moreLayout.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        FragmentTrainItemBinding binding;

        public ViewHolder(FragmentTrainItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }
}