package edu.psu.ist.hcdd340.finalproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ACTIVITY_LOGIN";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button button = findViewById(R.id.btn_login);
        button.setOnClickListener(this);

        sharedPreferences = getSharedPreferences(SignUpActivity.SHARED_PREF_NAME, MODE_PRIVATE);
    }

    @Override
    public void onClick(View view) {
        EditText emailEditText = findViewById(R.id.et_email);
        String email = emailEditText.getText().toString();

        EditText passwordEditText = findViewById(R.id.et_password);
        String password = passwordEditText.getText().toString();

        Log.d(TAG, "Input Email: " + email + ". Input Password: " + password);

        //Login Check
        String savedUser = sharedPreferences.getString(SignUpActivity.EMAIL_KEY, null);
        String savedPassword = sharedPreferences.getString(SignUpActivity.PASSWORD_KEY, null);

        if (email.equals(savedUser) && password.equals(savedPassword)){
            Button button = findViewById(R.id.btn_login);
            Snackbar.make(button, "Successfully logged in!", Snackbar.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(SignUpActivity.LOGGED_IN_KEY, true);
            editor.apply();
            Log.d(TAG, "Login succeeded!");
            //Delay so Snackbar can display before going back home
            new Handler(Looper.getMainLooper()).postDelayed(() -> finish(), 3000);
        }

        else {
            AlertDialog.Builder d = new AlertDialog.Builder(this);
            d.setTitle(R.string.login_error_title);
            d.setMessage(R.string.login_error_message);
            d.setPositiveButton(android.R.string.ok, null);
            d.show();
        }
    }
}