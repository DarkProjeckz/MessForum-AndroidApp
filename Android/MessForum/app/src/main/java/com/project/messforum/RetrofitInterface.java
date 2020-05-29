package com.project.messforum;

import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitInterface {

    /////////////////////LOGIN///////////////////////////////
    @POST("/adminlogin")
    Call<JsonObject> login(@Body HashMap<String, String> map);

    /////////////////Dashboard//////////////////////////////
    @POST("/data")
    Call<List<MenuData>> executeData(@Header("authorization") String key);

    @POST("/modmeal")
    Call<Void> executemodify (@Header("authorization") String key,@Body HashMap<String, String> map);

    /////////////////User//////////////////////////////
    @POST("/register")
    Call<Void> register(@Header("authorization") String key,@Body HashMap<String, String> map);

    @POST("/user/{id}")
    Call<LoginResult> userDetails(@Header("authorization") String key,@Path("id") String uid);

    @POST("/allusers")
    Call<List<LoginResult>> allUsers(@Header("authorization") String key);

    @POST("/deleteusers/{id}")
    Call<Void> deleteUser(@Header("authorization") String key,@Path("id") int uid);

    /////////////////Count//////////////////////////////
    @POST("/fetchcount")
    Call<CountResult> fetchCount(@Header("authorization") String key);

    @POST("/endregistration")
    Call<Void> endRegistration(@Header("authorization") String key);

    /////////////////Token//////////////////////////////

    @POST("/token/{roll}")
    Call<List<TokenDetails>> executetokensearch(@Header("authorization") String key,@Path("roll") String roll);

    @POST("/token")
    Call<List<TokenDetails>> executetokendetails(@Header("authorization") String key);

    @POST("/deletetoken/{roll}")
    Call<Void> executetokendelete(@Header("authorization") String key,@Path("roll") String roll);

    /////////////////Notification/////////////////////////

    @POST("/addnotification")
    Call<Void> addNotification(@Header("authorization") String key,@Body HashMap<String, String> map);

    @POST("/removenotification")
    Call<Void> removeNotification(@Header("authorization") String key);

    @POST("/getnotification")
    Call<JsonObject> getNotification(@Header("authorization") String key);


}
