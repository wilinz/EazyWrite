package com.eazywrite.app.data.network;

import android.util.Log;

import com.eazywrite.app.data.model.RegisterResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginNetwork {

    public static RegisterInterface getRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://hw.wilinz.com:444")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RegisterInterface service = retrofit.create(RegisterInterface.class);
        return service;
    }
}
