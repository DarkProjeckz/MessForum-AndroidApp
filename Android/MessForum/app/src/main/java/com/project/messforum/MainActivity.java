package com.project.messforum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawer;
    ActionBar actionBar;
    TextView mTitleTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(SaveSharedPreference.getUserName(MainActivity.this).length() == 0)
        {
            Intent i = new Intent(MainActivity.this,Login.class);
            startActivity(i);
            finish();
        }
        else
        {
            // Stay at the current activity.
            setContentView(R.layout.activity_main);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            actionBar = getSupportActionBar();
            mTitleTextView = new TextView(getApplicationContext());
           // mTitleTextView.setSingleLine();
            ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER;
            actionBar.setCustomView(mTitleTextView, layoutParams);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP);

            mTitleTextView.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Material_Widget_ActionBar_Title);
            mTitleTextView.setTextColor(getResources().getColor(R.color.colorWhite));
            mTitleTextView.setText("Dashboard");

            drawer = findViewById(R.id.drawer_layout);
            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            if(savedInstanceState == null)
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Dashboard()).commit();
                navigationView.setCheckedItem(R.id.dashboard);
            }
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId())
        {
            case R.id.dashboard:
                mTitleTextView.setText("Dashboard");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Dashboard()).commit();
                break;

            case R.id.user:
                mTitleTextView.setText("Student Details");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new User()).commit();
                break;

            case R.id.count:
                mTitleTextView.setText("Count");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Count()).commit();
                break;

            case R.id.token:
                mTitleTextView.setText("Token");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Token()).commit();
                break;

            case R.id.notification:
                mTitleTextView.setText("Notification");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Notification()).commit();
                break;

            case R.id.logout:

                SaveSharedPreference.clearUserName(this);
                Toast.makeText(this,"Logged out successfully",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this,Login.class);
                startActivity(i);
                finish();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
}