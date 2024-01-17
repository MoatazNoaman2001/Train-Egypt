package com.example.traineygpt;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.traineygpt.UI.SearchFragment;
import com.example.traineygpt.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.traineygpt.databinding.FragmentTrainListBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyTrainListRecyclerViewAdapter extends RecyclerView.Adapter<MyTrainListRecyclerViewAdapter.ViewHolder> {

    private final List<PlaceholderItem> mValues;
    String type;
    NavController controller;
    Activity activity;

    public  MyTrainListRecyclerViewAdapter(List<PlaceholderItem> items, TrainListFragment trainListFragment, String type, NavController controller) {
        mValues = items;
        this.type = type;
        this.controller = controller;
        this.activity = trainListFragment.requireActivity();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentTrainListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(String.valueOf(mValues.get(position).id));
        holder.mContentView.setText(mValues.get(position).content);
        holder.mContentView.setOnLongClickListener(v -> {
            TransitionManager.beginDelayedTransition((ViewGroup) holder.itemView.getRootView(), new AutoTransition());
            holder.mContentView.setText(holder.mContentView.getText().toString().concat( "\n"+ mValues.get(position).details));
            return true;
        });

        holder.mContentView.setOnClickListener(v -> {
            if (type.equals(
                    TrainListFragment.clickEventTypeForSearchTrain.START_DISTINCTION.name()
            )){
            controller.getPreviousBackStackEntry().getSavedStateHandle().set("Dis",
                    mValues.get(position).content);
                controller.popBackStack();
            }else{
                controller.getPreviousBackStackEntry().getSavedStateHandle().set("Tir",
                        mValues.get(position).content);
                controller.popBackStack();
            }
        });
    }


    @Override
    public void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public PlaceholderItem mItem;

        public ViewHolder(FragmentTrainListBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}