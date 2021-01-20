package com.example.smarthome.fragments.goods;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.smarthome.GoodsActivity;
import com.example.smarthome.Order;
import com.example.smarthome.R;
import com.google.android.material.button.MaterialButton;

public class GoodsCardViewHolder extends RecyclerView.ViewHolder {

    private View view;
    public NetworkImageView productImage;
    public TextView productTitle;
    public TextView productDescription;
    public TextView productPrice;
    public MaterialButton b;
    public int id;
    public String image;
    public double price;
    public GoodsCardViewHolder(@NonNull View itemView) {
        super(itemView);
        this.view = itemView;
        productImage = itemView.findViewById(R.id.product_image);
        productTitle = itemView.findViewById(R.id.product_title);
        productDescription = itemView.findViewById(R.id.product_description);
        productPrice = itemView.findViewById(R.id.product_price);

        b = itemView.findViewById(R.id.product_button);
        b.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {
                                     Intent intent = new Intent(view.getContext(), Order.class);
                                     intent.putExtra("id",id);
                                     intent.putExtra("name", productTitle.getText());
                                     intent.putExtra("price", productPrice.getText());
                                     intent.putExtra("image", image);
                                     view.getContext().startActivity(intent);
                                 }
                             }
        );
    }

    public View getView() {
        return view;
    }


}