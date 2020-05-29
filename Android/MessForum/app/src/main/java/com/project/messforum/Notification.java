package com.project.messforum;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Notification extends Fragment implements View.OnClickListener {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL;
    MaterialTextView currentText;
    private static final int ID_SUBMIT = 1111;
    private static final int ID_REMOVE = 2222;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.notification_fragment,container,false);
        BASE_URL = getActivity().getResources().getString(R.string.serverIp);
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);
        currentText = (MaterialTextView)view.findViewById(R.id.current_notice);
        MaterialTextView adminName = (MaterialTextView)view.findViewById(R.id.text_admin_name);

        adminName.setText("Welcome "+SaveSharedPreference.getUserName(getActivity()));
        MaterialButton submit = (MaterialButton)view.findViewById(R.id.button_submit);
        submit.setId(ID_SUBMIT);
        MaterialButton remove = (MaterialButton)view.findViewById(R.id.button_remove);
        remove.setId(ID_REMOVE);
        submit.setOnClickListener(this);
        remove.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        if(v.getId()==ID_SUBMIT)
        {
            addNotify();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (Build.VERSION.SDK_INT >= 26) {
                ft.setReorderingAllowed(false);
            }
            ft.detach(this).attach(this).commit();
        }
        else if(v.getId() == ID_REMOVE)
        {
            removeNotify();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (Build.VERSION.SDK_INT >= 26) {
                ft.setReorderingAllowed(false);
            }
            ft.detach(this).attach(this).commit();
        }
    }

    private void addNotify() {

        TextInputLayout notification = view.findViewById(R.id.item_title_add_name);
        notification.setErrorEnabled(false);
        String s = notification.getEditText().getText().toString();
        if(s.isEmpty() || s.length() == 0 || s.equals("") || s == null)
        {
            notification.setErrorEnabled(true);
            notification.setError("CAN'T BE EMPTY");
        }
        else
        {
            notification.getEditText().setText("");
            HashMap<String, String> map = new HashMap<>();

            map.put("admin", SaveSharedPreference.getUserName(getActivity()));
            map.put("notification", s);

            Call<Void> call = retrofitInterface.addNotification(SaveSharedPreference.getApiKey(getActivity()),map);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                    if (response.code() == 200) {
                        Toast.makeText(getActivity(),"Notification Added !!",
                                Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(getActivity(),"Something went wrong",
                                Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void removeNotify() {
        Call<Void> call = retrofitInterface.removeNotification(SaveSharedPreference.getApiKey(getActivity()));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.code() == 200) {
                    Toast.makeText(getActivity(),"Notification Removed !!",
                            Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getActivity(),"Something went wrong",
                            Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}
