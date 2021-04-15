package ca.mcgill.ecse321.android_scrs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class ViewAppointments extends Fragment {

    List<Timeslot> timeslots;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.view_appointments, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

            view.findViewById(R.id.view_technician_schedule_back).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    NavHostFragment.findNavController(ViewAppointments.this).navigate(R.id.action_viewTechnicianSchedule_to_dashboard);
                }
            });

            timeslots = new ArrayList<Timeslot>();
            timeslots.add(new Timeslot("StartingTime", "Endtime", "", ""));
            timeslots.add(new Timeslot("StartingTime", "Endtime", "", ""));
            timeslots.add(new Timeslot("StartingTime", "Endtime", "", ""));
            timeslots.add(new Timeslot("StartingTime", "Endtime", "", ""));
            timeslots.add(new Timeslot("StartingTime", "Endtime", "", ""));
            timeslots.add(new Timeslot("StartingTime", "Endtime", "", ""));
            timeslots.add(new Timeslot("StartingTime", "Endtime", "", ""));
            timeslots.add(new Timeslot("StartingTime", "Endtime", "", ""));

            RecyclerView timeslotView = (RecyclerView)view.findViewById(R.id.view_technician_schedule_view);
            TimeslotAdapter adapter = new TimeslotAdapter(timeslots);
            timeslotView.setAdapter(adapter);
            timeslotView.setLayoutManager(new LinearLayoutManager(view.getContext()));


        // Back Button Handling
        view.findViewById(R.id.button_back_view_appointments).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Variables.userType.equals("customer")) {
                    NavHostFragment.findNavController(ViewAppointments.this)
                            .navigate(R.id.action_login_to_login_Signup); //?????????????????
                } else {
                    Variables.userType = null;
                    NavHostFragment.findNavController(ViewAppointments.this)
                            .navigate(R.id.action_login_to_mainpage); //?????????
                }

            }
        });

    }
}
