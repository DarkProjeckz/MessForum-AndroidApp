package com.project.messforumstudent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

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
        reg.setErrorEnabled(false);
        pass.setErrorEnabled(false);

        String reg1 = reg.getEditText().getText().toString();
        String pass1 = pass.getEditText().getText().toString();

        int flag = 0;

        if (reg1.isEmpty() || reg1.length() == 0 || reg1.equals("") || reg1 == null)
        {
            reg.setErrorEnabled(true);
            reg.setError("Can't Be Empty");
            flag = 1;
        }
        if (pass1.isEmpty() || pass1.length() == 0 || pass1.equals("") || pass1 == null)
        {
            pass.setErrorEnabled(true);
            pass.setError("Can't Be Empty");
            flag = 1;
        }

        if (flag == 0)
        {
            reg.getEditText().setText("");
            pass.getEditText().setText("");
            HashMap<String, String> map = new HashMap<>();

            map.put("reg", reg1);
            map.put("password", pass1);

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
                        Long rollno = null;
                        String key1 = null;
                        try {
                            name = json[0].getString("name");
                            rollno = json[0].getLong("rollno");
                            key1 = json[0].getString("accessToken");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        SaveSharedPreference.setApiKey(Login.this,"Bearer "+key1);
                        SaveSharedPreference.setUserName(Login.this,name);
                        SaveSharedPreference.setRoll(Login.this,rollno);

                        Intent i = new Intent(Login.this,MainActivity.class);
                        startActivity(i);
                        finish();

                    } else if (response.code() == 404) {
                        Toast.makeText(Login.this, "Wrong Credentials",
                                Toast.LENGTH_LONG).show();
                    }
                    else if (response.code() == 405)
                    {
                        Toast.makeText(Login.this, "User does not exists",
                                Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(Login.this, t.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });
        }


    }
}
