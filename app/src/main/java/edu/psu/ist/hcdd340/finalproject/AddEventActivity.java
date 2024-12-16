package edu.psu.ist.hcdd340.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Set;

public class AddEventActivity extends AppCompatActivity {

    private EditText etTaskName;
    private Button btnSaveEvent, btnCancel;
    private static final String TAG = "AddEventActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        // Initialize UI elements
        etTaskName = findViewById(R.id.et_task_name);
        btnSaveEvent = findViewById(R.id.btn_save_event);
        btnCancel = findViewById(R.id.btn_cancel);

        // Handle the Save Event button click
        btnSaveEvent.setOnClickListener(v -> {
            String taskName = etTaskName.getText().toString().trim();
            if (!taskName.isEmpty()) {
                // Save the task name in SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("EventPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                // Retrieve current events and add the new task name
                Set<String> eventSet = sharedPreferences.getStringSet("events", new HashSet<>());
                eventSet.add(taskName);  // Add the new task name
                editor.putStringSet("events", eventSet);
                editor.apply();

                // Show confirmation message
                Snackbar.make(v, "Event Added to Calendar", Snackbar.LENGTH_LONG).show();

                // Go back to the EventsActivity
                Intent returnIntent = new Intent(AddEventActivity.this, EventsActivity.class);
                startActivity(returnIntent);
            } else {
                // Handle empty task name error (optional)
                Snackbar.make(v, "Task Name cannot be empty", Snackbar.LENGTH_LONG).show();
            }
        });

        // Handle the Cancel button click
        btnCancel.setOnClickListener(v -> {
            // Go back to the EventsActivity without saving
            Intent returnIntent = new Intent(AddEventActivity.this, EventsActivity.class);
            startActivity(returnIntent);
        });
    }
}