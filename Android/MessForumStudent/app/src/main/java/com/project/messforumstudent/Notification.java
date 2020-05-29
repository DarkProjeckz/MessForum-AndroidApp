package com.project.messforumstudent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textview.MaterialTextView;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Notification extends Fragment {

    private RetrofitInterface retrofitInterface;
    private Retrofit retrofit;
    private String BASE_URL;
    MaterialTextView currentText;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.notification_fragment,container,false);
        BASE_URL = getActivity().getResources().getString(R.string.serverIp);
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);
        currentText = (MaterialTextView)view.findViewById(R.id.text_area_notice);
        MaterialTextView name = (MaterialTextView)view.findViewById(R.id.username_student);
        name.setText("Hi "+SaveSharedPreference.getUserName(getActivity())+"!!");
        notifii();
        return view;
    }

    private void notifii() {
        final JSONObject[] json = new JSONObject[1];
        Call<JsonObject> call = retrofitInterface.getNotification(SaveSharedPreference.getApiKey(getActivity()));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.code() == 200) {

                    String message = null;
                    try {
                        json[0] = new JSONObject(response.body().toString());
                        message = json[0].getString("message");
                        currentText.setText(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
