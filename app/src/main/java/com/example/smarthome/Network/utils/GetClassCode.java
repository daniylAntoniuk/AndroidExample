package com.example.smarthome.Network.utils;

import com.example.smarthome.Network.models.Categories;

import java.util.HashMap;
import java.util.List;

public class GetClassCode {
    static HashMap<String, String> codeHash = new HashMap<String,String>();

//    static {
//        init();
//    }

    public static void init(List<Categories> categories) {
        for (Categories el:categories) {
            codeHash.put(el.getName(),Integer.toString(el.getId()));
        }


        //codeHash.put("key", "value");
        //codeHash.put("key", "value");
        //codeHash.put("key", "value");

    }

    public static String getCode(String param) {
        return codeHash.get(param);
    }
}