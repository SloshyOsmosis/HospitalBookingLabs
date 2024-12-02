package com.example.secdevlab4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UpdatePatientActivity extends AppCompatActivity {
    EditText editTextFirstName, editTextLastName, editTextAge;
    Spinner spinnerGender;
    Button buttonUpdatePatient;
    DBHelper myDB;
    int patientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_patient);

        editTextAge = findViewById(R.id.editTextAge);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        spinnerGender = findViewById(R.id.spinnerGender);
        buttonUpdatePatient = findViewById(R.id.buttonUpdatePatient);

        myDB = new DBHelper(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.gender_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);

        // Getting data from intent
        Intent intent = getIntent();
        patientId = intent.getIntExtra("patientId", -1);
        String firstName = intent.getStringExtra("firstName");
        String lastName = intent.getStringExtra("lastName");
        int age = intent.getIntExtra("age", 0);
        String gender = intent.getStringExtra("gender");

        editTextFirstName.setText(firstName);
        editTextLastName.setText(lastName);
        editTextAge.setText(String.valueOf(age));

        if (gender != null) {
            int genderPosition = getGenderPosition(gender);
            spinnerGender.setSelection(genderPosition);
        }

        buttonUpdatePatient.setOnClickListener(v -> updatePatientInDatabase());// Updates patient in database on button press
    }

    private int getGenderPosition(String gender) {
        String[] genders = getResources().getStringArray(R.array.gender_array);
        for (int i = 0; i < genders.length; i++) {
            if (genders[i].equals(gender)) {
                return i;
            }
        }
        return 0; // Default to the first position if not found
    }

    private void updatePatientInDatabase() {
        String fName = editTextFirstName.getText().toString().trim();
        String lName = editTextLastName.getText().toString().trim();
        int age = Integer.parseInt(editTextAge.getText().toString().trim());
        String gender = spinnerGender.getSelectedItem().toString().trim();

        if (fName.isEmpty() || lName.isEmpty() || age <= 0 || gender.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean result = myDB.updatePatient(patientId, fName, lName, age, gender);
        if (result) {
            Toast.makeText(this, "Patient updated successfully", Toast.LENGTH_SHORT).show();
            finish(); //Return to the previous screen
        } else {
            Toast.makeText(this, "Failed to update patient", Toast.LENGTH_SHORT).show();
        }
    }
}