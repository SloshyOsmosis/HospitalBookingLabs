package com.example.secdevlab4;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class DoctorManagementActivity extends AppCompatActivity {
    ListView doctorList;
    FloatingActionButton add_doctor;
    DBHelper myDB;
    DoctorCustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_management);

        doctorList = findViewById(R.id.doctorList);
        add_doctor = findViewById(R.id.add_doctor);

        // Navigate to AddDoctorActivity when clicked
        add_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                Intent intent = new Intent(context, AddDoctorActivity.class);
                startActivity(intent);
            }
        });

        myDB = new DBHelper(this);

        doctorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            // Navigate to UpdateDoctorActivity when clicked
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Doctor selectedDoctor = (Doctor) customAdapter.getItem(position);

                Intent intent = new Intent(DoctorManagementActivity.this, UpdateDoctorActivity.class);
                // Pass doctor details to UpdateDoctorActivity
                intent.putExtra("doctorId", selectedDoctor.getId());
                intent.putExtra("firstName", selectedDoctor.getFName());
                intent.putExtra("lastName", selectedDoctor.getLName());
                intent.putExtra("specialization", selectedDoctor.getSpecialty());
                startActivity(intent);
            }
        });

        doctorList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Selected doctor is chosen
                Doctor selectedDoctor = (Doctor) customAdapter.getItem(position);

                // Show a confirmation dialog before deleting
                new AlertDialog.Builder(DoctorManagementActivity.this)
                        .setTitle("Delete Doctor")
                        .setMessage("Are you sure you want to delete this doctor?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            // Delete the selected doctor if confirmed
                            public void onClick(DialogInterface dialog, int which) {
                                boolean result = myDB.deleteDoctor(selectedDoctor.getId());
                                if (result) {
                                    Toast.makeText(DoctorManagementActivity.this, "Doctor deleted successfully", Toast.LENGTH_SHORT).show();
                                    loadDoctor();
                                } else {
                                    Toast.makeText(DoctorManagementActivity.this, "Failed to delete doctor", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        // Cancel the deletion if not confirmed
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });
    }
    // Load doctors when the activity is resumed
    @Override
    protected void onResume() {
        super.onResume();
        loadDoctor();
    }

    // Helper method to load doctors from the database
    private void loadDoctor() {
        // Get doctors from the database
        List<Doctor> doctors = myDB.getDoctors();
        customAdapter = new DoctorCustomAdapter(this, doctors);
        doctorList.setAdapter(customAdapter);
    }
}