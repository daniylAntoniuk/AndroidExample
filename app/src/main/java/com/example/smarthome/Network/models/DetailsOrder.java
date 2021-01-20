package com.example.smarthome.Network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DetailsOrder implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("adress")
    @Expose
    private String adress;
    @SerializedName("flat")
    @Expose
    private int flat;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("house")
    @Expose
    private int house;
    @SerializedName("goodsName")
    @Expose
    private String  goodsName;
    @SerializedName("goodsImage")
    @Expose
    private String  goodsImage;
    @SerializedName("orderDate")
    @Expose
    private String  orderDate;
    @SerializedName("statusId")
    @Expose
    private String  statusId;

    public DetailsOrder() {
    }

    public DetailsOrder(int id, String adress, int flat, String status, int house, String goodsName, String goodsImage, String orderDate, String statusId) {
        this.id = id;
        this.adress = adress;
        this.flat = flat;
        this.status = status;
        this.house = house;
        this.goodsName = goodsName;
        this.goodsImage = goodsImage;
        this.orderDate = orderDate;
        this.statusId = statusId;
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

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }
}
