package ca.mcgill.ecse321.android_scrs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ViewAppointments extends Fragment {

    List<Appointment> appointments;
    List<Timeslot> timeslots;
    int appointmentId;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        appointments=new ArrayList<Appointment>();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.view_appointments, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_back_view_appointments).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ViewAppointments.this).navigate(R.id.action_ViewAppointments_to_dashboard);
            }
        });

        AsyncHttpResponseHandler appointmentResponseHandler = new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                final String response = new String(responseBody);
                try {
                    final JSONArray jAppointmentList = new JSONArray(response);

                    for (int i = 0; i < jAppointmentList.length(); ++i) {

                        final JSONObject jAppointment = jAppointmentList.getJSONObject(i);

                        appointmentId = jAppointment.getInt("appointmentId");
                        final String appointmentType = jAppointment.getString("appointmentType");

                        AsyncHttpResponseHandler timeslotResponseHandler = new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                final String response = new String(responseBody);
                                try {
                                    final JSONArray jTimeslotList = new JSONArray(response);
                                    for (int i = 0; i < jTimeslotList.length(); ++i) {
                                        final JSONObject jTimeslot = jTimeslotList.getJSONObject(i);
                                        final String startDate = jTimeslot.getString("startDate");
                                        final String endDate = jTimeslot.getString("endDate");
                                        final String startTime = jTimeslot.getString("startTime");
                                        final String endTime = jTimeslot.getString("endTime");

                                        appointments.add(new Appointment(appointmentType,startDate, endDate, startTime, endTime,appointmentId));

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                // Send another request to get all the workspaces
                                String url = Variables.getAbsoluteUrl("/api/appointment/getStartAndEnd/"+ appointmentId);
                                Variables.client.get(ViewAppointments.super.getContext(), url, null, "application/json", timeslotResponseHandler);
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
                            {
                                System.out.println(statusCode);
                            }
                        };

//                        appointment/getall/${this.$store.state.user}
                        String url2 = Variables.getAbsoluteUrl("/api/appointment/getall/" + Variables.userID);
                        Variables.client.get(ViewAppointments.super.getContext(), url2, null, "application/json", appointmentResponseHandler);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RecyclerView appointmentView = view.findViewById(R.id.view_appointments_view);
                AppointmentAdapter adapter = new AppointmentAdapter(appointments);
                appointmentView.setAdapter(adapter);
                appointmentView.setLayoutManager(new LinearLayoutManager(view.getContext()));

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
            {
                System.out.println(statusCode);
            }

        };


        // Back Button Handling
        view.findViewById(R.id.button_back_view_appointments).setOnClickListener(view1 -> {
            if (Variables.userType.equals("customer")) {
                NavHostFragment.findNavController(ViewAppointments.this)
                        .navigate(R.id.action_ViewAppointments_to_dashboard);
            }

        });


    }


    }

