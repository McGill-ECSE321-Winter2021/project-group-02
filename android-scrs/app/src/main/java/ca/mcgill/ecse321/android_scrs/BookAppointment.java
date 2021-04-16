package ca.mcgill.ecse321.android_scrs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class BookAppointment extends Fragment
{
    private String appointmentType = "";
    private List<Timeslot> timeslots = new ArrayList<Timeslot>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Variables.timeslotId = -1;
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.book_appointment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        // Inputs to query
        Spinner serviceSpinner = (Spinner) view.findViewById(R.id.bookAppt_service_spinner);
        TextView serviceText = view.findViewById(R.id.bookAppt_service_editText);
        TextView apptMsg = view.findViewById(R.id.bookAppt_message_editText);
        TextView errorMsg = view.findViewById(R.id.bookAppt_error_text);

        // Handling dropdown selection
        serviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedService = parent.getItemAtPosition(position).toString();
                switch (selectedService)
                {
                    case "Choose service":
                        appointmentType = "";
                        serviceText.setVisibility(View.INVISIBLE);
                        break;
                    case "Other":
                        appointmentType = "Other";
                        serviceText.setVisibility(View.VISIBLE);
                        errorMsg.setVisibility(View.INVISIBLE);
                        break;
                    default:
                        appointmentType = selectedService.replaceAll("\\s", "");
                        serviceText.setVisibility(View.INVISIBLE);
                        errorMsg.setVisibility(View.INVISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                serviceSpinner.setSelection(0);
                appointmentType = "";
                serviceText.setVisibility(View.INVISIBLE);
            }
        });

        // Fetching available timeslots
        AsyncHttpResponseHandler timeslotResponseHandler = new AsyncHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
            {
                final String response = new String(responseBody);
                try
                {
                    final JSONArray jTimeslotList = new JSONArray(response);
                    for (int i = 0; i < jTimeslotList.length(); ++i)
                    {
                        final JSONObject jTimeslot = jTimeslotList.getJSONObject(i);
                        final int timeslotId = jTimeslot.getInt("timeslotId");
                        final String startDate = jTimeslot.getString("startDate");
                        final String endDate = jTimeslot.getString("endDate");
                        final String startTime = jTimeslot.getString("startTime");
                        final String endTime = jTimeslot.getString("endTime");
                        final int workspaceId = jTimeslot.getInt("workspaceId");

                        timeslots.add(new Timeslot(timeslotId, startDate, endDate, startTime, endTime, workspaceId));
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

                RecyclerView timeslotView = (RecyclerView) view.findViewById(R.id.bookAppt_timeslot_view);
                TimeslotAdapter adapter = new TimeslotAdapter(timeslots, true);
                timeslotView.setAdapter(adapter);
                timeslotView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
            {
                System.out.println(statusCode);
            }
        };

        // Get the current date in the format we need for the timeslot api
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = Calendar.getInstance().getTime();
        final String today = dateFormat.format(date);
        // Send the get request for the availability
        String url = Variables.getAbsoluteUrl("/api/timeslot/available/" + today);
        Variables.client.get(BookAppointment.super.getContext(), url, null, "application/json", timeslotResponseHandler);

        // Handling book button press
        view.findViewById(R.id.bookAppt_book_button).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // check for unselected service
                if (appointmentType == "")
                {
                    errorMsg.setText(R.string.err_service_select);
                    errorMsg.setVisibility(View.VISIBLE);
                    return;
                }
                // check for other service
                if (appointmentType == "Other" && serviceText.getText().toString().trim().length() == 0)
                {
                    errorMsg.setText(R.string.empty_service);
                    errorMsg.setVisibility(View.VISIBLE);
                    return;
                }
                // check for unselected timeslot
                if (Variables.timeslotId == -1) {
                    errorMsg.setText(R.string.unselected_timeslot);
                    errorMsg.setVisibility(View.VISIBLE);
                    return;
                }

                StringEntity params = null;
                JSONObject json = new JSONObject();
                try
                {
                    json.put("appointmentType", appointmentType);
                    json.put("service", serviceText.getText().toString().trim());
                    json.put("note", apptMsg.getText().toString().trim());
                    json.put("customerId", Variables.userID);
                    JSONArray jTimeslotArray = new JSONArray();
                    jTimeslotArray.put(Variables.timeslotId);
                    json.put("timeslotsId", jTimeslotArray);
                    params = new StringEntity(json.toString());
                } catch (JSONException | UnsupportedEncodingException e)
                {
                    errorMsg.setText(R.string.err_unknown_error);
                    errorMsg.setVisibility(View.VISIBLE);
                    System.out.println(e.getMessage());
                }

                AsyncHttpResponseHandler resHandler = new AsyncHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
                    {
                        NavHostFragment.findNavController(BookAppointment.this).navigate(R.id.action_bookAppointment_to_dashboard);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
                    {
                        errorMsg.setText(R.string.err_unknown_error);
                        System.out.println(statusCode);
                        errorMsg.setVisibility(View.VISIBLE);
                    }
                };

                Variables.client.post(BookAppointment.super.getContext(), Variables.getAbsoluteUrl("/api/appointment/book"), params, "application/json", resHandler);
            }
        });

        // Handling back button press
        view.findViewById(R.id.bookAppt_back_button).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                NavHostFragment.findNavController(BookAppointment.this).navigate(R.id.action_bookAppointment_to_dashboard);
            }
        });
    }
}
