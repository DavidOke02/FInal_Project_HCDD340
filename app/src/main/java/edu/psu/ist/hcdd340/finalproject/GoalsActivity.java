package edu.psu.ist.hcdd340.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class GoalsActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener{

    private BottomNavigationView bottomNavigationView;
    private static final String TAG = "ACTIVITY_GOALS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navbar_goals_item);
        bottomNavigationView.setOnItemSelectedListener(this);
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
