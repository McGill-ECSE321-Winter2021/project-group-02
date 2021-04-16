package ca.mcgill.ecse321.android_scrs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TimeslotAdapter extends RecyclerView.Adapter<TimeslotAdapter.ViewHolder>
{
    private List<Timeslot> timeslots;
    private boolean isInteractive;
    private Button selectedButton = null;

    public TimeslotAdapter(List<Timeslot> timeslots)
    {
        this.timeslots = timeslots;
    }

    public TimeslotAdapter(List<Timeslot> timeslots, boolean isInteractive)
    {
        this(timeslots);
        this.isInteractive = isInteractive;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View timeslotView;
        if (isInteractive)
        {
            timeslotView = inflater.inflate(R.layout.interactive_timeslot_layout, parent, false);
        } else
        {
            timeslotView = inflater.inflate(R.layout.timeslot_layout, parent, false);
        }

        return new ViewHolder(timeslotView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Timeslot timeslot = timeslots.get(position);

        if (isInteractive)
        {
            Button button = holder.timeslotButton;
            button.setText(timeslot.toString());
            button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if (selectedButton != null)
                    {
                        selectedButton.setEnabled(true);
                    }
                    Variables.timeslotId = timeslot.getTimeslotId();
                    button.setEnabled(false);
                    selectedButton = button;
                }
            });
        } else
        {
            TextView textView = holder.timeslotTextView;
            textView.setText(timeslot.toString());
        }

    }

    @Override
    public int getItemCount()
    {
        return timeslots.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView timeslotTextView;
        public Button timeslotButton;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            if (isInteractive)
            {
                timeslotButton = (Button) itemView.findViewById(R.id.timeslot_button);
            } else
            {
                timeslotTextView = (TextView) itemView.findViewById(R.id.timeslot_entry);
            }
        }
    }
}
