package com.eazywrite.app.data.network.service;

import com.eazywrite.app.data.model.RegisterResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface AccountService {

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("account/verify")
    Call<RegisterResponse> postVerify(@Body RequestBody body);

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("account/register")
    Call<RegisterResponse> postSignUp(@Body RequestBody body);

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("account/login")
    Call<RegisterResponse> postLogin(@Body RequestBody body);

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @PUT("password/reset")
    Call<RegisterResponse> postReset(@Body RequestBody body);
}
