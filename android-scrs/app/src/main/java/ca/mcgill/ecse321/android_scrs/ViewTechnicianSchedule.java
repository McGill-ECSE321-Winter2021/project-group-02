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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class ViewTechnicianSchedule extends Fragment
{
    List<Timeslot> timeslots;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    )
    {
        timeslots = new ArrayList<Timeslot>();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.view_technician_schedule, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.view_technician_schedule_back).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                NavHostFragment.findNavController(ViewTechnicianSchedule.this).navigate(R.id.action_viewTechnicianSchedule_to_dashboard);
            }
        });

        AsyncHttpResponseHandler workspaceResponseHandler = new AsyncHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
            {
                final String response = new String(responseBody);
                try
                {
                    final JSONArray jWorkspaceList = new JSONArray(response);

                    for (int i = 0; i < jWorkspaceList.length(); ++i)
                    {
                        final JSONObject jworkspace = jWorkspaceList.getJSONObject(i);
                        final int workspaceId = jworkspace.getInt("workspaceId");
                        for (Timeslot timeslot : timeslots)
                        {
                            if (workspaceId == timeslot.getWorkspaceId())
                            {
                                timeslot.setWorkspaceName(jworkspace.getString("spaceName"));
                            }
                        }
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }

                // Hook the adapter into the RecyclerView
                RecyclerView timeslotView = (RecyclerView) view.findViewById(R.id.view_technician_schedule_view);
                TimeslotAdapter adapter = new TimeslotAdapter(timeslots);
                timeslotView.setAdapter(adapter);
                timeslotView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
            {
                System.out.println(statusCode);
            }
        };

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
                        final String startDate = jTimeslot.getString("startDate");
                        final String endDate = jTimeslot.getString("endDate");
                        final String startTime = jTimeslot.getString("startTime");
                        final String endTime = jTimeslot.getString("endTime");
                        final int workspaceId = jTimeslot.getInt("workspaceId");
                        final int timeslotId = jTimeslot.getInt("timeslotId");

                        timeslots.add(new Timeslot(timeslotId, startDate, endDate, startTime, endTime, workspaceId));
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }

                // Send another request to get all the workspaces
                String url = Variables.getAbsoluteUrl("/api/workspace/getall");
                Variables.client.get(ViewTechnicianSchedule.super.getContext(), url, null, "application/json", workspaceResponseHandler);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
            {
                System.out.println(statusCode);
            }
        };

        // Get the current date in the format we need for the api
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        Date date = Calendar.getInstance().getTime();
        final Calendar cal = Calendar.getInstance();

        final String nowString = dateFormat.format(date);

        cal.add(Calendar.DATE, 1);
        date = cal.getTime();

        final String tomorrowString = dateFormat.format(date);
        // Send the get request for the schedule
        String url = Variables.getAbsoluteUrl(String.format(Locale.getDefault(), "/api/technician/viewschedule/%d/%s/%s", Variables.userID, nowString, tomorrowString));
        Variables.client.get(ViewTechnicianSchedule.super.getContext(), url, null, "application/json", timeslotResponseHandler);
    }
}
