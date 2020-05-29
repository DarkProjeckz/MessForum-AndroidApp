package com.project.messforumstudent;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Count extends Fragment {

    View view;
    private RetrofitInterface retrofitInterface;
    private Retrofit retrofit;
    private String BASE_URL;
    MaterialCheckBox breakfast,lunch,dinner;
    int b,l,d;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.count_fragment,container,false);

        BASE_URL = getActivity().getResources().getString(R.string.serverIp);
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        breakfast = view.findViewById(R.id.checkbox_break_fast);
        lunch = view.findViewById(R.id.checkbox_lunch);
        dinner = view.findViewById(R.id.checkbox_dinner);
        MaterialButton count_submit = (MaterialButton)view.findViewById(R.id.count_submit);

        b=1;l=1;d=1;
        count_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countOkay();
            }
        });
        return  view;
    }

    private void countOkay() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        builder.setTitle("Sure");
        builder.setMessage("Sure with the count ?");
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(breakfast.isChecked()) b=0;
                if(lunch.isChecked()) l=0;
                if(dinner.isChecked()) d=0;
                regCount(SaveSharedPreference.getRoll(getActivity()),b,l,d);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(),"Not registered",Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    private void regCount(String roll, int br, int lu, int di) {

        HashMap<String, String> map = new HashMap<>();

        map.put("roll", roll);
        map.put("breakfast", ""+br);
        map.put("lunch", ""+lu);
        map.put("dinner", ""+di);

        Call<Void> call = retrofitInterface.registerCount(SaveSharedPreference.getApiKey(getActivity()),map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.code() == 200) {
                    Toast.makeText(getActivity(),"Count added",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getActivity(),"Count Already Added",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}