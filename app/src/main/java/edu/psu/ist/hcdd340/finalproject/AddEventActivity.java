package edu.psu.ist.hcdd340.finalproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.material.snackbar.Snackbar;

public class AddEventActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        // Find the Save button by its ID
        Button btnSaveEvent = findViewById(R.id.btn_save_event);

        // Set an OnClickListener for the Save button
        btnSaveEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a Snackbar with the message when the Save button is clicked
                Snackbar.make(v, "Event Added to Calendar", Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
