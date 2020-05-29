package com.project.messforum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL ;
    //private String BASE_URL = "http://192.168.43.17:3000";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        BASE_URL = getApplicationContext().getResources().getString(R.string.serverIp);
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);
    }

    public void goApp(View v)
    {
        final JSONObject[] json = new JSONObject[1];
        //Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.example.ashif.sqlite");
        TextInputLayout reg = (TextInputLayout)findViewById(R.id.regno);
        TextInputLayout pass = (TextInputLayout)findViewById(R.id.pass);
        //TextInputEditText uid = (TextInputEditText)findViewById(R.id.uid);
        //TextInputEditText upid = (TextInputEditText)findViewById(R.id.upid);
        reg.setErrorEnabled(false);
        pass.setErrorEnabled(false);
        int ans = 0;
        String r = reg.getEditText().getText().toString();
        String p = pass.getEditText().getText().toString();
        if(r.isEmpty() || r.length() == 0 || r.equals("") || r == null)
        {
            reg.setErrorEnabled(true);

            reg.setError("Can't be empty");

            ans = 1;
        }
        if(p.isEmpty() || p.length() == 0 || p.equals("") || p == null)
        {
            pass.setErrorEnabled(true);
            pass.setError("Can't be empty");
            ans = 1;
        }
        if(ans == 0)
        {
            reg.getEditText().setText("");
            pass.getEditText().setText("");
            HashMap<String, String> map = new HashMap<>();

            map.put("username", r);
            map.put("password", p);

            Call<JsonObject> call = retrofitInterface.login(map);

            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.code() == 200) {

                        try {
                            json[0] = new JSONObject(response.body().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String name = null;
                        String key1 = null;
                        try {
                            name = json[0].getString("name");
                            key1 = json[0].getString("accessToken");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        SaveSharedPreference.setApiKey(Login.this,"Bearer "+key1);
                        SaveSharedPreference.setUserName(Login.this,name);

                        Intent i = new Intent(Login.this,MainActivity.class);
                        startActivity(i);
                        finish();

                    } else if (response.code() == 401) {
                        Toast.makeText(Login.this, "Wrong Credentials",
                                Toast.LENGTH_SHORT).show();
                    }
                    else if (response.code() == 404)
                    {
                        Toast.makeText(Login.this, "User does not exists",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(Login.this, t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
