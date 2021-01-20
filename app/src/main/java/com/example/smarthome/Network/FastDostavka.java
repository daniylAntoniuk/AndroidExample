package com.example.smarthome.Network;

import com.example.smarthome.Network.models.Categories;
import com.example.smarthome.Network.models.DetailsOrder;
import com.example.smarthome.Network.models.GetGoods;
import com.example.smarthome.Network.models.GetStores;
import com.example.smarthome.Network.models.Goods;
import com.example.smarthome.Network.models.LastOrder;
import com.example.smarthome.Network.models.Order;
import com.example.smarthome.Network.models.Profile;
import com.example.smarthome.Network.models.Store;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FastDostavka {
    @POST("/api/auth/login")
    public Call<Tokens> login(@Body Login m);
    @POST("/api/auth/refresh")
    public Call<Tokens> refresh(@Body Refresh m);
    @GET("/api/account/profile")
    public Call<Profile> profile();
    @GET("/api/category/categories")
    public Call<List<Categories>> categories();
    @POST("/api/store/stores")
    public Call<List<Store>> stores(@Body GetStores m);
    @POST("/api/store/goods")
    public Call<List<Goods>> goods(@Body GetGoods m);
    @POST("/api/Order/order")
    public Call<Void> order(@Body Order m);
    @GET("/api/Order/last-order")
    public Call<LastOrder> lastOrder();
    @GET("/api/Order/orders")
    public Call<List<LastOrder>> listOrders();

    @GET("/api/Order/order/{id}")
    public Call<DetailsOrder> DetailsOrder(@Path("id") int id);

//    @GET("/posts")
//    public Call<List<Post>> getAllPosts();
}
