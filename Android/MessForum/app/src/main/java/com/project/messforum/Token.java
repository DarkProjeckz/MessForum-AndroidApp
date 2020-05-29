package com.project.messforum;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Token extends Fragment implements View.OnClickListener {

    View view;
    private static final int ID_DISPLAY = 3333;
    private static final int ID_SEARCH = 4444;
    private TableLayout display1,display2;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL;
    List<TokenDetails> td = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.token_fragment,container,false);

        MaterialButton display = ( MaterialButton)view.findViewById(R.id.display);
        display.setId(ID_DISPLAY);
        MaterialButton search = ( MaterialButton) view.findViewById(R.id.search);
        search.setId(ID_SEARCH);
        display.setOnClickListener(this);
        search.setOnClickListener(this);

        display1 = view.findViewById(R.id.table_search_tokens);
        display2 = view.findViewById(R.id.table_register_tokens);

        BASE_URL = getActivity().getResources().getString(R.string.serverIp);
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);
        return view;
    }

    @Override
    public void onClick(View v) {


        if(v.getId() == ID_DISPLAY)
        {
            display2.removeAllViews();
            init();
        }
        else if(v.getId() == ID_SEARCH)
        {

            display1.removeAllViews();
            search();
        }
        else if(v.getId() != ID_DISPLAY && v.getId() != ID_SEARCH)
        {
            deltoken(v.getId());
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (Build.VERSION.SDK_INT >= 26) {
                ft.setReorderingAllowed(false);
            }
            ft.detach(this).attach(this).commit();

        }
    }

    public  void search()
    {
        TextInputLayout searchl = view.findViewById(R.id.item_title_reg_no);
        searchl.setErrorEnabled(false);
        String s = searchl.getEditText().getText().toString();
        if(s.isEmpty() || s.length() == 0 || s.equals("") || s == null)
        {
            searchl.setErrorEnabled(true);
            searchl.setError("CAN'T BE EMPTY");
        }
        else
        {
            searchl.getEditText().setText("");
            Call<List<TokenDetails>> call = retrofitInterface.executetokensearch(SaveSharedPreference.getApiKey(getActivity()),s);

            call.enqueue(new Callback<List<TokenDetails>>() {
                @Override
                public void onResponse(Call<List<TokenDetails>> call, Response<List<TokenDetails>> response) {

                    if (response.code() == 200) {
                        td = response.body();

                        ////////////////////////Table Heading Row
                        TableRow row0 = new TableRow(getActivity());

                        //////////////////////Layout Params for all rows
                        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        rowParams.gravity = Gravity.FILL_HORIZONTAL;

                        /////////////////////Text View for heading row
                        MaterialTextView tv01 = new MaterialTextView(getActivity());
                        MaterialTextView tv02 = new MaterialTextView(getActivity());
                        MaterialTextView tv03 = new MaterialTextView(getActivity());
                        MaterialTextView tv04 = new MaterialTextView(getActivity());
                        MaterialTextView tv05 = new MaterialTextView(getActivity());
                        MaterialTextView tv06 = new MaterialTextView(getActivity());

                        ////////////////////Padding Values
                        float scale = getResources().getDisplayMetrics().density;
                        int dpAsPixels1 = (int) (10 * scale + 0.5f);
                        int dpAsPixels2 = (int) (8 * scale + 0.5f);
                        int dpAsPixels3 = (int) (15 * scale + 0.5f);
                        int dpAsPixels4 = (int) (16 * scale + 0.5f);
                        int dpAsPixels5 = (int) (8.5 * scale + 0.5f);

                        ///////////////////Text View Values
                        tv01.setText("ID");
                        tv02.setText("Name");
                        tv03.setText("Token Name");
                        tv04.setText("Token Count");
                        tv05.setText("Token Number");
                        tv06.setText("Approve");

                        tv01.setTypeface(tv01.getTypeface(), Typeface.BOLD);
                        tv02.setTypeface(tv02.getTypeface(), Typeface.BOLD);
                        tv03.setTypeface(tv03.getTypeface(), Typeface.BOLD);
                        tv04.setTypeface(tv04.getTypeface(), Typeface.BOLD);
                        tv05.setTypeface(tv05.getTypeface(), Typeface.BOLD);
                        tv06.setTypeface(tv06.getTypeface(), Typeface.BOLD);

                        tv01.setBackgroundResource(R.drawable.cellshape);
                        tv02.setBackgroundResource(R.drawable.cellshape);
                        tv03.setBackgroundResource(R.drawable.cellshape);
                        tv04.setBackgroundResource(R.drawable.cellshape);
                        tv05.setBackgroundResource(R.drawable.cellshape);
                        tv06.setBackgroundResource(R.drawable.cellshape);

                        tv01.setTextColor(getResources().getColor(R.color.colorTable));
                        tv02.setTextColor(getResources().getColor(R.color.colorTable));
                        tv03.setTextColor(getResources().getColor(R.color.colorTable));
                        tv04.setTextColor(getResources().getColor(R.color.colorTable));
                        tv05.setTextColor(getResources().getColor(R.color.colorTable));
                        tv06.setTextColor(getResources().getColor(R.color.colorTable));

                        tv01.setTextSize(25);
                        tv02.setTextSize(25);
                        tv03.setTextSize(25);
                        tv04.setTextSize(25);
                        tv05.setTextSize(25);
                        tv06.setTextSize(25);

                        tv01.setPadding(dpAsPixels1, dpAsPixels4, dpAsPixels1, dpAsPixels3);
                        tv02.setPadding(dpAsPixels1, dpAsPixels4, dpAsPixels1, dpAsPixels3);
                        tv03.setPadding(dpAsPixels1, dpAsPixels4, dpAsPixels1, dpAsPixels3);
                        tv04.setPadding(dpAsPixels1, dpAsPixels4, dpAsPixels1, dpAsPixels3);
                        tv05.setPadding(dpAsPixels1, dpAsPixels4, dpAsPixels1, dpAsPixels3);
                        tv06.setPadding(dpAsPixels1, dpAsPixels4, dpAsPixels1, dpAsPixels3);

                        //////////////////Adding values to Row
                        row0.addView(tv01);
                        row0.addView(tv02);
                        row0.addView(tv03);
                        row0.addView(tv04);
                        row0.addView(tv05);
                        row0.addView(tv06);

                        display1.addView(row0);

                        ////////////////////////TableRow For Search ////////////////////////////////////////
                        for(TokenDetails td1 : td)
                        {
                            TableRow row01 = new TableRow(getActivity());

                            row01.setLayoutParams(rowParams);

                            ////////////////////Remove Button
                            MaterialButton b01 = new MaterialButton(getActivity());

                            ///////////////////////Columns Values
                            MaterialTextView tvs1 = new MaterialTextView(getActivity());
                            MaterialTextView tvs2 = new MaterialTextView(getActivity());
                            MaterialTextView tvs3 = new MaterialTextView(getActivity());
                            MaterialTextView tvs4 = new MaterialTextView(getActivity());
                            MaterialTextView tvs5 = new MaterialTextView(getActivity());

                            //////////////////////Linear Layout for Remove Button
                            LinearLayout sdone_button = new LinearLayout(getActivity());

                            ////////////////Linear Layout values
                            sdone_button.setPadding(dpAsPixels1, dpAsPixels2, dpAsPixels1, dpAsPixels5);
                            sdone_button.setBackgroundResource(R.drawable.cellshape);

                            ////////////////////////Remove Button Values
                            b01.setGravity(Gravity.CENTER_HORIZONTAL);
                            b01.setTextColor(Color.parseColor("#000000"));
                            b01.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorTitle)));
                            b01.setTypeface(b01.getTypeface(), Typeface.BOLD);
                            b01.setText("DONE");
                            b01.setId(td1.getId());
                            b01.setTextColor(getResources().getColor(R.color.colorTextTitle));
                            b01.setOnClickListener(Token.this);

                            sdone_button.addView(b01);

                            ////////////////////////Setting data to columns ( Text Views )
                            tvs1.setText(td1.getRegno()+"");
                            tvs2.setText(td1.getName());
                            tvs3.setText(td1.getTname());
                            tvs4.setText(td1.getTnum()+"");
                            tvs5.setText(td1.getToknum()+"");

                            tvs1.setBackgroundResource(R.drawable.cellshape);
                            tvs2.setBackgroundResource(R.drawable.cellshape);
                            tvs3.setBackgroundResource(R.drawable.cellshape);
                            tvs4.setBackgroundResource(R.drawable.cellshape);
                            tvs5.setBackgroundResource(R.drawable.cellshape);

                            tvs1.setTextSize(25);
                            tvs2.setTextSize(25);
                            tvs3.setTextSize(25);
                            tvs4.setTextSize(25);
                            tvs5.setTextSize(25);

                            tvs1.setTextColor(getResources().getColor(R.color.colorTable));
                            tvs2.setTextColor(getResources().getColor(R.color.colorTable));
                            tvs3.setTextColor(getResources().getColor(R.color.colorTable));
                            tvs4.setTextColor(getResources().getColor(R.color.colorTable));
                            tvs5.setTextColor(getResources().getColor(R.color.colorTable));

                            tvs1.setPadding(dpAsPixels1, dpAsPixels4, dpAsPixels1, dpAsPixels3);
                            tvs2.setPadding(dpAsPixels1, dpAsPixels4, dpAsPixels1, dpAsPixels3);
                            tvs3.setPadding(dpAsPixels1, dpAsPixels4, dpAsPixels1, dpAsPixels3);
                            tvs4.setPadding(dpAsPixels1, dpAsPixels4, dpAsPixels1, dpAsPixels3);
                            tvs5.setPadding(dpAsPixels1, dpAsPixels4, dpAsPixels1, dpAsPixels3);

                            //////////////////Adding values to Row
                            row01.addView(tvs1);
                            row01.addView(tvs2);
                            row01.addView(tvs3);
                            row01.addView(tvs4);
                            row01.addView(tvs5);

                            row01.addView(sdone_button);

                            /////////////////Adding Row to Table Layout
                            display1.addView(row01);
                        }

                    } else if (response.code() == 404) {
                        Toast.makeText(getActivity(), "No Token Found",
                                Toast.LENGTH_SHORT).show();
                    }
                    else if (response.code() == 401) {
                        Toast.makeText(getActivity(), "Empty",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<TokenDetails>> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public void init() {

        Call<List<TokenDetails>> call = retrofitInterface.executetokendetails(SaveSharedPreference.getApiKey(getActivity()));

        call.enqueue(new Callback<List<TokenDetails>>() {
            @Override
            public void onResponse(Call<List<TokenDetails>> call, Response<List<TokenDetails>> response) {

                if (response.code() == 200) {
                    td = response.body();

                    float scale = getResources().getDisplayMetrics().density;
                    int dpAsPixels1 = (int) (10 * scale + 0.5f);
                    int dpAsPixels2 = (int) (8 * scale + 0.5f);
                    int dpAsPixels3 = (int) (15 * scale + 0.5f);
                    int dpAsPixels4 = (int) (16 * scale + 0.5f);
                    int dpAsPixels5 = (int) (8.5 * scale + 0.5f);

                    TableRow row = new TableRow(getActivity());

                    /////////////////////Text View for heading row
                    MaterialTextView tv1 = new MaterialTextView(getActivity());
                    MaterialTextView tv2 = new MaterialTextView(getActivity());
                    MaterialTextView tv3 = new MaterialTextView(getActivity());
                    MaterialTextView tv4 = new MaterialTextView(getActivity());
                    MaterialTextView tv5 = new MaterialTextView(getActivity());
                    MaterialTextView tv6 = new MaterialTextView(getActivity());

                    ///////////////////Text View Values
                    tv1.setText("ID");
                    tv2.setText("Name");
                    tv3.setText("Token Name");
                    tv4.setText("Token Count");
                    tv5.setText("Token Number");
                    tv6.setText("Approve");

                    TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    rowParams.gravity = Gravity.FILL_HORIZONTAL;

                    tv1.setTypeface(tv1.getTypeface(), Typeface.BOLD);
                    tv2.setTypeface(tv2.getTypeface(), Typeface.BOLD);
                    tv3.setTypeface(tv3.getTypeface(), Typeface.BOLD);
                    tv4.setTypeface(tv4.getTypeface(), Typeface.BOLD);
                    tv5.setTypeface(tv5.getTypeface(), Typeface.BOLD);
                    tv6.setTypeface(tv6.getTypeface(), Typeface.BOLD);

                    tv1.setBackgroundResource(R.drawable.cellshape);
                    tv2.setBackgroundResource(R.drawable.cellshape);
                    tv3.setBackgroundResource(R.drawable.cellshape);
                    tv4.setBackgroundResource(R.drawable.cellshape);
                    tv5.setBackgroundResource(R.drawable.cellshape);
                    tv6.setBackgroundResource(R.drawable.cellshape);

                    tv1.setTextColor(getResources().getColor(R.color.colorTable));
                    tv2.setTextColor(getResources().getColor(R.color.colorTable));
                    tv3.setTextColor(getResources().getColor(R.color.colorTable));
                    tv4.setTextColor(getResources().getColor(R.color.colorTable));
                    tv5.setTextColor(getResources().getColor(R.color.colorTable));
                    tv6.setTextColor(getResources().getColor(R.color.colorTable));

                    tv1.setTextSize(25);
                    tv2.setTextSize(25);
                    tv3.setTextSize(25);
                    tv4.setTextSize(25);
                    tv5.setTextSize(25);
                    tv6.setTextSize(25);

                    tv1.setPadding(dpAsPixels1, dpAsPixels4, dpAsPixels1, dpAsPixels3);
                    tv2.setPadding(dpAsPixels1, dpAsPixels4, dpAsPixels1, dpAsPixels3);
                    tv3.setPadding(dpAsPixels1, dpAsPixels4, dpAsPixels1, dpAsPixels3);
                    tv4.setPadding(dpAsPixels1, dpAsPixels4, dpAsPixels1, dpAsPixels3);
                    tv5.setPadding(dpAsPixels1, dpAsPixels4, dpAsPixels1, dpAsPixels3);
                    tv6.setPadding(dpAsPixels1, dpAsPixels4, dpAsPixels1, dpAsPixels3);

                    //////////////////Adding values to Row
                    row.addView(tv1);
                    row.addView(tv2);
                    row.addView(tv3);
                    row.addView(tv4);
                    row.addView(tv5);
                    row.addView(tv6);

                    display2.addView(row);

                    for(TokenDetails td2 : td) {
                        ////////////////////////TableRow
                        TableRow row1 = new TableRow(getActivity());

                        row1.setLayoutParams(rowParams);

                        ////////////////////Remove Button
                        MaterialButton b1 = new MaterialButton(getActivity());

                        ///////////////////////Columns Values
                        MaterialTextView tvr1 = new MaterialTextView(getActivity());
                        MaterialTextView tvr2 = new MaterialTextView(getActivity());
                        MaterialTextView tvr3 = new MaterialTextView(getActivity());
                        MaterialTextView tvr4 = new MaterialTextView(getActivity());
                        MaterialTextView tvr5 = new MaterialTextView(getActivity());

                        //////////////////////Linear Layout for Remove Button
                        LinearLayout rdone_button = new LinearLayout(getActivity());

                        ////////////////Linear Layout values
                        rdone_button.setPadding(dpAsPixels1, dpAsPixels2, dpAsPixels1, dpAsPixels5);
                        rdone_button.setBackgroundResource(R.drawable.cellshape);

                        ////////////////////////Remove Button Values
                        b1.setGravity(Gravity.CENTER_HORIZONTAL);
                        b1.setTextColor(Color.parseColor("#000000"));
                        b1.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorTitle)));
                        b1.setTypeface(b1.getTypeface(), Typeface.BOLD);
                        b1.setText("DONE");
                        b1.setId(td2.getId());
                        b1.setTextColor(getResources().getColor(R.color.colorTextTitle));
                        b1.setOnClickListener(Token.this);

                        rdone_button.addView(b1);

                        ////////////////////////Setting data to columns ( Text Views )
                        tvr1.setText(td2.getRegno()+"");
                        tvr2.setText(td2.getName()+"");
                        tvr3.setText(td2.getTname()+"");
                        tvr4.setText(td2.getTnum()+"");
                        tvr5.setText(td2.getToknum()+"");

                        tvr1.setBackgroundResource(R.drawable.cellshape);
                        tvr2.setBackgroundResource(R.drawable.cellshape);
                        tvr3.setBackgroundResource(R.drawable.cellshape);
                        tvr4.setBackgroundResource(R.drawable.cellshape);
                        tvr5.setBackgroundResource(R.drawable.cellshape);

                        tvr1.setTextSize(25);
                        tvr2.setTextSize(25);
                        tvr3.setTextSize(25);
                        tvr4.setTextSize(25);
                        tvr5.setTextSize(25);

                        tvr1.setTextColor(getResources().getColor(R.color.colorTable));
                        tvr2.setTextColor(getResources().getColor(R.color.colorTable));
                        tvr3.setTextColor(getResources().getColor(R.color.colorTable));
                        tvr4.setTextColor(getResources().getColor(R.color.colorTable));
                        tvr5.setTextColor(getResources().getColor(R.color.colorTable));


                        tvr1.setPadding(dpAsPixels1, dpAsPixels4, dpAsPixels1, dpAsPixels3);
                        tvr2.setPadding(dpAsPixels1, dpAsPixels4, dpAsPixels1, dpAsPixels3);
                        tvr3.setPadding(dpAsPixels1, dpAsPixels4, dpAsPixels1, dpAsPixels3);
                        tvr4.setPadding(dpAsPixels1, dpAsPixels4, dpAsPixels1, dpAsPixels3);
                        tvr5.setPadding(dpAsPixels1, dpAsPixels4, dpAsPixels1, dpAsPixels3);

                        //////////////////Adding values to Row
                        row1.addView(tvr1);
                        row1.addView(tvr2);
                        row1.addView(tvr3);
                        row1.addView(tvr4);
                        row1.addView(tvr5);

                        row1.addView(rdone_button);

                        /////////////////Adding Row to Table Layout
                        display2.addView(row1);


                    } }else if (response.code() == 404) {
                    Toast.makeText(getActivity(), "SOMETHING WENT WRONG",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TokenDetails>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    public  void deltoken(long id)
    {
        Call<Void> call = retrofitInterface.executetokendelete(SaveSharedPreference.getApiKey(getActivity()),id+"");

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.code() == 200) {
                    Toast.makeText(getActivity(), "DONE",
                            Toast.LENGTH_SHORT).show();

                } else if (response.code() == 404) {
                    Toast.makeText(getActivity(), "Wrong Credentials",
                            Toast.LENGTH_SHORT).show();
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
