package ca.mcgill.ecse321.android_scrs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class Dashboard extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dashboard, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (Variables.userType.equals("technician"))
        {
            view.findViewById(R.id.dash_book_appt_button).setVisibility(View.GONE);
            view.findViewById(R.id.dash_view_appts_button).setVisibility(View.GONE);
            view.findViewById(R.id.dash_view_sched_button).setVisibility(View.VISIBLE);
        } else // must be a customer, hide the technician schedule button
        {
            view.findViewById(R.id.dash_book_appt_button).setVisibility(View.VISIBLE);
            view.findViewById(R.id.dash_view_appts_button).setVisibility(View.VISIBLE);
            view.findViewById(R.id.dash_view_sched_button).setVisibility(View.GONE);
        }

        // book new appointment button
        view.findViewById(R.id.dash_book_appt_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(Variables.userType.equals("customer")){
//                    NavHostFragment.findNavController(Dashboard.this).navigate(); TODO ADD ACTION WHEN BUTTON IS MAPPED
//                } else{
//                    Variables.userType = null;
//                    Variables.userID = -1;
//                    NavHostFragment.findNavController(Dashboard.this)
//                            .navigate(R.id.action_dashboard_to_mainpage);
//                }

            }
        });

        // view booked appointments button
        view.findViewById(R.id.dash_view_appts_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Variables.userType.equals("customer")){
                    NavHostFragment.findNavController(Dashboard.this).navigate(R.id.action_dashboard_to_viewAppointments);
                } else{
                    Variables.userType = null;
                    Variables.userID = -1;
                    NavHostFragment.findNavController(Dashboard.this)
                            .navigate(R.id.action_dashboard_to_mainpage);
                }

            }
        });

        // view technician schedule button
        view.findViewById(R.id.dash_view_sched_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Variables.userType.equals("technician")){
                    NavHostFragment.findNavController(Dashboard.this).navigate(R.id.action_dashboard_to_viewTechnicianSchedule);
                } else{
                    Variables.userType = null;
                    Variables.userID = -1;
                    NavHostFragment.findNavController(Dashboard.this)
                            .navigate(R.id.action_dashboard_to_mainpage);
                }

            }
        });

        // update account info button
        view.findViewById(R.id.dash_modify_account_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Dashboard.this).navigate(R.id.action_dashboard_to_modifyAccount);
            }
        });

        // log out button
        view.findViewById(R.id.dash_logout_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Variables.userType = null;
                Variables.userID = -1;
                NavHostFragment.findNavController(Dashboard.this).navigate(R.id.action_dashboard_to_mainpage);
            }
        });

    }
}
