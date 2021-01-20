package com.example.smarthome.ui.dashboard;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.smarthome.R;
import com.google.android.material.button.MaterialButton;


public class OrdersCardViewHolder extends RecyclerView.ViewHolder {

    public NetworkImageView category_image;
    public TextView category_name;
    private View view;
    public MaterialButton category_button;

    public OrdersCardViewHolder(@NonNull View itemView) {
        super(itemView);
        this.view = itemView;
        category_image = itemView.findViewById(R.id.orders_image);
        category_name = itemView.findViewById(R.id.orders_name);
        category_button = itemView.findViewById(R.id.category_button);
    }

    public View getView() {
        return view;
    }
}