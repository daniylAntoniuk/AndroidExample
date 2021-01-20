package com.example.smarthome.fragments.stores;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.smarthome.GoodsActivity;
import com.example.smarthome.R;
import com.google.android.material.button.MaterialButton;

public class StoreCardViewHolder extends RecyclerView.ViewHolder {

    private View view;
    public NetworkImageView productImage;
    public TextView productTitle;
    public TextView productDescription;
    public MaterialButton b;
    public int id;
    public double c1;
    public double c2;
    public StoreCardViewHolder(@NonNull View itemView) {
        super(itemView);
        this.view = itemView;
        productImage = itemView.findViewById(R.id.product_image);
        productTitle = itemView.findViewById(R.id.product_title);
        productDescription = itemView.findViewById(R.id.product_description);
        b = itemView.findViewById(R.id.product_button);
        b.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {
                                     Intent intent = new Intent(view.getContext(), GoodsActivity.class);
                                     intent.putExtra("title", productTitle.getText());
                                     intent.putExtra("id",id);
                                     intent.putExtra("c1",c1);
                                     intent.putExtra("c2",c2);
                                     intent.putExtra("desc",productDescription.getText());

                                     view.getContext().startActivity(intent);
                                 }
                             }
        );
    }

    public View getView() {
        return view;
    }


}