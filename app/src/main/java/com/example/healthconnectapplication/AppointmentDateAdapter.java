package com.example.healthconnectapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AppointmentDateAdapter extends RecyclerView.Adapter<AppointmentDateAdapter.AppointmentDateViewHolder> {

    private List<AppointmentDate> appointmentDateList;

    public AppointmentDateAdapter(List<AppointmentDate> appointmentDateList) {
        this.appointmentDateList = appointmentDateList;
    }

    @Override
    public AppointmentDateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointmentdate, parent, false);
        return new AppointmentDateViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AppointmentDateViewHolder holder, int position) {
        AppointmentDate appointmentDate = appointmentDateList.get(position);
        holder.patientIdTextView.setText(appointmentDate.getPatientId());
        holder.firstNameTextView.setText(appointmentDate.getFirstName());
        holder.lastNameTextView.setText(appointmentDate.getLastName());
        holder.appointmentDateTextView.setText(appointmentDate.getAppointmentDate());
    }

    @Override
    public int getItemCount() {
        return appointmentDateList.size();
    }

    public static class AppointmentDateViewHolder extends RecyclerView.ViewHolder {

        TextView patientIdTextView;
        TextView firstNameTextView;
        TextView lastNameTextView;
        TextView appointmentDateTextView;

        public AppointmentDateViewHolder(View itemView) {
            super(itemView);
            patientIdTextView = itemView.findViewById(R.id.patientIdTextView);
            firstNameTextView = itemView.findViewById(R.id.firstNameTextView);
            lastNameTextView = itemView.findViewById(R.id.lastNameTextView);
            appointmentDateTextView = itemView.findViewById(R.id.appointmentDateTextView);
        }
    }
}
