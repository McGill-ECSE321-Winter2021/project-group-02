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

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class BookAppointment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.book_appointment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        // Inputs to query
        TextView apptMsg = view.findViewById(R.id.bookAppt_message_editText);
        TextView errorMsg = view.findViewById(R.id.bookAppt_error_text);

        Spinner serviceSpinner = (Spinner) view.findViewById(R.id.bookAppt_service_spinner);
        serviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        // TODO Handling book button press
        view.findViewById(R.id.bookAppt_book_button).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                StringEntity params = null;
                JSONObject json = new JSONObject();
                try
                {
                    // TODO
                    json.put("note", apptMsg.getText().toString().trim());
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

                Variables.client.put(BookAppointment.super.getContext(),  Variables.getAbsoluteUrl("/api/appointment/book"), params, "application/json", resHandler);
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
