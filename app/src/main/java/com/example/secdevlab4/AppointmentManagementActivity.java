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

public class AppointmentManagementActivity extends AppCompatActivity {
    ListView appointmentList;
    FloatingActionButton add_appointment;
    DBHelper myDB;
    AppointmentCustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        appointmentList = findViewById(R.id.appointmentList);
        add_appointment = findViewById(R.id.add_appointment);

        myDB = new DBHelper(this);

        // Navigate to AddAppointmentActivity when the add_appointment button is clicked
        add_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                Intent intent = new Intent(context, AddAppointmentActivity.class);
                startActivity(intent);
            }
        });

        // Load appointments when the activity is created
        appointmentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Navigate to UpdateAppointmentActivity when an appointment is clicked
                Appointment selectedAppointment = (Appointment) customAdapter.getItem(position);
                Intent intent = new Intent(AppointmentManagementActivity.this, UpdateAppointmentActivity.class);
                // Pass appointment details to UpdateAppointmentActivity
                intent.putExtra("appointmentId", selectedAppointment.getId());
                intent.putExtra("patientId", selectedAppointment.getPatientId());
                intent.putExtra("doctorId", selectedAppointment.getDoctorId());
                intent.putExtra("date", selectedAppointment.getDate());
                intent.putExtra("time", selectedAppointment.getTime());
                intent.putExtra("reason", selectedAppointment.getReason());
                startActivity(intent);
            }
        });

        // Handle long-press on an appointment to delete it
        appointmentList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Appointment selectedAppointment = (Appointment) customAdapter.getItem(position);
                // Show a confirmation dialog before deleting the appointment
                new AlertDialog.Builder(AppointmentManagementActivity.this)
                        .setTitle("Delete Appointment")
                        .setMessage("Are you sure you want to delete this appointment?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            // Delete the selected appointment if confirmed
                            public void onClick(DialogInterface dialog, int which) {
                                boolean result = myDB.deleteAppointment(selectedAppointment.getId());
                                if (result) {
                                    Toast.makeText(AppointmentManagementActivity.this, "Appointment deleted successfully", Toast.LENGTH_SHORT).show();
                                    loadAppointments();
                                    } else {
                                    Toast.makeText(AppointmentManagementActivity.this, "Failed to delete appointment", Toast.LENGTH_SHORT).show();
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

    // Load appointments when the activity is resumed
    @Override
    protected void onResume() {
        super.onResume();
        loadAppointments();
    }

    // Helper method to load appointments from the database
    private void loadAppointments() {
        // Get appointments from the database
        List<Appointment> appointments = myDB.getAppointments();
        customAdapter = new AppointmentCustomAdapter(this, appointments);
        appointmentList.setAdapter(customAdapter);
    }
}