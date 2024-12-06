package edu.psu.ist.hcdd340.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    private RecyclerView recyclerView;
    private GlanceCardAdapter cardAdapter;
    private GlanceCardData[] cardData;
    private BottomNavigationView bottomNavigationView;
    private SharedPreferences sharedPreferences;
    private boolean isLoggedIn;
    private static final String TAG = "ACTIVITY_MAIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting up home page basics
        isLoggedIn = false;
        invalidateOptionsMenu();
        recyclerView = findViewById(R.id.glance_panel);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navbar_home_item);
        bottomNavigationView.setOnItemSelectedListener(this);

        //Display username if logged in
        sharedPreferences = getSharedPreferences(SignUpActivity.SHARED_PREF_NAME, MODE_PRIVATE);
        TextView welcomeMessage = findViewById(R.id.welcome_text);
        if (sharedPreferences.getBoolean(SignUpActivity.LOGGED_IN_KEY, false) == true){
            String userName = sharedPreferences.getString(SignUpActivity.FIRST_NAME_KEY, null);
            welcomeMessage.setText(String.format("%s %s!", getString(R.string.home_welcome_message), userName));
            isLoggedIn = true;
        } else {
            welcomeMessage.setText(String.format("%s User!", getString(R.string.home_welcome_message)));
        }

        // Sample data for the At a Glance cards
        cardData = new GlanceCardData[]{
                new GlanceCardData("Today's Breakdown", getString(R.string.glance_panel_text_1)),
                new GlanceCardData("Recent Insights", getString(R.string.glance_panel_text_2)),
                new GlanceCardData("Quick Motivation & Encouragement", getString(R.string.glance_panel_text_3)),
        };

        // Set up the LinearLayoutManager to be horizontal
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Set the adapter
        cardAdapter = new GlanceCardAdapter(this, cardData);
        recyclerView.setAdapter(cardAdapter);

        // Snap helper to make the scroll stop at each card and snap to one at a time
        SnapHelper snapHelper = new PagerSnapHelper() {
            @Override
            public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
                return super.findTargetSnapPosition(layoutManager, velocityX, velocityY);
            }
        };
        snapHelper.attachToRecyclerView(recyclerView);
    }

    //Makes sure page refreshes after login
    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        //Checks login state to determine menu options
        if (isLoggedIn == true){
            MenuItem item1 = menu.findItem(R.id.login_item);
            MenuItem item2 = menu.findItem(R.id.sign_up_item);
            item1.setVisible(false);
            item2.setVisible(false);
        }

        else {
            MenuItem item1 = menu.findItem(R.id.view_profile_item);
            MenuItem item2 = menu.findItem(R.id.sign_out_item);
            item1.setVisible(false);
            item2.setVisible(false);
        }
        return true;
    }

    //Profile Menu
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();

        if (menuId == R.id.login_item) {
            Log.d(TAG, "Login menu clicked!");
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
            return true;
        } else if (menuId == R.id.sign_up_item) {
            Log.d(TAG, "Sign Up menu clicked!");
            Intent signUpIntent = new Intent(this, SignUpActivity.class);
            startActivity(signUpIntent);
            return true;
        } else if (menuId == R.id.profile_item) {
            Log.d(TAG, "Profile menu clicked!");
            return true;
        } else if (menuId == R.id.view_profile_item) {
            Log.d(TAG, "View Profile menu clicked!");
            Intent viewProfileIntent = new Intent(this, ProfileActivity.class);
            startActivity(viewProfileIntent);
            return true;
        } else if (menuId == R.id.sign_out_item) {
            Log.d(TAG, "Sign Out menu clicked!");
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(SignUpActivity.LOGGED_IN_KEY, false);
            editor.apply();
            invalidateOptionsMenu();
            recreate();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Navigation Bar Stuff
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (bottomNavigationView.getSelectedItemId() != itemId) {
            switch (itemId) {
                case R.id.navbar_home_item:
                    Log.d(TAG, "Home Clicked!");
                    Intent homeIntent = new Intent(this, MainActivity.class);
                    startActivity(homeIntent);
                    return true;

                case R.id.navbar_schedule_item:
                    Log.d(TAG, "Schedule Clicked!");
                    Intent scheduleIntent = new Intent(this, EventsActivity.class);
                    startActivity(scheduleIntent);
                    return true;

                case R.id.navbar_goals_item:
                    Log.d(TAG, "Goals Clicked!");
                    Intent goalsIntent = new Intent(this, GoalsActivity.class);
                    startActivity(goalsIntent);
                    return true;

                case R.id.navbar_study_item:
                    Log.d(TAG, "Study Clicked!");
                    Intent studyIntent = new Intent(this, StudyActivity.class);
                    startActivity(studyIntent);
                    return true;
            }
        }
        return false;
    }
}