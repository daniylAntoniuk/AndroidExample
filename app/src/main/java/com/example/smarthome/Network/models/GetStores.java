package com.example.smarthome.Network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetStores {
    @SerializedName("category")
    @Expose
    private int category;

    public GetStores(int category) {
        this.category = category;
    }

    public int getId() {
        return category;
    }

    public void setId(int category) {
        this.category = category;
    }
}