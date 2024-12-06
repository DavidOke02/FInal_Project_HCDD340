package edu.psu.ist.hcdd340.finalproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etInstitutionPlace, etInstitutionType, etExpectedGradDate;
    private Button btnUpdate;
    private Button btnClear;
    private SharedPreferences sharedPreferences_user;
    private SharedPreferences sharedPreferences_extra;
    private static final String TAG = "ACTIVITY_PROFILE";
    public static final String SHARED_PREF_NAME = "EXTRA_INFO";
    public static final String INSTITUTION_PLACE_KEY =  "INSTITUTION_PLACE";
    public static final String INSTITUTION_TYPE_KEY = "INSTITUTION_TYPE";
    public static final String EXPECTED_GRAD_DATE_KEY =  "EXPECTED_GRAD_DATE";
    public static final String SAVED_KEY = "SAVED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Setting up Profile Display Card Info
        sharedPreferences_user = getSharedPreferences(SignUpActivity.SHARED_PREF_NAME, MODE_PRIVATE);
        String firstName = sharedPreferences_user.getString(SignUpActivity.FIRST_NAME_KEY, null);
        String lastName = sharedPreferences_user.getString(SignUpActivity.LAST_NAME_KEY, null);
        String fullName = String.format("%s %s", firstName, lastName);
        String email = sharedPreferences_user.getString(SignUpActivity.EMAIL_KEY, null);
        String major = sharedPreferences_user.getString(SignUpActivity.MAJOR_KEY, null);

        TextView idName = findViewById(R.id.profile_id_name);
        idName.setText(String.format("%s %s", getString(R.string.profile_id_name), fullName));

        TextView idEmail = findViewById(R.id.profile_id_email);
        idEmail.setText(String.format("%s %s", getString(R.string.email_label) , email));

        TextView idMajor = findViewById(R.id.profile_id_major);
        idMajor.setText(String.format("%s %s", getString(R.string.profile_id_major) , major));

        sharedPreferences_extra = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        refreshExtras();

        // Initialize views
        etInstitutionPlace = findViewById(R.id.et_profile_institution_place);
        etInstitutionType = findViewById(R.id.et_profile_institution_type);
        etExpectedGradDate = findViewById(R.id.et_profile_expected_grad_date);
        btnUpdate = findViewById(R.id.btn_profile_update_info);
        btnUpdate.setOnClickListener(this);
        btnClear = findViewById(R.id.btn_profile_clear_info);
        btnClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_profile_update_info) {
            Log.d(TAG, "Update Button Clicked!");
            updateInfo();
            refreshExtras();
        } else if (id == R.id.btn_profile_clear_info) {
            Log.d(TAG, "Clear Button Clicked!");
            clearInfo();
            refreshExtras();
        }
    }

    public void refreshExtras() {
        // Setting extra profile info if its save exists
        String institutionPlace = sharedPreferences_extra.getString(INSTITUTION_PLACE_KEY, null);
        String institutionType = sharedPreferences_extra.getString(INSTITUTION_TYPE_KEY, null);
        String expectedGradDate = sharedPreferences_extra.getString(EXPECTED_GRAD_DATE_KEY, null);

        TextView idInstitutionPlace = findViewById(R.id.profile_id_institution_place);
        TextView idInstitutionType = findViewById(R.id.profile_id_institution_type);
        TextView idExpectedGradDate = findViewById(R.id.profile_id_expected_grad_date);
        if (sharedPreferences_extra.getBoolean(SAVED_KEY, false) == true){
            idInstitutionPlace.setText(String.format("%s %s", getString(R.string.profile_id_institution_place), institutionPlace));
            idInstitutionType.setText(String.format("%s %s", getString(R.string.profile_id_institution_type), institutionType));
            idExpectedGradDate.setText(String.format("%s %s", getString(R.string.profile_id_expected_grad_date), expectedGradDate));
        } else {
            idInstitutionPlace.setText(String.format("%s N/A", getString(R.string.profile_id_institution_place)));
            idInstitutionType.setText(String.format("%s N/A", getString(R.string.profile_id_institution_type)));
            idExpectedGradDate.setText(String.format("%s N/A", getString(R.string.profile_id_expected_grad_date)));
        }
    }

    public void updateInfo() {
        String institutionPlace = etInstitutionPlace.getText().toString();
        String institutionType = etInstitutionType.getText().toString();
        String expectedGradDate = etExpectedGradDate.getText().toString();

        if (institutionPlace.isEmpty() || institutionType.isEmpty() || expectedGradDate.isEmpty()) {
            Snackbar.make(btnUpdate, "Please fill all fields", Snackbar.LENGTH_SHORT).show();
        } else {
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(INSTITUTION_PLACE_KEY, institutionPlace);
            editor.putString(INSTITUTION_TYPE_KEY, institutionType);
            editor.putString(EXPECTED_GRAD_DATE_KEY, expectedGradDate);
            editor.putBoolean(SAVED_KEY, true);
            editor.apply();

            Snackbar.make(btnUpdate, "Info Updated!", Snackbar.LENGTH_SHORT).show();
        }
    }

    public void clearInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.apply();

        Snackbar.make(btnClear, "Info Cleared!", Snackbar.LENGTH_SHORT).show();

    }
}