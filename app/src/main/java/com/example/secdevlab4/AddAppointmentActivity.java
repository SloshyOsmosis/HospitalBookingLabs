package com.example.secdevlab4;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;
import java.util.List;

public class AddAppointmentActivity extends AppCompatActivity {
    EditText editTextReason, editTextDate, editTextTime;
    Spinner spinnerPatient, spinnerDoctor;
    Button buttonAddAppointment;
    DBHelper myDB;
    List<Patient> patientList; // Declare patientList
    List<Doctor> doctorList; // Declare doctorList

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        // Initialize views
        editTextReason = findViewById(R.id.editTextReason);
        editTextDate = findViewById(R.id.editTextDate);
        editTextTime = findViewById(R.id.editTextTime);
        spinnerPatient = findViewById(R.id.doctorSpinner);
        spinnerDoctor = findViewById(R.id.patientSpinner);
        buttonAddAppointment = findViewById(R.id.addAppointmentbtn);

        // Set up date and time pickers
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                // Get the current date
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // Create a date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddAppointmentActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            // Set the date in the EditText
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) { //
                                editTextDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                            }
                        },
                        year,
                        month,
                        day
                );
                datePickerDialog.show();
            }
        });

        // Set up time picker
        editTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                // Create a time picker dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AddAppointmentActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            // Set the time in the EditText
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // Format the time with leading zeros if necessary
                                String formattedTime = String.format("%02d:%02d", hourOfDay, minute);
                                editTextTime.setText(formattedTime);
                            }
                        },
                        hour,
                        minute,
                        true // Set to true for 24-hour format, false for 12-hour format
                );

                timePickerDialog.show(); // Show the dialog
            }
        });

        // Initialize the database helper
        myDB = new DBHelper(this);

        buttonAddAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAppointmentToDatabase();
            }
        });

        // Customising the spinners
        try {
            patientList = myDB.getPatients(); // Get list of patients from the DB
            doctorList = myDB.getDoctors(); // Get list of doctors from the DB

            ArrayAdapter<Patient> patientAdapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, patientList) {
                @NonNull
                @Override
                public View getView(int position, View convertView, android.view.ViewGroup parent) {
                    // Spinner view to display full name
                    Patient patient = getItem(position);
                    if (convertView == null) {
                        convertView = super.getView(position, convertView, parent);
                    }
                    if (patient != null) {
                        ((android.widget.TextView) convertView).setText(patient.getFullName());
                    }
                    return convertView;
                }

                @Override
                public View getDropDownView(int position, View convertView, android.view.ViewGroup parent) {
                    // Customize dropdown view to display full name (first and last name)
                    Patient patient = getItem(position);
                    if (convertView == null) {
                        convertView = super.getDropDownView(position, convertView, parent);
                    }
                    if (patient != null) {
                        ((android.widget.TextView) convertView).setText(patient.getFullName());
                    }
                    return convertView;
                }
            };

            patientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerPatient.setAdapter(patientAdapter);

            // For Doctor Spinner, display doctor's full name (first name + last name)
            ArrayAdapter<Doctor> doctorAdapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, doctorList) {
                @Override
                public View getView(int position, View convertView, android.view.ViewGroup parent) {
                    // Customize spinner view to display full name (first and last name)
                    Doctor doctor = getItem(position);
                    if (convertView == null) {
                        convertView = super.getView(position, convertView, parent);
                    }
                    if (doctor != null) {
                        ((android.widget.TextView) convertView).setText(doctor.getFullName());
                    }
                    return convertView;
                }

                @Override
                public View getDropDownView(int position, View convertView, android.view.ViewGroup parent) {
                    // Customize dropdown view to display full name (first and last name)
                    Doctor doctor = getItem(position);
                    if (convertView == null) {
                        convertView = super.getDropDownView(position, convertView, parent);
                    }
                    if (doctor != null) {
                        ((android.widget.TextView) convertView).setText(doctor.getFullName());
                    }
                    return convertView;
                }
            };
            doctorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerDoctor.setAdapter(doctorAdapter);

        } catch (Exception e) {
            // Handle exceptions
            Toast.makeText(this, "Error loading data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void addAppointmentToDatabase(){
        // Retrieve input values
        String reason = editTextReason.getText().toString().trim();
        String date = editTextDate.getText().toString().trim();
        String time = editTextTime.getText().toString().trim();

        // Get selected patient and doctor from spinners
        Patient selectedPatient = (Patient) spinnerPatient.getSelectedItem();
        Doctor selectedDoctor = (Doctor) spinnerDoctor.getSelectedItem();

        // Check if fields are empty
        if (reason.isEmpty() || date.isEmpty() || time.isEmpty() || selectedPatient == null || selectedDoctor == null) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get patient and doctor IDs
        int patientId = selectedPatient.getId();
        int doctorId = selectedDoctor.getId();

        // Check for appointment conflict
        if (myDB.checkAppointmentConflict(doctorId, date, time)) {
            Toast.makeText(this, "Error: Doctor already has an appointment within 15 minutes.", Toast.LENGTH_LONG).show();
            return;
        }

        // Insert data into the database
        boolean result = myDB.insertAppointment(patientId, doctorId, date, time, reason);
        if (result) {
            Toast.makeText(this, "Appointment added successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            // Handle the error
            Toast.makeText(this, "Failed to add appointment", Toast.LENGTH_SHORT).show();
        }
    }
}