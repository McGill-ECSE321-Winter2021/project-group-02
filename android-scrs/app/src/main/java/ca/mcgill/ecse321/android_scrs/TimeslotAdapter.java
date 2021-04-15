package ca.mcgill.ecse321.android_scrs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TimeslotAdapter extends RecyclerView.Adapter<TimeslotAdapter.ViewHolder>
{
    private List<Timeslot> timeslots;

    public TimeslotAdapter(List<Timeslot> timeslots)
    {
        this.timeslots = timeslots;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View timeslotView = inflater.inflate(R.layout.timeslot_layout, parent, false);

        return new ViewHolder(timeslotView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Timeslot timeslot = timeslots.get(position);

        TextView textView = holder.timeslotTextView;
        textView.setText(timeslot.toString());
    }

    @Override
    public int getItemCount() {
        return timeslots.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView timeslotTextView;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            timeslotTextView = (TextView)itemView.findViewById(R.id.timeslot_entry);
        }
    }
}
