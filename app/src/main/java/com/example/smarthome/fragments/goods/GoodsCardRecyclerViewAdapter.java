package com.example.smarthome.fragments.goods;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Network.ImageRequester;
import com.example.smarthome.Network.models.Goods;
import com.example.smarthome.Network.models.GoodsEntry;
import com.example.smarthome.Network.models.StoreEntry;
import com.example.smarthome.R;
import com.example.smarthome.constants.Urls;

import java.util.Date;
import java.util.List;

/**
 * Adapter used to show a simple grid of products.
 */
public class GoodsCardRecyclerViewAdapter extends RecyclerView.Adapter<GoodsCardViewHolder> {

    private List<GoodsEntry> productList;
    private ImageRequester imageRequester;
    private Context context;


    public GoodsCardRecyclerViewAdapter(List<GoodsEntry> productList, Context context) {
        this.productList = productList;
        this.context=context;
        imageRequester = ImageRequester.getInstance();
    }

    @NonNull
    @Override
    public GoodsCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater
                .from(parent.getContext()).inflate(R.layout.goods_card, parent, false);
        return new GoodsCardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull GoodsCardViewHolder holder, final int position) {
        if (productList != null && position < productList.size()) {
            GoodsEntry product = productList.get(position);
            holder.productTitle.setText(product.name);
            holder.productDescription.setText(product.description);
            holder.id =  product.id;
            holder.image = product.image;
            holder.productPrice.setText(Double.toString(product.price));
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
