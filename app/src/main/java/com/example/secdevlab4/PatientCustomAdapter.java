package com.example.secdevlab4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class PatientCustomAdapter extends BaseAdapter {

    private Context context;
    private List<Patient> patientList;

    public PatientCustomAdapter(Context context, List<Patient> patientList) {
        this.context = context;
        this.patientList = patientList;
    }

    @Override
    // Returns the number of patients in the list
    public int getCount() {
        return patientList.size();
    }

    @Override
    // Returns the patient at the given position
    public Object getItem(int position) {
        return patientList.get(position);
    }

    @Override
    // Returns the ID of the patient
    public long getItemId(int position) {
        return patientList.get(position).getId();
    }

    @Override
    // Creates a custom view for each patient in the list
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflates the custom layout for each patient
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.patient_item, parent, false);
        }

        // Finds the views in the custom layout
        TextView idView = convertView.findViewById(R.id.patientId);
        TextView nameView = convertView.findViewById(R.id.patientName);
        TextView ageView = convertView.findViewById(R.id.patientAge);
        TextView genderView = convertView.findViewById(R.id.patientGender);

        Patient patient = patientList.get(position);

        // Sets the text for each view
        idView.setText("ID: " + patient.getId());
        nameView.setText(patient.getFName() + " " + patient.getLName());
        ageView.setText("Age: " + patient.getAge());
        genderView.setText("Gender: " + patient.getGender());

        // Returns the custom view for the patient
        return convertView;
    }
}