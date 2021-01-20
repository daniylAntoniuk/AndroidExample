package com.example.smarthome.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.DetailsOrderActivity;
import com.example.smarthome.Network.ImageRequester;
import com.example.smarthome.Network.NetworkService;
import com.example.smarthome.R;
import com.example.smarthome.Network.models.LastOrder;
import com.example.smarthome.constants.Urls;

import java.util.Date;
import java.util.List;

public class OrdersCardRecyclerViewAdapter extends RecyclerView.Adapter<OrdersCardViewHolder> {

    private List<LastOrder> ordersList;
    private ImageRequester imageRequester;
    private Context context;

    public OrdersCardRecyclerViewAdapter(List<LastOrder> ordersList, Context context) {
        this.ordersList = ordersList;
        imageRequester = ImageRequester.getInstance();
        this.context=context;
    }

    @NonNull
    @Override
    public OrdersCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_card, parent, false);
        return new OrdersCardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(final @NonNull OrdersCardViewHolder holder, final int position) {
        if (ordersList != null && position < ordersList.size()) {
            final LastOrder order = ordersList.get(position);
            holder.category_name.setText(order.getGoodsName());

            holder.category_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.itemView.getContext(), DetailsOrderActivity.class).
                            putExtra("order", order);
                    holder.itemView.getContext().startActivity(intent);
                }
            });
            int i = (int) (new Date().getTime()/1000);
            String url;
            if(order.getGoodsImage()!=null){
                url= Urls.BASE_URL+"/UserImages/"+order.getGoodsImage()+"?data="+i;
            }
            else {
                url = Urls.BASE_URL + "/UserImages/default.png?data=" + i;
            }
            imageRequester.setImageFromUrl(holder.category_image, url);
        }
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }
}