package com.example.confidence;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MotivationViewHolder extends RecyclerView.ViewHolder {

    //Properties
    private final TextView messageView;
    //Constructor
    private MotivationViewHolder(@NonNull View itemView) {
        super(itemView);
        messageView = itemView.findViewById(R.id.message_to_view);
    }
    //Methods
    public void bindMotivation(String message) { messageView.setText(message); }

    static MotivationViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_view, parent, false);
        return new MotivationViewHolder(view);
    }

}
