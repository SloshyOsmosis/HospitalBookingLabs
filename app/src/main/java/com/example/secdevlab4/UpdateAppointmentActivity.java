package com.example.secdevlab4;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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

public class UpdateAppointmentActivity extends AppCompatActivity {
    EditText editTextReason, editTextDate, editTextTime;
    Spinner spinnerPatient, spinnerDoctor;
    Button buttonUpdateAppointment;
    DBHelper myDB;
    List<Patient> patientList;
    List<Doctor> doctorList;
    int appointmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_appointment);

        editTextReason = findViewById(R.id.editTextReason);
        editTextDate = findViewById(R.id.editTextDate);
        editTextTime = findViewById(R.id.editTextTime);
        spinnerPatient = findViewById(R.id.patientSpinner);
        spinnerDoctor = findViewById(R.id.doctorSpinner);
        buttonUpdateAppointment = findViewById(R.id.buttonUpdateAppointment);

        Intent intent = getIntent();
        appointmentId = intent.getIntExtra("appointmentId", -1);
        String reason = intent.getStringExtra("reason");
        String date = intent.getStringExtra("date");
        String time = intent.getStringExtra("time");
        int patientId = intent.getIntExtra("patientId", -1);
        int doctorId = intent.getIntExtra("doctorId", -1);

        editTextReason.setText(reason);
        editTextDate.setText(date);
        editTextTime.setText(time);

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        UpdateAppointmentActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
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

        editTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        UpdateAppointmentActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
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

        myDB = new DBHelper(this);
        buttonUpdateAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAppointmentInDatabase();
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
            Toast.makeText(this, "Error loading data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void updateAppointmentInDatabase() {
        String reason = editTextReason.getText().toString().trim();
        String date = editTextDate.getText().toString().trim();
        String time = editTextTime.getText().toString().trim();

        Patient selectedPatient = (Patient) spinnerPatient.getSelectedItem();
        Doctor selectedDoctor = (Doctor) spinnerDoctor.getSelectedItem();

        if (selectedPatient != null && selectedDoctor != null) { // Correct condition
            int patientId = selectedPatient.getId();
            int doctorId = selectedDoctor.getId();

            boolean result = myDB.updateAppointment(appointmentId, patientId, doctorId, date, time, reason);

            if (result) {
                Toast.makeText(this, "Appointment updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to update appointment", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please select a patient and a doctor", Toast.LENGTH_SHORT).show(); // Handle case where patient or doctor is not selected
        }
    }
}