package edu.psu.ist.hcdd340.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class EventsActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener{

    private CalendarView calendarView;
    private BottomNavigationView bottomNavigationView;
    private static final String TAG = "ACTIVITY_EVENT";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        //Setting up Event Page elements
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navbar_schedule_item);
        bottomNavigationView.setOnItemSelectedListener(this);

        // Find the views
        calendarView = findViewById(R.id.calendarView);
        Button todayButton = findViewById(R.id.todayButton);

        // Set the calendar to display today's date when the activity starts
        calendarView.setDate(System.currentTimeMillis(), false, true);

        // Set up the "Select Today" button
        todayButton.setOnClickListener(v -> {
            // Get the current time in milliseconds
            long today = System.currentTimeMillis();

            // Update the CalendarView to display today's date
            calendarView.setDate(today, true, true);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.event_menu, menu);
        return true;
    }

    //Add Menu
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();

        if (menuId == R.id.add_to_calendar_item) {
            Log.d(TAG, "Add to Schedule menu clicked!");
            Intent addEventIntent = new Intent(this, AddEventActivity.class);
            startActivity(addEventIntent);
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
