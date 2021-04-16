package ca.mcgill.ecse321.android_scrs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {
    private List<Appointment> appointments;

    public AppointmentAdapter(List<Appointment> appointments)
    {
        this.appointments = appointments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View appointmentView = inflater.inflate(R.layout.appointment_layout, parent, false);

        return new ViewHolder(appointmentView);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter.ViewHolder holder, int position)
    {
        Appointment appointment = appointments.get(position);

        TextView textView = holder.appointmentTextView;
        textView.setText(appointment.toString());
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView appointmentTextView;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            appointmentTextView = (TextView)itemView.findViewById(R.id.appointment_entry);
        }
    }
}
