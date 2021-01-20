package com.example.smarthome.account;

import com.example.smarthome.Network.Tokens;

public interface JwtServiceHolder {
    void saveJWTToken(String token,String refreshToken);
    Tokens getToken();
    void removeToken();
}