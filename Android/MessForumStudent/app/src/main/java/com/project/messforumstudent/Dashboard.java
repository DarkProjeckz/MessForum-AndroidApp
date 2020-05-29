package com.project.messforumstudent;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textview.MaterialTextView;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Dashboard extends Fragment {

    String BASE_URL;
    View view;
    private RetrofitInterface retrofitInterface;
    private Retrofit retrofit;
    List<MenuData> menuData = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view =  inflater.inflate(R.layout.dashboard_fragment,container,false);

         BASE_URL = getActivity().getResources().getString(R.string.serverIp);
         retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
         retrofitInterface = retrofit.create(RetrofitInterface.class);
         init();
         return view;
    }
    public void init()
    {

        Call<List<MenuData>> call = retrofitInterface.executeData(SaveSharedPreference.getApiKey(getActivity()));

        call.enqueue(new Callback<List<MenuData>>() {
            @Override
            public void onResponse(Call<List<MenuData>> call, Response<List<MenuData>> response) {

                if (response.code() == 200) {
                    menuData = response.body();
                    print(menuData);



                } else if (response.code() == 404) {
                    Toast.makeText(getActivity(), "Wrong Credentials",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<MenuData>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });



    }

    private void print(List<MenuData> menuData) {

        ////////////////////////TableLayout
        TableLayout breakFast = view.findViewById(R.id.table_break_fast);
        TableLayout lunch = view.findViewById(R.id.table_lunch);
        TableLayout dinner = view.findViewById(R.id.table_dinner);
        for(int i=0;i<7;i++) {
            ////////////////////////TableRow
            TableRow row1 = new TableRow(getActivity());

            TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            rowParams.gravity = Gravity.FILL_HORIZONTAL;
            row1.setLayoutParams(rowParams);

            ///////////////////////Columns Values
            com.google.android.material.textview.MaterialTextView tv1 = new com.google.android.material.textview.MaterialTextView(getActivity());
            com.google.android.material.textview.MaterialTextView tv2 = new com.google.android.material.textview.MaterialTextView(getActivity());
            com.google.android.material.textview.MaterialTextView tv3 = new com.google.android.material.textview.MaterialTextView(getActivity());
            ////////////////////////Setting data to columns
            MenuData data = menuData.get(i);
            tv1.setText(""+data.getSno());
            tv2.setText(data.getDay());
            tv3.setText(data.getFood());

            tv1.setBackgroundResource(R.drawable.cellshape);
            tv2.setBackgroundResource(R.drawable.cellshape);
            tv3.setBackgroundResource(R.drawable.cellshape);

            tv1.setTextColor(getResources().getColor(R.color.colorTable));
            tv2.setTextColor(getResources().getColor(R.color.colorTable));
            tv3.setTextColor(getResources().getColor(R.color.colorTable));


            tv1.setTextSize(25);
            tv2.setTextSize(25);
            tv3.setTextSize(25);

            float scale = getResources().getDisplayMetrics().density;
            int dpAsPixels = (int) (12*scale + 0.5f);

            tv1.setPadding(dpAsPixels,dpAsPixels,dpAsPixels,dpAsPixels);
            tv2.setPadding(dpAsPixels,dpAsPixels,dpAsPixels,dpAsPixels);
            tv3.setPadding(dpAsPixels,dpAsPixels,dpAsPixels,dpAsPixels);

            row1.addView(tv1);
            row1.addView(tv2);
            row1.addView(tv3);

            breakFast.addView(row1);
        }
        for(int i=0;i<7;i++) {
            ////////////////////////TableRow
            TableRow row1 = new TableRow(getActivity());

            TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            rowParams.gravity = Gravity.FILL_HORIZONTAL;
            row1.setLayoutParams(rowParams);
            ///////////////////////Columns Values
            androidx.appcompat.widget.AppCompatTextView tv1 = new androidx.appcompat.widget.AppCompatTextView(getActivity());
            androidx.appcompat.widget.AppCompatTextView tv2 = new androidx.appcompat.widget.AppCompatTextView(getActivity());
            androidx.appcompat.widget.AppCompatTextView tv3 = new androidx.appcompat.widget.AppCompatTextView(getActivity());
            ////////////////////////Setting data to columns
            MenuData data = menuData.get(i+7);
            tv1.setText(""+data.getSno());
            tv2.setText(data.getDay());
            tv3.setText(data.getFood());

            tv1.setBackgroundResource(R.drawable.cellshape);
            tv2.setBackgroundResource(R.drawable.cellshape);
            tv3.setBackgroundResource(R.drawable.cellshape);

            tv1.setTextColor(getResources().getColor(R.color.colorTable));
            tv2.setTextColor(getResources().getColor(R.color.colorTable));
            tv3.setTextColor(getResources().getColor(R.color.colorTable));

            tv1.setTextSize(25);
            tv2.setTextSize(25);
            tv3.setTextSize(25);

            float scale = getResources().getDisplayMetrics().density;
            int dpAsPixels = (int) (12*scale + 0.5f);

            tv1.setPadding(dpAsPixels,dpAsPixels,dpAsPixels,dpAsPixels);
            tv2.setPadding(dpAsPixels,dpAsPixels,dpAsPixels,dpAsPixels);
            tv3.setPadding(dpAsPixels,dpAsPixels,dpAsPixels,dpAsPixels);

            row1.addView(tv1);
            row1.addView(tv2);
            row1.addView(tv3);

            lunch.addView(row1);
        }
        for(int i=0;i<7;i++) {
            ////////////////////////TableRow
            TableRow row1 = new TableRow(getActivity());
            TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            rowParams.gravity = Gravity.FILL_HORIZONTAL;
            row1.setLayoutParams(rowParams);

            ///////////////////////Columns Values
            androidx.appcompat.widget.AppCompatTextView tv1 = new androidx.appcompat.widget.AppCompatTextView(getActivity());
            androidx.appcompat.widget.AppCompatTextView tv2 = new androidx.appcompat.widget.AppCompatTextView(getActivity());
            androidx.appcompat.widget.AppCompatTextView tv3 = new androidx.appcompat.widget.AppCompatTextView(getActivity());
            ////////////////////////Setting data to columns
            MenuData data = menuData.get(i+14);
            tv1.setText(""+data.getSno());
            tv2.setText(data.getDay());
            tv3.setText(data.getFood());

            tv1.setBackgroundResource(R.drawable.cellshape);
            tv2.setBackgroundResource(R.drawable.cellshape);
            tv3.setBackgroundResource(R.drawable.cellshape);

            tv1.setTextColor(getResources().getColor(R.color.colorTable));
            tv2.setTextColor(getResources().getColor(R.color.colorTable));
            tv3.setTextColor(getResources().getColor(R.color.colorTable));

            tv1.setTextSize(25);
            tv2.setTextSize(25);
            tv3.setTextSize(25);

            float scale = getResources().getDisplayMetrics().density;
            int dpAsPixels = (int) (12*scale + 0.5f);

            tv1.setPadding(dpAsPixels,dpAsPixels,dpAsPixels,dpAsPixels);
            tv2.setPadding(dpAsPixels,dpAsPixels,dpAsPixels,dpAsPixels);
            tv3.setPadding(dpAsPixels,dpAsPixels,dpAsPixels,dpAsPixels);

            row1.addView(tv1);
            row1.addView(tv2);
            row1.addView(tv3);

            dinner.addView(row1);
        }
    }

}
