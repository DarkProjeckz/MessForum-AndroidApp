package com.project.messforumstudent;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialDialogs;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Token extends Fragment {

    View view;
    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;
    Spinner spinner4;
    Spinner spinner5;
    private RetrofitInterface retrofitInterface;
    private Retrofit retrofit;
    private  String BASE_URL;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.token_fragment,container,false);
        BASE_URL = getActivity().getResources().getString(R.string.serverIp);
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);


        MaterialButton tokReg = view.findViewById(R.id.tokReg);
        tokReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regTokens();
            }
        });
        init();
        return view;
    }

    private void init() {

        spinner1 = (Spinner) view.findViewById(R.id.row_egg_no_of_tokens);
        spinner2 = (Spinner) view.findViewById(R.id.row_chicken_no_of_tokens);
        spinner3 = (Spinner) view.findViewById(R.id.row_mutton_no_of_tokens);
        spinner4 = (Spinner) view.findViewById(R.id.row_gobi_no_of_tokens);
        spinner5 = (Spinner) view.findViewById(R.id.row_mushroom_no_of_tokens);

        // Spinner click listener
        //spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("0");
        categories.add("1");
        categories.add("2");
        categories.add("3");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner1.setAdapter(dataAdapter);
        spinner2.setAdapter(dataAdapter);
        spinner3.setAdapter(dataAdapter);
        spinner4.setAdapter(dataAdapter);
        spinner5.setAdapter(dataAdapter);
    }

    private void regTokens() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        builder.setTitle("Sure");
        builder.setMessage("Submit tokens ?");
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                okayRegTokens();
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

    private void okayRegTokens() {

        String egg1 = spinner1.getSelectedItem().toString();
        String chicken1 = spinner2.getSelectedItem().toString();
        String mutton1 =  spinner3.getSelectedItem().toString();
        String gobi1 = spinner4.getSelectedItem().toString();
        String mushroom1 = spinner5.getSelectedItem().toString();
        if(egg1.equals("0") && chicken1.equals("0") && mutton1.equals("0") && gobi1.equals("0") && mushroom1.equals("0"))
        {
            View contextView = view.findViewById(R.id.item_title_add_student);
            //Snackbar.make(contextView, "No Tokens Selected", Snackbar.LENGTH_SHORT).show();
            Toast.makeText(getActivity(),"No Tokens Selected",Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(egg1.equals("0")) egg1="";
            if(chicken1.equals("0")) chicken1="";
            if(mutton1.equals("0")) mutton1="";
            if(gobi1.equals("0")) gobi1="";
            if(mushroom1.equals("0")) mushroom1="";

            HashMap<String, String> map = new HashMap<>();

            map.put("name", SaveSharedPreference.getUserName(getActivity()));
            map.put("reg", SaveSharedPreference.getRoll(getActivity()));
            map.put("egg",egg1);
            map.put("chicken", chicken1);
            map.put("mutton", mutton1);
            map.put("gobi", gobi1);
            map.put("mushroom",mushroom1);

            Call<JsonObject> call = retrofitInterface.registerToken(SaveSharedPreference.getApiKey(getActivity()),map);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if(response.code()==200) {
                        final JSONObject[] json = new JSONObject[1];
                        try {
                            json[0] = new JSONObject(response.body().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String str = null;
                        try {
                            str = json[0].getString("ans");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(str.isEmpty() || str == null || str.length() == 0 || str.equals(""))
                        {
                            Toast.makeText(getActivity(),"Tokens registered Successfully",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getActivity(),"Token already pending:",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                        Toast.makeText(getActivity(),"Try again later",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
