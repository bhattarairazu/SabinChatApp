package com.example.manjil.sabinchat.RestApi;

import com.example.manjil.sabinchat.Model.Model_messagelist;
import com.example.manjil.sabinchat.Model.UserSignup;
import com.example.manjil.sabinchat.Model.user_list.Userlists;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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
    //getting message list
    @GET("message/{user_id}/{friend_id}")
    Call<Model_messagelist> mgetmessagelist(@Path("user_id") int user_id,@Path("friend_id") int friend_id);
    //sending message to server without images
    @FormUrlEncoded
    @POST("send")
    Call<UserSignup> msend_singlemessage(@Field("message") String message,@Field("from_id") int from_id,@Field("to_id") int to_id);
    //getting all users
    @GET("user/all")
    Call<Userlists> mgetuser_list();

    //updating user status
    @GET("user/update/status/{user_id}/{status}")
    Call<UserSignup> mupdate_user(@Path("user_id") int user_id,@Path("status") int status);
        //multipart request sending messages wtih images
    @Multipart
    @POST("send")
    Call<UserSignup> uploadimage(@PartMap()Map<String,RequestBody> partmap, @Part MultipartBody.Part file);

//updating name of the user
    @FormUrlEncoded
    @POST("user/update/{user_id}")
    Call<UserSignup> update_username(@Path("user_id") int user_id,@Field("name") String name);

    //updating user images
    @Multipart
    @POST("update/picture/14")
    Call<UserSignup> update_profileimage(@Part MultipartBody.Part file);




}
