package com.example.secdevlab4;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddDoctorActivity extends AppCompatActivity {

    EditText editTextFirstName, editTextLastName;
    Spinner spinnerSpecialty;
    Button buttonAddDoctor;
    DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);

        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        spinnerSpecialty = findViewById(R.id.spinnerSpecialty);
        buttonAddDoctor = findViewById(R.id.addDoctorbtn);

        myDB = new DBHelper(this);

        ArrayAdapter<CharSequence> specialtyAdapter = ArrayAdapter.createFromResource(
                this, R.array.doctor_array, android.R.layout.simple_spinner_item);
        specialtyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSpecialty.setAdapter(specialtyAdapter);

        buttonAddDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDoctorToDatabase();
            }
        });
    }

    private void addDoctorToDatabase() {
        // Retrieve input values
        String fName = editTextFirstName.getText().toString().trim();
        String lName = editTextLastName.getText().toString().trim();
        String specialty = spinnerSpecialty.getSelectedItem().toString();

        // Validate input fields
        if (fName.isEmpty() || lName.isEmpty() || specialty.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Insert data into the database
        boolean result = myDB.insertDoctor(fName, lName, specialty);
        if (result) {
            Toast.makeText(this, "Doctor added successfully", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity and return to the previous screen
        } else {
            Toast.makeText(this, "Failed to add doctor", Toast.LENGTH_SHORT).show();
        }
    }
}