package com.project.messforumstudent;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class User extends Fragment implements Callback<LoginResult> {

    View view;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL;
    MaterialTextView roll ;
    MaterialTextView name ;
    MaterialTextView gender ;
    MaterialTextView batch ;
    MaterialTextView dept ;
    MaterialTextView room ;
    MaterialCardView cardRoll;
    MaterialCardView cardName;
    MaterialCardView cardGen;
    MaterialCardView cardBatch;
    MaterialCardView cardDept;
    MaterialCardView cardRoom;
    Animation blink;
    //private String BASE_URL = "http://192.168.43.17:3000";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.user_fragment, container, false);
        BASE_URL = getActivity().getResources().getString(R.string.serverIp);
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);
        cardRoll = view.findViewById(R.id.item_title_reg_no);
        cardName = view.findViewById(R.id.item_title_name);
        cardGen = view.findViewById(R.id.item_title_gender);
        cardBatch = view.findViewById(R.id.item_title_year);
        cardDept = view.findViewById(R.id.item_title_dept);
        cardRoom = view.findViewById(R.id.item_title_room_no);

        roll = (MaterialTextView)view.findViewById(R.id.item_reg_no);
        name = (MaterialTextView)view.findViewById(R.id.item_name);
        gender = (MaterialTextView)view.findViewById(R.id.item_gender);
        batch = (MaterialTextView)view.findViewById(R.id.item_year);
        dept = (MaterialTextView)view.findViewById(R.id.item_dept);
        room = (MaterialTextView)view.findViewById(R.id.item_room_no);
        blink = AnimationUtils.loadAnimation(getActivity(),R.anim.anim);
        Call<LoginResult> call = retrofitInterface.userDetails(SaveSharedPreference.getApiKey(getActivity()),SaveSharedPreference.getRoll(getActivity()));
        call.enqueue(this);
        return view;
    }

    @Override
    public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
        if (response.code() == 200) {
            LoginResult result = response.body();



            roll.setText(""+result.getRoll());
            cardRoll.startAnimation(blink);
            name.setText(result.getName());
            cardName.startAnimation(blink);
            gender.setText(result.getGender());
            cardGen.startAnimation(blink);
            dept.setText(result.getDept());
            cardDept.startAnimation(blink);
            room.setText(result.getRoom());
            cardRoom.startAnimation(blink);
            batch.setText(result.getBatch());
            cardBatch.startAnimation(blink);

        } else if (response.code() == 404) {
            Toast.makeText(getActivity(),
                    "No Student Data Found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<LoginResult> call, Throwable t) {
        Toast.makeText(getActivity(), t.getMessage(),
                Toast.LENGTH_LONG).show();
    }
}
