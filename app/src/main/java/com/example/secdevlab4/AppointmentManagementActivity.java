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

        add_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                Intent intent = new Intent(context, AddAppointmentActivity.class);
                startActivity(intent);
            }
        });

        appointmentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Appointment selectedAppointment = (Appointment) customAdapter.getItem(position);
                Intent intent = new Intent(AppointmentManagementActivity.this, UpdateAppointmentActivity.class);
                intent.putExtra("appointmentId", selectedAppointment.getId());
                intent.putExtra("patientId", selectedAppointment.getPatientId());
                intent.putExtra("doctorId", selectedAppointment.getDoctorId());
                intent.putExtra("date", selectedAppointment.getDate());
                intent.putExtra("time", selectedAppointment.getTime());
                intent.putExtra("reason", selectedAppointment.getReason());
                startActivity(intent);
            }
        });

        appointmentList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Appointment selectedAppointment = (Appointment) customAdapter.getItem(position);
                new AlertDialog.Builder(AppointmentManagementActivity.this)
                        .setTitle("Delete Appointment")
                        .setMessage("Are you sure you want to delete this appointment?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
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
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAppointments();
    }

    private void loadAppointments() {
        List<Appointment> appointments = myDB.getAppointments();
        customAdapter = new AppointmentCustomAdapter(this, appointments);
        appointmentList.setAdapter(customAdapter);
    }
}