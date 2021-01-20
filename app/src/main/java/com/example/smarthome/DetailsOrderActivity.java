package com.example.smarthome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.smarthome.Network.AuthorizedService;
import com.example.smarthome.Network.ImageRequester;
import com.example.smarthome.Network.models.DetailsOrder;
import com.example.smarthome.Network.models.LastOrder;
import com.example.smarthome.Network.utils.CommonUtils;
import com.example.smarthome.constants.Urls;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsOrderActivity extends AppCompatActivity {

    private NetworkImageView photo;
    private ImageRequester imageRequester;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_order);

        photo = (NetworkImageView)findViewById(R.id.photo);
        imageRequester = ImageRequester.getInstance();
        LastOrder order = (LastOrder) getIntent().getSerializableExtra("order");

        AuthorizedService.getInstance()
                .getJSONApi()
                .DetailsOrder(order.getId())
                .enqueue(new Callback<DetailsOrder>() {
                    @Override
                    public void onResponse(Call<DetailsOrder> call, Response<DetailsOrder> response) {
                        if (response.errorBody() == null && response.isSuccessful()) {
                            DetailsOrder order = response.body();
                            int i = (int) (new Date().getTime()/1000);
                            String url;
                            if(order.getGoodsImage()!=null){
                                url= Urls.BASE_URL+"/UserImages/"+order.getGoodsImage()+"?data="+i;
                            }
                            else {
                                url = Urls.BASE_URL + "/UserImages/default.png?data=" + i;
                            }
                            imageRequester.setImageFromUrl(photo, url);
                            TextView productName = findViewById(R.id.productName);
                            productName.setText(order.getGoodsName());
                            TextView adress = findViewById(R.id.adress);
                            adress.setText(order.getAdress());
                            TextView home = findViewById(R.id.houseHome);
                            home.setText(Integer.toString(order.getHouse()));
                            TextView flatApartment = findViewById(R.id.flatApartment);
                            flatApartment.setText(Integer.toString(order.getFlat()));
                            TextView status = findViewById(R.id.status);
                            status.setText(order.getStatus());

                            CommonUtils.hideLoading();
                        }
                        else {
                            CommonUtils.hideLoading();
                        }
                    }

                    @Override
                    public void onFailure(Call<DetailsOrder> call, Throwable t) {
                        CommonUtils.hideLoading();
                    }
                });
    }
}