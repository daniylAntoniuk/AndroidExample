package com.example.smarthome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.example.smarthome.Network.AuthorizedService;
import com.example.smarthome.Network.ImageRequester;
import com.example.smarthome.Network.models.Profile;
import com.example.smarthome.constants.Urls;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Order extends FragmentActivity {

    private GoogleMap mMap;
    private String Adress;
    private int house;
    private int flat;
    private int id;
    private String name;
    private String price;
    private String image;
    private ImageRequester imageRequester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        id = getIntent().getIntExtra("id",0);
        name = getIntent().getStringExtra("name");
        price = getIntent().getStringExtra("price");
        image = getIntent().getStringExtra("image");

        TextView text = findViewById(R.id.product_nazva);
        text.setText(name);

        TextView text2 = findViewById(R.id.product_tsina);
        text2.setText(price);

        NetworkImageView img = findViewById(R.id.image_duzhe_garno);
        imageRequester = ImageRequester.getInstance();
        int i = (int) (new Date().getTime()/1000);
        if(image!=null){
            imageRequester.setImageFromUrl(img, Urls.BASE_URL+"/UserImages/"+image+"?data="+i);
        }
        else {
            imageRequester.setImageFromUrl(img, Urls.BASE_URL+"/UserImages/default.png?data="+i);
        }
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
        Places.initialize(getApplicationContext(), "AIzaSyD8wfDwhcyTZoy451UQq5Hz-hgluDTDKl8");

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NotNull Place place) {
                // TODO: Get info about the selected place.
                Toast.makeText(getApplicationContext(), place.getAddress(), Toast.LENGTH_SHORT).show();
                Adress = place.getAddress();
                //Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
            }


            @Override
            public void onError(@NotNull Status status) {
                // TODO: Handle the error.
                Toast.makeText(getApplicationContext(), status.getStatusCode(), Toast.LENGTH_SHORT).show();

            }
        });
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);


    }

    public void click(View view) {
        com.example.smarthome.Network.models.Order o = new com.example.smarthome.Network.models.Order();
        o.setAdress(Adress);
        o.setGoodsId(id);
        final TextInputEditText house = findViewById(R.id.house);
        final TextInputEditText flat = findViewById(R.id.flat);

        o.setHouse(Integer.parseInt(house.getText().toString()));
        o.setFlat(Integer.parseInt(flat.getText().toString()));
        AuthorizedService.getInstance()
                .getJSONApi()
                .order(o)
                .enqueue(new Callback() {
                             @Override
                             public void onResponse(Call call, Response response) {
                                 if(response.isSuccessful()){
                                     Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                                     startActivity(intent);

                                 }
                             }

                             @Override
                             public void onFailure(Call call, Throwable t) {

                             }
                         });

    }
//
//    @Override
//    public void onMapReady(GoogleMap map) {
//        mMap = map;
//        // TODO: Before enabling the My Location layer, you must request
//        // location permission from the user. This sample does not include
//        // a request for location permission.
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},10);
//        }
//        mMap.setMyLocationEnabled(true);
//        mMap.setOnMyLocationButtonClickListener(this);
//        mMap.setOnMyLocationClickListener(this);
//    }
//    @Override
//    public void onMyLocationClick(@NonNull Location location) {
//        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public boolean onMyLocationButtonClick() {
//        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
//        // Return false so that we don't consume the event and the default behavior still occurs
//        // (the camera animates to the user's current position).
//        return false;
//    }
}