package com.example.secdevlab4;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class PatientManagementActivity extends AppCompatActivity {

    ListView patientList;
    FloatingActionButton add_patient;
    DBHelper myDB;

    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_management);

        patientList = findViewById(R.id.patientList);
        add_patient = findViewById(R.id.add_patient);

        add_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                Intent intent = new Intent(context, AddPatientActivity.class);
                startActivity(intent);
            }
        });

        myDB = new DBHelper(this);

        patientList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Patient selectedPatient = (Patient) customAdapter.getItem(position);

                Intent intent = new Intent(PatientManagementActivity.this, UpdatePatientActivity.class);
                intent.putExtra("patientId", selectedPatient.getId());
                intent.putExtra("firstName", selectedPatient.getFName());
                intent.putExtra("lastName", selectedPatient.getLName());
                intent.putExtra("age", selectedPatient.getAge());
                intent.putExtra("gender", selectedPatient.getGender());
                startActivity(intent);
            }
        });

        patientList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Selected patient is chosen
                Patient selectedPatient = (Patient) customAdapter.getItem(position);

                new AlertDialog.Builder(PatientManagementActivity.this)
                        .setTitle("Delete Patient")
                        .setMessage("Are you sure you want to delete this patient?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                boolean result = myDB.deletePatient(selectedPatient.getId());
                                if (result) {
                                    Toast.makeText(PatientManagementActivity.this, "Patient deleted successfully", Toast.LENGTH_SHORT).show();
                                    loadPatients();
                                } else {
                                    Toast.makeText(PatientManagementActivity.this, "Failed to delete patient", Toast.LENGTH_SHORT).show();
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
        loadPatients();
    }

    private void loadPatients() {
        List<Patient> patients = myDB.getPatients();
        customAdapter = new CustomAdapter(this, patients);
        patientList.setAdapter(customAdapter);
    }
}