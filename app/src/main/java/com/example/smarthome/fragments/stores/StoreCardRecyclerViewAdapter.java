package com.example.smarthome.fragments.stores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Network.models.StoreEntry;
import com.example.smarthome.R;
import com.example.smarthome.Network.ImageRequester;
import com.example.smarthome.constants.Urls;


import java.util.Date;
import java.util.List;

/**
 * Adapter used to show a simple grid of products.
 */
public class StoreCardRecyclerViewAdapter extends RecyclerView.Adapter<StoreCardViewHolder> {

    private List<StoreEntry> productList;
    private ImageRequester imageRequester;
    private Context context;


    StoreCardRecyclerViewAdapter(List<StoreEntry> productList, Context context) {
        this.productList = productList;
        this.context=context;
        imageRequester = ImageRequester.getInstance();
    }

    @NonNull
    @Override
    public StoreCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater
                .from(parent.getContext()).inflate(R.layout.store_card, parent, false);
        return new StoreCardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreCardViewHolder holder, final int position) {
        if (productList != null && position < productList.size()) {
            StoreEntry product = productList.get(position);
            holder.productTitle.setText(product.name);
            holder.productDescription.setText(product.description);
            holder.id =  product.id;
            holder.c1 = product.getCoordinate1();
            holder.c2 = product.getCoordinate2();
            int i = (int) (new Date().getTime()/1000);
            if(product.image!=null){
                imageRequester.setImageFromUrl(holder.productImage, Urls.BASE_URL+"/UserImages/"+product.image+"?data="+i);
            }
            else {
                imageRequester.setImageFromUrl(holder.productImage, Urls.BASE_URL+"/UserImages/default.png?data="+i);
            }
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
