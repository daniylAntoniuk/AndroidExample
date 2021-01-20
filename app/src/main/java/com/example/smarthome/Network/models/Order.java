package com.example.smarthome.Network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {
    @SerializedName("adress")
    @Expose
    private String Adress;
    @SerializedName("house")
    @Expose
    private int House;
    @SerializedName("flat")
    @Expose
    private int Flat;
    @SerializedName("goodsId")
    @Expose
    private int GoodsId;

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String adress) {
        Adress = adress;
    }

    public int getHouse() {
        return House;
    }

    public void setHouse(int house) {
        House = house;
    }

    public int getFlat() {
        return Flat;
    }

    public void setFlat(int flat) {
        Flat = flat;
    }

    public int getGoodsId() {
        return GoodsId;
    }

    public void setGoodsId(int goodsId) {
        GoodsId = goodsId;
    }
}
