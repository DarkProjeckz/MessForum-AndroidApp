package com.project.messforum;

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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Count extends Fragment implements View.OnClickListener {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL ;
    //private String BASE_URL = "http://192.168.43.17:3000";
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.count_fragment,container,false);
        BASE_URL = getActivity().getResources().getString(R.string.serverIp);
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        MaterialButton end = ( MaterialButton)view.findViewById(R.id.endReg);
        end.setOnClickListener(this);
        viewCount();
        return view;
    }

    private void viewCount() {
        final MaterialTextView breakFast = (MaterialTextView)view.findViewById(R.id.item_breakfast_count);
        final MaterialTextView lunch = (MaterialTextView)view.findViewById(R.id.item_lunch_count);
        final MaterialTextView dinner = (MaterialTextView)view.findViewById(R.id.item_dinner_count);
        breakFast.setTextColor(getResources().getColor(R.color.colorTable));
        lunch.setTextColor(getResources().getColor(R.color.colorTable));
        dinner.setTextColor(getResources().getColor(R.color.colorTable));

        Call<CountResult> call = retrofitInterface.fetchCount(SaveSharedPreference.getApiKey(getActivity()));

        call.enqueue(new Callback<CountResult>() {
            @Override
            public void onResponse(Call<CountResult> call, Response<CountResult> response) {

                if (response.code() == 200) {
                    CountResult countResult = response.body();

                    breakFast.setText(""+countResult.getBreakfast());
                    lunch.setText(""+countResult.getLunch());
                    dinner.setText(""+countResult.getDinner());
                } else if (response.code() == 404) {
                    Toast.makeText(getActivity(),
                            "Something wrong", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<CountResult> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        builder.setTitle("Sure");
        builder.setMessage("Confirm end registration ?");
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                endReg();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(),"Cancelled",Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    private void endReg() {
        Call<Void> call = retrofitInterface.endRegistration(SaveSharedPreference.getApiKey(getActivity()));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.code() == 200) {
                    Toast.makeText(getActivity(),
                            "Registration Ended", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 404) {
                    Toast.makeText(getActivity(),
                            "Something wrong", Toast.LENGTH_SHORT).show();
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
