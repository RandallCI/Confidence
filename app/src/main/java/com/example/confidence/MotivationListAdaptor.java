package com.example.confidence;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.confidence.persistance.Motivation;

public class MotivationListAdaptor extends ListAdapter<Motivation, MotivationViewHolder> {

    //Constructor definition.
    public MotivationListAdaptor(@NonNull DiffUtil.ItemCallback<Motivation> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public MotivationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return MotivationViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull MotivationViewHolder holder, int position) {
        Motivation currentMotivation = getItem(position);
        holder.bindMotivation(currentMotivation.getMotivations());
    }

    //Implement required methods.


    //Create the Diff.
    static class MotivationDiff extends DiffUtil.ItemCallback<Motivation> {

        @Override
        public boolean areItemsTheSame(@NonNull Motivation oldItem, @NonNull Motivation newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Motivation oldItem, @NonNull Motivation newItem) {
            return oldItem.getMotivations().equals(newItem.getMotivations());
        }
    }
}
