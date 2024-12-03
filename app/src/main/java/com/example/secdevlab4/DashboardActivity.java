package com.example.secdevlab4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    Button drBtn, patientBtn, appointmentBtn, logoutBtn;
    TextView greetingsTextView;
    DBHelper myDB;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        drBtn = findViewById(R.id.drBtn);
        patientBtn = findViewById(R.id.patientBtn);
        appointmentBtn = findViewById(R.id.appointmentBtn);
        logoutBtn = findViewById(R.id.logoutBtn);
        greetingsTextView = findViewById(R.id.greetingsTextView);

        myDB = new DBHelper(this);

        userId = getIntent().getIntExtra("userId", -1);

        if (userId != -1) {
            String userName = myDB.getUserName(userId); // Fetch user's name
            greetingsTextView.setText("Welcome, " + userName + "!");
        }

        drBtn.setOnClickListener(v -> startActivity(new Intent(this, DoctorManagementActivity.class)));

        patientBtn.setOnClickListener(v -> startActivity(new Intent(this, PatientManagementActivity.class)));

        appointmentBtn.setOnClickListener(v -> startActivity(new Intent(this, AppointmentManagementActivity.class)));

        logoutBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            Toast.makeText(this, "User successfully logged out.", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}