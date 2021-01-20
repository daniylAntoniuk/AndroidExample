package com.example.smarthome.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Network.AuthorizedService;
import com.example.smarthome.Network.models.GetStores;
import com.example.smarthome.Network.models.LastOrder;
import com.example.smarthome.Network.models.Store;
import com.example.smarthome.Network.models.StoreEntry;
import com.example.smarthome.Network.utils.CommonUtils;
import com.example.smarthome.R;
import com.example.smarthome.fragments.stores.StoreCardRecyclerViewAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersFragment extends Fragment {

    private List<LastOrder> ordersList;
    private OrdersCardRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        setupViews(view);

        setRecyclerView();
        loadStoresList();
        return view;
    }

    private void setupViews(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
    }

    private void setRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1,
                GridLayoutManager.VERTICAL, false));
        ordersList = new ArrayList<>();
        adapter = new OrdersCardRecyclerViewAdapter(ordersList,  getContext());

        recyclerView.setAdapter(adapter);
    }

    private void loadStoresList() {
        // CommonUtils.showLoading(getContext());

        AuthorizedService.getInstance()
                .getJSONApi()
                .listOrders()
                .enqueue(new Callback<List<LastOrder>>() {
                    @Override
                    public void onResponse(Call<List<LastOrder>> call, Response<List<LastOrder>> response) {
                        if (response.errorBody() == null && response.isSuccessful()) {
                            List<LastOrder> list = response.body();
                            ordersList.clear();
                            ordersList.addAll(0, list);
                            adapter.notifyDataSetChanged();
                            CommonUtils.hideLoading();
                        }
                        else {
                            CommonUtils.hideLoading();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<LastOrder>> call, Throwable t) {
                        CommonUtils.hideLoading();
                    }
                });
    }


}