package com.example.manjil.sabinchat.RestApi;

import com.example.manjil.sabinchat.Model.UserSignup;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetroInterface {

//for signup of usersd
   // @Headers("Content-Type:application/json")
    @FormUrlEncoded
    @POST("signup")
    Call<UserSignup> mregister_information(@Field("name") String name,@Field("username") String username,@Field("password") String password);
    //for singin
    @FormUrlEncoded
    @POST("login")
    Call<UserSignup> mlogins(@Field("username") String username,@Field("password") String password);

}
