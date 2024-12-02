package com.example.secdevlab4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private List<Patient> patientList;

    public CustomAdapter(Context context, List<Patient> patientList) {
        this.context = context;
        this.patientList = patientList;
    }

    @Override
    public int getCount() {
        return patientList.size();
    }

    @Override
    public Object getItem(int position) {
        return patientList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return patientList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.patient_item, parent, false);
        }

        TextView idView = convertView.findViewById(R.id.patientId);
        TextView nameView = convertView.findViewById(R.id.patientName);
        TextView ageView = convertView.findViewById(R.id.patientAge);
        TextView genderView = convertView.findViewById(R.id.patientGender);

        Patient patient = patientList.get(position);

        idView.setText("ID: " + patient.getId());
        nameView.setText(patient.getFName() + " " + patient.getLName());
        ageView.setText("Age: " + patient.getAge());
        genderView.setText("Gender: " + patient.getGender());

        return convertView;
    }
}