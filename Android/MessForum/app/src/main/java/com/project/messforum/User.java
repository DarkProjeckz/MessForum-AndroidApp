package com.project.messforum;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class User extends Fragment implements View.OnClickListener, TextWatcher {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL;
    //private String BASE_URL = "http://192.168.43.17:3000";
    private static final int ID_DISPLAY = 2353;
    private static final int ID_ADD = 2351;
    private static final int ID_SEARCH = 1212;
    private TableLayout display;
    MaterialCardView search;
    TextInputLayout reg;
    TextInputLayout name;
    TextInputLayout dept;
    TextInputLayout batch;
    TextInputLayout gender;
    TextInputLayout room;
    TextInputLayout password;
    TextInputEditText regt,namet,deptt,batcht,gendert,roomt,passwordt;
    //ScrollView scrollView;
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_fragment, container, false);
        BASE_URL = getActivity().getResources().getString(R.string.serverIp);
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        search = (MaterialCardView)view.findViewById(R.id.card_search_details);
        display = view.findViewById(R.id.table_display);
        //display.requestFocus();
      //  scrollView = view.findViewById(R.id.scrollView1);
        MaterialButton display = ( MaterialButton)view.findViewById(R.id.disAll);
        display.setId(ID_DISPLAY);
        MaterialButton addStudent = ( MaterialButton)view.findViewById(R.id.addStudent);
        addStudent.setId(ID_ADD);
        MaterialButton search = ( MaterialButton)view.findViewById(R.id.searchStudent);
        search.setId(ID_SEARCH);

        display.setOnClickListener(this);
        addStudent.setOnClickListener(this);
        search.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId() == ID_DISPLAY)
        {
            display.removeAllViews();
            init();
        }
        if(v.getId() == ID_ADD)
        {
            addUser();
        }
        if(v.getId() == ID_SEARCH)
        {
            search.removeAllViews();
            searchUser();
        }
        if(v.getId() != ID_DISPLAY && v.getId() != ID_ADD && v.getId() != ID_SEARCH)
        {
            deluser(v.getId());

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (Build.VERSION.SDK_INT >= 26) {
                ft.setReorderingAllowed(false);
            }
            ft.detach(this).attach(this).commit();
            init();
        }
    }

    private void searchUser() {
        TextInputLayout regNo = view.findViewById(R.id.searchReg);
        String regin = regNo.getEditText().getText().toString();
        regNo.setErrorEnabled(false);
        int flag = 0;

        if(regin.isEmpty() || regin.length() == 0 || regin.equals("") || regin == null)
        {
            regNo.setErrorEnabled(true);
            regNo.setError("CAN'T BE EMPTY");
            flag = 1;
        }
        if(flag == 0)
        {
            regNo.getEditText().setText("");
            Call<LoginResult> call = retrofitInterface.userDetails(SaveSharedPreference.getApiKey(getActivity()),regin);
            call.enqueue(new Callback<LoginResult>() {
                @Override
                public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {

                    if (response.code() == 200) {
                        LoginResult result = response.body();
                        displaySearch(result);

                    } else if (response.code() == 404) {
                        Toast.makeText(getActivity(),
                                "No Student Data Found", Toast.LENGTH_SHORT).show();
                    }

                }
                @Override
                public void onFailure(Call<LoginResult> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void displaySearch(LoginResult result) {

        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels4 = (int) (16*scale + 0.5f);
        int dpAsPixels6 = (int) (30*scale + 0.5f);
        int dpAsPixels7 = (int) (40*scale + 0.5f);
        int dpAsPixels8 = (int) (80*scale + 0.5f);
        int dpAsPixels9 = (int) (120*scale + 0.5f);
        int dpAsPixels10 = (int) (160*scale + 0.5f);
        int dpAsPixels11 = (int) (240*scale + 0.5f);
        int dpAsPixels12 = (int) (140*scale + 0.5f);

        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params1.gravity = Gravity.CENTER_HORIZONTAL;
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params2.gravity = Gravity.CENTER_HORIZONTAL;
        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params3.gravity = Gravity.CENTER_HORIZONTAL;
        LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params4.gravity = Gravity.CENTER_HORIZONTAL;
        LinearLayout.LayoutParams params5 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params5.gravity = Gravity.CENTER_HORIZONTAL;
        LinearLayout.LayoutParams params6 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params6.gravity = Gravity.CENTER_HORIZONTAL;

        params1.setMargins(0,0,0,0);
        params2.setMargins(0,dpAsPixels7,0,0);
        params3.setMargins(0,dpAsPixels8,0,0);
        params4.setMargins(0,dpAsPixels9,0,0);
        params5.setMargins(0,dpAsPixels10,0,0);
        params6.setMargins(dpAsPixels8 ,dpAsPixels11,dpAsPixels8,dpAsPixels4);


        MaterialButton b = new MaterialButton(getActivity());

        MaterialTextView sreg = new MaterialTextView(getActivity());
        MaterialTextView sname = new MaterialTextView(getActivity());
        MaterialTextView syear = new MaterialTextView(getActivity());
        MaterialTextView sdept = new MaterialTextView(getActivity());
        MaterialTextView sroom_no = new MaterialTextView(getActivity());

        /////////////////Text View Values
        sreg.setText(""+result.getRoll());
        sname.setText(result.getName());
        syear.setText(result.getBatch());
        sdept.setText(result.getDept());
        sroom_no.setText(result.getRoom());

        sreg.setTextSize(25);
        sname.setTextSize(25);
        syear.setTextSize(25);
        sdept.setTextSize(25);
        sroom_no.setTextSize(25);

        sreg.setTextColor(getResources().getColor(R.color.colorTable));
        sname.setTextColor(getResources().getColor(R.color.colorTable));
        syear.setTextColor(getResources().getColor(R.color.colorTable));
        sdept.setTextColor(getResources().getColor(R.color.colorTable));
        sroom_no.setTextColor(getResources().getColor(R.color.colorTable));

        sreg.setPadding(dpAsPixels6, dpAsPixels6, dpAsPixels6, dpAsPixels6);
        sname.setPadding(dpAsPixels6, dpAsPixels6, dpAsPixels6, dpAsPixels6);
        syear.setPadding(dpAsPixels6, dpAsPixels6, dpAsPixels6, dpAsPixels6);
        sdept.setPadding(dpAsPixels6, dpAsPixels6, dpAsPixels6, dpAsPixels6);
        sroom_no.setPadding(dpAsPixels6, dpAsPixels6, dpAsPixels6, dpAsPixels6);

        sreg.setLayoutParams(params1);
        sreg.setGravity(Gravity.CENTER_HORIZONTAL);
        sname.setGravity(Gravity.CENTER_HORIZONTAL);
        syear.setGravity(Gravity.CENTER_HORIZONTAL);
        sdept.setGravity(Gravity.CENTER_HORIZONTAL);
        sroom_no.setGravity(Gravity.CENTER_HORIZONTAL);
        b.setGravity(Gravity.CENTER_HORIZONTAL);

        sname.setLayoutParams(params2);
        syear.setLayoutParams(params3);
        sdept.setLayoutParams(params4);
        sroom_no.setLayoutParams(params5);
        b.setLayoutParams(params6);

        ////////////////////////Remove Button Values
        b.setGravity(Gravity.CENTER_HORIZONTAL);
        b.setTextColor(Color.parseColor("#000000"));
        b.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorTitle)));
        b.setTypeface(b.getTypeface(), Typeface.BOLD);
        b.setId(result.getIdd());
        b.setText("REMOVE");
        b.setOnClickListener(User.this);
        b.setTextColor(getResources().getColor(R.color.colorTextTitle));

        search.addView(sreg);
        search.addView(sname);
        search.addView(syear);
        search.addView(sdept);
        search.addView(sroom_no);
        search.addView(b);
    }

    private void addUser() {
        reg = view.findViewById(R.id.add_reg);
        name = view.findViewById(R.id.add_name);
        dept = view.findViewById(R.id.add_dept);
        batch = view.findViewById(R.id.add_year);
        gender = view.findViewById(R.id.add_gender);
        room = view.findViewById(R.id.add_room);
        password = view.findViewById(R.id.add_password);


        regt = view.findViewById(R.id.reg);
        namet = view.findViewById(R.id.name);
        gendert = view.findViewById(R.id.gen);
        batcht = view.findViewById(R.id.batch);
        deptt = view.findViewById(R.id.dept);
        roomt = view.findViewById(R.id.room);
        passwordt = view.findViewById(R.id.passw);

        regt.addTextChangedListener(this);
        namet.addTextChangedListener(this);
        gendert.addTextChangedListener(this);
        batcht.addTextChangedListener(this);
        deptt.addTextChangedListener(this);
        roomt.addTextChangedListener(this);
        passwordt.addTextChangedListener(this);

        reg.setErrorEnabled(false);
        name.setErrorEnabled(false);
        dept.setErrorEnabled(false);
        batch.setErrorEnabled(false);
        gender.setErrorEnabled(false);
        room.setErrorEnabled(false);
        password.setErrorEnabled(false);

        String name1 = name.getEditText().getText().toString();
        String reg1 = reg.getEditText().getText().toString();
        String pass1 = password.getEditText().getText().toString();
        String gen1 = gender.getEditText().getText().toString();
        String room1 = room.getEditText().getText().toString();
        String batch1= batch.getEditText().getText().toString();
        String dept1 = dept.getEditText().getText().toString();

        int flag = 0;

        if(reg1.isEmpty() || reg1.length() == 0 || reg1.equals("") || reg1 == null)
        {
            reg.setErrorEnabled(true);
            reg.setError("CAN'T BE EMPTY");
            flag = 1;
        }
        if(name1.isEmpty() || name1.length() == 0 || name1.equals("") || name1 == null)
        {
            name.setErrorEnabled(true);
            name.setError("CAN'T BE EMPTY");
            flag = 1;
        }
        if(pass1.isEmpty() || pass1.length() == 0 || pass1.equals("") || pass1 == null)
        {
            password.setErrorEnabled(true);
            password.setError("CAN'T BE EMPTY");
            flag = 1;
        }
        if(gen1.isEmpty() || gen1.length() == 0 || gen1.equals("") || gen1 == null)
        {
            gender.setErrorEnabled(true);
            gender.setError("CAN'T BE EMPTY");
            flag = 1;
        }
        if(room1.isEmpty() || room1.length() == 0 || room1.equals("") || room1==null)
        {
            room.setErrorEnabled(true);
            room.setError("CAN'T BE EMPTY");
            flag=1;
        }
        if(batch1.isEmpty() || batch1.length() == 0 || batch1.equals("") || batch1==null)
        {
            batch.setErrorEnabled(true);
            batch.setError("CAN'T BE EMPTY");
            flag=1;
        }
        if(dept1.isEmpty() || dept1.length() == 0 || dept1.equals("") || dept1==null)
        {
            dept.setErrorEnabled(true);
            dept.setError("CAN'T BE EMPTY");
            flag=1;
        }
        if(flag == 0)
        {
            reg.getEditText().setText("");
            name.getEditText().setText("");
            dept.getEditText().setText("");
            batch.getEditText().setText("");
            gender.getEditText().setText("");
            room.getEditText().setText("");
            password.getEditText().setText("");

            HashMap<String, String> map = new HashMap<>();

            map.put("name", name1);
            map.put("reg", reg1);
            map.put("password",pass1);
            map.put("gen", gen1);
            map.put("room", room1);
            map.put("batch", batch1);
            map.put("dept",dept1);

            Call<Void> call = retrofitInterface.register(SaveSharedPreference.getApiKey(getActivity()),map);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                    if (response.code() == 200) {
                        Toast.makeText(getActivity(),
                                "Registered successfully", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {
                        Toast.makeText(getActivity(),
                                "Already registered", Toast.LENGTH_SHORT).show();
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

    private void init() {
        ////////////////////////TableLayout
        Call<List<LoginResult>> call = retrofitInterface.allUsers(SaveSharedPreference.getApiKey(getActivity()));
        call.enqueue(new Callback<List<LoginResult>>() {
            @Override
            public void onResponse(Call<List<LoginResult>> call, Response<List<LoginResult>> response) {

                if (response.code() == 200) {

                    List<LoginResult> data = response.body();

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
                    int dpAsPixels1 = (int) (10*scale + 0.5f);
                    int dpAsPixels2 = (int) (8*scale + 0.5f);
                    int dpAsPixels3 = (int) (15*scale + 0.5f);
                    int dpAsPixels4 = (int) (16*scale + 0.5f);
                    int dpAsPixels5 = (int) (8.5*scale + 0.5f);

                    ///////////////////Text View Values
                    tv01.setText("Register No");
                    tv02.setText("Name");
                    tv03.setText("Year");
                    tv04.setText("Dept");
                    tv05.setText("Room No");
                    tv06.setText("Remove");

                    tv01.setTextColor(getResources().getColor(R.color.colorTable));
                    tv02.setTextColor(getResources().getColor(R.color.colorTable));
                    tv03.setTextColor(getResources().getColor(R.color.colorTable));
                    tv04.setTextColor(getResources().getColor(R.color.colorTable));
                    tv05.setTextColor(getResources().getColor(R.color.colorTable));
                    tv06.setTextColor(getResources().getColor(R.color.colorTable));

                    tv01.setTypeface(tv01.getTypeface(), Typeface.BOLD);
                    tv02.setTypeface(tv02.getTypeface(), Typeface.BOLD);
                    tv03.setTypeface(tv03.getTypeface(), Typeface.BOLD);
                    tv04.setTypeface(tv04.getTypeface(), Typeface.BOLD);
                    tv05.setTypeface(tv05.getTypeface(), Typeface.BOLD);
                    tv06.setTypeface(tv05.getTypeface(), Typeface.BOLD);

                    tv01.setBackgroundResource(R.drawable.cellshape);
                    tv02.setBackgroundResource(R.drawable.cellshape);
                    tv03.setBackgroundResource(R.drawable.cellshape);
                    tv04.setBackgroundResource(R.drawable.cellshape);
                    tv05.setBackgroundResource(R.drawable.cellshape);
                    tv06.setBackgroundResource(R.drawable.cellshape);

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

                    display.addView(row0);


                    for(LoginResult result : data)
                    {
                        TableRow row1 = new TableRow(getActivity());

                        row1.setLayoutParams(rowParams);

                        ////////////////////Remove Button
                        MaterialButton b1 = new MaterialButton(getActivity());

                        ///////////////////////Columns Values
                        com.google.android.material.textview.MaterialTextView tv1 = new com.google.android.material.textview.MaterialTextView(getActivity());
                        com.google.android.material.textview.MaterialTextView tv2 = new com.google.android.material.textview.MaterialTextView(getActivity());
                        com.google.android.material.textview.MaterialTextView tv3 = new com.google.android.material.textview.MaterialTextView(getActivity());
                        com.google.android.material.textview.MaterialTextView tv4 = new com.google.android.material.textview.MaterialTextView(getActivity());
                        com.google.android.material.textview.MaterialTextView tv5 = new com.google.android.material.textview.MaterialTextView(getActivity());

                        tv1.setInputType(InputType.TYPE_CLASS_NUMBER);
                        //////////////////////Linear Layout for Remove Button
                        LinearLayout remove_button = new LinearLayout(getActivity());

                        remove_button.setPadding(dpAsPixels1, dpAsPixels2, dpAsPixels1, dpAsPixels5);
                        remove_button.setBackgroundResource(R.drawable.cellshape);

                        ////////////////////////Remove Button Values
                        b1.setGravity(Gravity.CENTER_HORIZONTAL);
                        b1.setTextColor(Color.parseColor("#000000"));
                        b1.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorTitle)));
                        b1.setTypeface(b1.getTypeface(), Typeface.BOLD);
                        b1.setText("REMOVE");
                        b1.setId(result.getIdd());
                        b1.setOnClickListener(User.this);
                        b1.setTextColor(getResources().getColor(R.color.colorTextTitle));
                        remove_button.addView(b1);

                        ////////////////////////Setting data to columns ( Text Views )

                        tv1.setText(""+result.getRoll());
                        tv2.setText(result.getName());
                        tv3.setText(result.getBatch());
                        tv4.setText(result.getDept());
                        tv5.setText(result.getRoom());

                        tv1.setBackgroundResource(R.drawable.cellshape);
                        tv2.setBackgroundResource(R.drawable.cellshape);
                        tv3.setBackgroundResource(R.drawable.cellshape);
                        tv4.setBackgroundResource(R.drawable.cellshape);
                        tv5.setBackgroundResource(R.drawable.cellshape);

                        tv1.setTextColor(getResources().getColor(R.color.colorTable));
                        tv2.setTextColor(getResources().getColor(R.color.colorTable));
                        tv3.setTextColor(getResources().getColor(R.color.colorTable));
                        tv4.setTextColor(getResources().getColor(R.color.colorTable));
                        tv5.setTextColor(getResources().getColor(R.color.colorTable));


                        tv1.setTextSize(25);
                        tv2.setTextSize(25);
                        tv3.setTextSize(25);
                        tv4.setTextSize(25);
                        tv5.setTextSize(25);

                        tv1.setPadding(dpAsPixels1, dpAsPixels4, dpAsPixels1, dpAsPixels3);
                        tv2.setPadding(dpAsPixels1, dpAsPixels4, dpAsPixels1, dpAsPixels3);
                        tv3.setPadding(dpAsPixels1, dpAsPixels4, dpAsPixels1, dpAsPixels3);
                        tv4.setPadding(dpAsPixels1, dpAsPixels4, dpAsPixels1, dpAsPixels3);
                        tv5.setPadding(dpAsPixels1, dpAsPixels4, dpAsPixels1, dpAsPixels3);

                        //////////////////Adding values to Row
                        row1.addView(tv1);
                        row1.addView(tv2);
                        row1.addView(tv3);
                        row1.addView(tv4);
                        row1.addView(tv5);
                        row1.addView(remove_button);
                        /////////////////Adding Row to Table Layout
                        display.addView(row1);
                    }
                } else if (response.code() == 404) {
                    Toast.makeText(getActivity(), "Data not found",
                            Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<LoginResult>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deluser(int id) {
        Call<Void> call = retrofitInterface.deleteUser(SaveSharedPreference.getApiKey(getActivity()),id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.code() == 200) {
                    Toast.makeText(getActivity(), "User Deleted",
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        reg.setErrorEnabled(false);
        name.setErrorEnabled(false);
        dept.setErrorEnabled(false);
        gender.setErrorEnabled(false);
        batch.setErrorEnabled(false);
        room.setErrorEnabled(false);
        password.setErrorEnabled(false);

    }

    @Override
    public void afterTextChanged(Editable s) {}
}
