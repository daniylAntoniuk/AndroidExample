package com.example.smarthome.Network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LastOrder implements Serializable {
    @SerializedName("adress")
    @Expose
    private String adress;
    @SerializedName("house")
    @Expose
    private int house;
    @SerializedName("flat")
    @Expose
    private int flat;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("goodsName")
    @Expose
    private String  goodsName;
    @SerializedName("goodsImage")
    @Expose
    private String  goodsImage;

    public LastOrder() {
    }

    public LastOrder(String adress, int house, int flat, int id, String status, String goodsName, String goodsImage) {
        this.adress = adress;
        this.house = house;
        this.flat = flat;
        this.id = id;
        this.status = status;
        this.goodsName = goodsName;
        this.goodsImage = goodsImage;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public String getAdress() {
        return adress;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getHouse() {
        return house;
    }

    public void setHouse(int house) {
        this.house = house;
    }

    public int getFlat() {
        return flat;
    }

    public void setFlat(int flat) {
        this.flat = flat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
