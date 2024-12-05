package com.example.secdevlab4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class DoctorCustomAdapter extends BaseAdapter {

    private Context context;
    private List<Doctor> doctorList;

    public DoctorCustomAdapter(Context context, List<Doctor> doctorList) {
        this.context = context;
        this.doctorList = doctorList;
    }

    @Override
    // Get the number of doctors
    public int getCount() {
        return doctorList.size();
    }

    @Override
    // Get the doctor at the given position
    public Object getItem(int position) {
        return doctorList.get(position);
    }

    @Override
    // Get the ID of the doctor
    public long getItemId(int position) {
        return doctorList.get(position).getId();
    }

    @Override
    // Create a new view for each item in the list
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.doctor_item, parent, false);
        }

        TextView idView = convertView.findViewById(R.id.doctorId);
        TextView nameView = convertView.findViewById(R.id.doctorName);
        TextView specialtyView = convertView.findViewById(R.id.doctorSpecialty);

        Doctor doctor = doctorList.get(position);

        idView.setText("ID: " + doctor.getId());
        nameView.setText("Dr. " + doctor.getFName() + " " + doctor.getLName());
        specialtyView.setText("Specialty: " + doctor.getSpecialty());

        return convertView;
    }
}