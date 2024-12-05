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

public class PatientManagementActivity extends AppCompatActivity {

    ListView patientList;
    FloatingActionButton add_patient;
    DBHelper myDB;

    PatientCustomAdapter patientCustomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_management);

        patientList = findViewById(R.id.patientList);
        add_patient = findViewById(R.id.add_patient);

        // Takes the user to the add patient page
        add_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                Intent intent = new Intent(context, AddPatientActivity.class);
                startActivity(intent);
            }
        });

        myDB = new DBHelper(this);

        // Loads the list of patients
        patientList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Patient selectedPatient = (Patient) patientCustomAdapter.getItem(position);

                Intent intent = new Intent(PatientManagementActivity.this, UpdatePatientActivity.class);
                // Passes the patient's details to the update patient page
                intent.putExtra("patientId", selectedPatient.getId());
                intent.putExtra("firstName", selectedPatient.getFName());
                intent.putExtra("lastName", selectedPatient.getLName());
                intent.putExtra("age", selectedPatient.getAge());
                intent.putExtra("gender", selectedPatient.getGender());
                startActivity(intent);
            }
        });

        // Deletes the selected patient on a hold press
        patientList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Selected patient is chosen
                Patient selectedPatient = (Patient) patientCustomAdapter.getItem(position);

                // Shows a confirmation dialog before deleting the patient
                new AlertDialog.Builder(PatientManagementActivity.this)
                        .setTitle("Delete Patient")
                        .setMessage("Are you sure you want to delete this patient?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            // Deletes the patient if user presses 'Yes'
                            public void onClick(DialogInterface dialog, int which) {
                                // Deletes the patient from the database
                                boolean result = myDB.deletePatient(selectedPatient.getId());
                                if (result) {
                                    // Shows a success message if the patient is deleted
                                    Toast.makeText(PatientManagementActivity.this, "Patient deleted successfully", Toast.LENGTH_SHORT).show();
                                    loadPatients();
                                } else {
                                    // Shows an error message if the patient is not deleted
                                    Toast.makeText(PatientManagementActivity.this, "Failed to delete patient", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        // If user presses 'No', the dialog is dismissed
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });
    }

    // Loads the list of patients
    @Override
    protected void onResume() {
        super.onResume();
        loadPatients();
    }

    // Loads the list of patients
    private void loadPatients() {
        // Gets the list of patients from the database
        List<Patient> patients = myDB.getPatients();
        // Creates a custom adapter for the patient list
        patientCustomAdapter = new PatientCustomAdapter(this, patients);
        patientList.setAdapter(patientCustomAdapter);
    }
}