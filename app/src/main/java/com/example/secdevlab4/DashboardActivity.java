package com.example.secdevlab4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DashboardActivity extends AppCompatActivity {

    Button drBtn, patientBtn, appointmentBtn, logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        drBtn = findViewById(R.id.drBtn);
        patientBtn = findViewById(R.id.patientBtn);
        appointmentBtn = findViewById(R.id.appointmentBtn);
        logoutBtn = findViewById(R.id.logoutBtn);

        drBtn.setOnClickListener(v -> startActivity(new Intent(this, DoctorManagementActivity.class)));

        patientBtn.setOnClickListener(v -> startActivity(new Intent(this, PatientManagementActivity.class)));

        appointmentBtn.setOnClickListener(v -> startActivity(new Intent(this, AppointmentActivity.class)));

        logoutBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
}