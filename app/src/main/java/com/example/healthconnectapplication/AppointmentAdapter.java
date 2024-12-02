package com.example.healthconnectapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {

    private List<Appointment> appointments;
    private Context context;

    public AppointmentAdapter(List<Appointment> appointments,Context context) {
        this.appointments = appointments;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for individual appointment items
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_appointment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the appointment at the current position
        Appointment appointment = appointments.get(position);

        // Set the data in the views
        holder.textViewName.setText(appointment.getFirstName() + " " + appointment.getLastName());
        holder.textViewDate.setText(appointment.getAppointmentDate());
        holder.textViewMedication.setText(appointment.getMedication());
        holder.textViewDiagnosis.setText(appointment.getDiagnosis());

        holder.buttonEditRecord.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditRecordActivity.class);
            intent.putExtra("appointmentId", appointment.getAppointmentId());
            intent.putExtra("diagnosis", appointment.getDiagnosis());
            intent.putExtra("treatment", appointment.getTreatment());
            intent.putExtra("medication", appointment.getMedication());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewDate, textViewDiagnosis, textViewTreatment,textViewMedication;

        Button buttonEditRecord;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewDiagnosis = itemView.findViewById(R.id.textViewDiagnosis);
            textViewTreatment = itemView.findViewById(R.id.textViewTreatment);
            textViewMedication = itemView.findViewById(R.id.textViewMedication);
            buttonEditRecord = itemView.findViewById(R.id.buttonEditRecord);
        }
    }
}
