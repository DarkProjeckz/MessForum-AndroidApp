package com.project.messforumstudent;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitInterface {

    @POST("/login")
    Call<JsonObject> login(@Body HashMap<String, String> map);

    @POST("/data")
    Call<List<MenuData>> executeData(@Header("authorization") String key);

    @POST("/user/{id}")
    Call<LoginResult> userDetails(@Header("authorization") String key,@Path("id") String uid);

    @POST("/getnotification")
    Call<JsonObject> getNotification(@Header("authorization") String key);

    @POST("/regfood")
    Call<Void> registerCount(@Header("authorization") String key,@Body HashMap<String, String> map);

    @POST("/tokenreg")
    Call<JsonObject> registerToken(@Header("authorization") String key, @Body HashMap<String, String> map);


}
