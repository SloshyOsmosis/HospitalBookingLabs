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

public class UpdateDoctorActivity extends AppCompatActivity {

    EditText editTextFirstName, editTextLastName;
    Spinner spinnerSpecialty;
    Button buttonUpdateDoctor;
    DBHelper myDB;
    int doctorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_doctor);

        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        spinnerSpecialty = findViewById(R.id.spinnerSpecialty);
        buttonUpdateDoctor = findViewById(R.id.buttonUpdateDoctor);

        myDB = new DBHelper(this);
        // Populating the spinner with doctor specialties
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.doctor_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSpecialty.setAdapter(adapter);

        // Getting data from intent
        Intent intent = getIntent();
        doctorId = intent.getIntExtra("doctorId", -1);
        String firstName = intent.getStringExtra("firstName");
        String lastName = intent.getStringExtra("lastName");
        String specialty = intent.getStringExtra("specialty");

        editTextFirstName.setText(firstName);
        editTextLastName.setText(lastName);

        if (specialty != null) {
            int specialtyPosition = getSpecialtyPosition(specialty);
            spinnerSpecialty.setSelection(specialtyPosition);
        }

        buttonUpdateDoctor.setOnClickListener(v -> updateDoctorInDatabase());// Updates doctor in database on button press

    }

    private int getSpecialtyPosition(String specialty) {
        String[] specialties = getResources().getStringArray(R.array.doctor_array);
        for (int i = 0; i < specialties.length; i++) {
            if (specialties[i].equals(specialty)) {
                return i;
            }
    }
        return 0; // Default to the first position if not found
    }

    // Updates the doctor in the database
    private void updateDoctorInDatabase() {
        String fName = editTextFirstName.getText().toString().trim();
        String lName = editTextLastName.getText().toString().trim();
        String specialty = spinnerSpecialty.getSelectedItem().toString().trim();

        // Check if any fields are empty
        if (fName.isEmpty() || lName.isEmpty() || specialty.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update the doctor in the database
        boolean result = myDB.updateDoctor(doctorId, fName, lName, specialty);


        if (result) {
            // Show a success message if the update is successful
            Toast.makeText(this, "Doctor updated successfully", Toast.LENGTH_SHORT).show();
            finish(); //Return to the previous screen

            }
        // Show a failure message if the update fails
        else {
            Toast.makeText(this, "Failed to update doctor", Toast.LENGTH_SHORT).show();
        }
    }
}