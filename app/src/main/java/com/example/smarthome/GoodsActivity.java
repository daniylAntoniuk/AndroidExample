package com.example.smarthome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.smarthome.Network.AuthorizedService;
import com.example.smarthome.Network.NetworkService;
import com.example.smarthome.Network.Tokens;
import com.example.smarthome.Network.models.Categories;
import com.example.smarthome.Network.models.GetGoods;
import com.example.smarthome.Network.models.Goods;
import com.example.smarthome.Network.models.GoodsEntry;
import com.example.smarthome.Network.models.Store;
import com.example.smarthome.Network.models.StoreEntry;
import com.example.smarthome.Network.utils.CommonUtils;
import com.example.smarthome.fragments.goods.GoodsCardRecyclerViewAdapter;
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

public class GoodsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double c1;
    private double c2;
    private String title;
    private List<GoodsEntry> storeEntryList;
    private GoodsCardRecyclerViewAdapter storeAdapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        title = getIntent().getStringExtra("title");
        String desc = getIntent().getStringExtra("desc");
        int id = getIntent().getIntExtra("id",0);
        c1 = getIntent().getDoubleExtra("c1",0);
        c2 = getIntent().getDoubleExtra("c2",0);
        TextView t = findViewById(R.id.textView);
        t.setText(desc);
        getSupportActionBar().setTitle(title);
        GetGoods g = new GetGoods();
        g.setId(id);
        setupViews();
        setRecyclerView();
        AuthorizedService.getInstance()
                .getJSONApi()
                .goods(g)
                .enqueue(new Callback<List<Goods>>() {
                    @Override
                    public void onResponse(Call<List<Goods>> call, Response<List<Goods>> response) {
                        if (response.errorBody() == null && response.isSuccessful()) {
                            List<Goods> list = response.body();
                            storeEntryList.clear();
                            for (Goods item : list) {
                                GoodsEntry tmp = new GoodsEntry(item.getId(), item.getName(), item.getImage(), item.getDescription(),item.getPrice());
                                storeEntryList.add(tmp);
                            }
                            storeAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Goods>> call, Throwable t) {

                    }
                });
    }
    private void setupViews() {
        recyclerView = findViewById(R.id.recycler_view);
    }

    private void setRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2,
                GridLayoutManager.VERTICAL, false));
        storeEntryList = new ArrayList<>();
        storeAdapter = new GoodsCardRecyclerViewAdapter(storeEntryList,  this);

        recyclerView.setAdapter(storeAdapter);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng place = new LatLng(c1, c2);
        mMap.addMarker(new MarkerOptions().position(place).title(title));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 17F));

    }
}