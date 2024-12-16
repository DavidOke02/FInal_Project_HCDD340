package edu.psu.ist.hcdd340.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import java.util.ArrayList;
import java.util.Set;

public class EventsActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, View.OnClickListener {

    private CalendarView calendarView;
    private BottomNavigationView bottomNavigationView;
    private static final String TAG = "ACTIVITY_EVENT";

    private LinearLayout eventsLayout;
    private ArrayList<String> eventsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        // Setting up Event Page elements
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navbar_schedule_item);
        bottomNavigationView.setOnItemSelectedListener(this);

        // Find the views
        calendarView = findViewById(R.id.calendarView);
        eventsLayout = findViewById(R.id.eventLayout); // The LinearLayout that will hold the event list

        // Set the calendar to display today's date when the activity starts
        calendarView.setDate(System.currentTimeMillis(), false, true);

        // Initialize the list of events
        eventsList = new ArrayList<>();

        // Retrieve saved events from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("EventPrefs", MODE_PRIVATE);
        Set<String> eventSet = sharedPreferences.getStringSet("events", null);
        if (eventSet != null) {
            eventsList.addAll(eventSet);  // Add saved events to the list
        }

        // Display the saved events (only task names)
        displayEvents();

        // Set a listener for date changes on the CalendarView
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // Log the selected date (you can add other actions here)
            Log.d(TAG, "Selected date: " + dayOfMonth + "/" + (month + 1) + "/" + year);
        });
    }

    // Method to display events in the LinearLayout
    private void displayEvents() {
        eventsLayout.removeView(findViewById(R.id.eventDescription)); // Clear existing description view

        // If there are no events, show "No events today"
        if (eventsList.isEmpty()) {
            TextView noEventsText = new TextView(this);
            noEventsText.setText("No events today");
            noEventsText.setPadding(16, 8, 16, 8);
            eventsLayout.addView(noEventsText);
        } else {
            // Otherwise, display the list of task names
            for (String event : eventsList) {
                TextView eventText = new TextView(this);
                eventText.setText(event);  // Only show the task name
                eventText.setTextSize(16);
                eventText.setPadding(16, 8, 16, 8);
                eventsLayout.addView(eventText);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.event_menu, menu);
        return true;
    }

    // Add Menu
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

    @Override
    public void onClick (View view){
        calendarView.setDate(System.currentTimeMillis(), true, true);
    }

    // Navigation Bar Stuff
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