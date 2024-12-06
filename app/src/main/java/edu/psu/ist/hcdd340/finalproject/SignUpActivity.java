package edu.psu.ist.hcdd340.finalproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class SignUpActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etEmail, etPassword;
    private Spinner spinnerMajor;
    private Button btnSignUp;
    private static final String TAG = "ACTIVITY_LOGIN";
    public static final String SHARED_PREF_NAME = "USER_INFO";
    public static final String FIRST_NAME_KEY =  "FIRST_NAME";
    public static final String LAST_NAME_KEY = "LAST_NAME";
    public static final String EMAIL_KEY =  "EMAIL";
    public static final String PASSWORD_KEY = "PASSWORD";
    public static final String MAJOR_KEY =  "MAJOR";
    public static final String LOGGED_IN_KEY = "LOGGED_IN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize views
        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        spinnerMajor = findViewById(R.id.spinner_major);
        btnSignUp = findViewById(R.id.btn_sign_up);

        // Set up Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.major_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMajor.setAdapter(adapter);

        // Save user info on Sign Up button click
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInfo();
            }
        });
    }

    private void saveUserInfo() {
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String major = spinnerMajor.getSelectedItem().toString();

        if (firstName.isEmpty() || email.isEmpty() || password.isEmpty() || major.equals(getString(R.string.major_prompt))) {
            Snackbar.make(btnSignUp, "Please fill all fields", Snackbar.LENGTH_SHORT).show();
        } else {
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(FIRST_NAME_KEY, firstName);
            editor.putString(LAST_NAME_KEY, lastName);
            editor.putString(EMAIL_KEY, email);
            editor.putString(PASSWORD_KEY, password);
            editor.putString(MAJOR_KEY, major);
            editor.putBoolean(LOGGED_IN_KEY, false);
            editor.apply();

            Snackbar.make(btnSignUp, "Sign Up Successful!", Snackbar.LENGTH_SHORT).show();
        }
    }
}