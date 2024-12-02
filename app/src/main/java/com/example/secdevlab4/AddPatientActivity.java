package com.example.secdevlab4;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddPatientActivity extends AppCompatActivity {

    EditText editTextFirstName, editTextLastName, editTextAge;
    Spinner spinnerGender;
    Button buttonAddPatient;
    DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextAge = findViewById(R.id.editTextAge);
        spinnerGender = findViewById(R.id.spinnerGender);
        buttonAddPatient = findViewById(R.id.addPatientbtn);

        myDB = new DBHelper(this);

        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(
                this, R.array.gender_array, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(genderAdapter);

        buttonAddPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPatientToDatabase();

            }
        });
    }

    private void addPatientToDatabase() {
        // Retrieve input values
        String fName = editTextFirstName.getText().toString().trim();
        String lName = editTextLastName.getText().toString().trim();
        String ageString = editTextAge.getText().toString().trim();
        String gender = spinnerGender.getSelectedItem().toString();

        // Validate input fields
        if (fName.isEmpty() || lName.isEmpty() || ageString.isEmpty() || gender.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageString); // Safely parse age to an integer
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid age. Please enter a number.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Insert data into the database
        boolean result = myDB.insertPatient(fName, lName, age, gender);
        if (result) {
            Toast.makeText(this, "Patient added successfully", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity and return to the previous screen
        } else {
            Toast.makeText(this, "Failed to add patient", Toast.LENGTH_SHORT).show();
        }
    }
}