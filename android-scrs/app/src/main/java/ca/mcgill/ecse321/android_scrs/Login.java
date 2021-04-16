package ca.mcgill.ecse321.android_scrs;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class Login extends Fragment
{

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    )
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.login, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        //querying text
        TextView error_warning = view.findViewById(R.id.error_warning_login);
        TextView email_input = view.findViewById(R.id.login_email);
        TextView password_input = view.findViewById(R.id.login_password);

        //adding change event listeners
        TextView[] inputs = new TextView[]{email_input, password_input};

        for (TextView input : inputs)
        {
            input.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    removeWarning(view);
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });
        }

        // back button handling
        view.findViewById(R.id.button_back3).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (Variables.userType.equals("customer"))
                {
                    NavHostFragment.findNavController(Login.this)
                            .navigate(R.id.action_login_to_login_Signup);
                } else
                {
                    Variables.userType = null;
                    NavHostFragment.findNavController(Login.this)
                            .navigate(R.id.action_login_to_mainpage);
                }

            }
        });

        // login button handling
        view.findViewById(R.id.button_submit_login).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                String email = email_input.getText().toString();
                String password = password_input.getText().toString();

                RequestParams params = new RequestParams();
                params.put("email", email);
                params.put("password", password);

                AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
                    {
                        int response = Integer.parseInt(new String(responseBody));
                        if (response != -1 && response != 0)
                        {
                            Variables.userID = response;
                            NavHostFragment.findNavController(Login.this)
                                    .navigate(R.id.action_login_to_dashboard);
                        } else
                        {
                            error_warning.setText(R.string.err_login_failed);
                            error_warning.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
                    {
                        error_warning.setText(R.string.err_unknown_error);
                        error_warning.setVisibility(View.VISIBLE);
                    }
                };
                Variables.client.post(Login.super.getContext(), Variables.getAbsoluteUrl("/api/login/" + Variables.userType), params, responseHandler);

            }
        });
    }

    // helper function
    public void removeWarning(@NonNull View view)
    {
        view.findViewById(R.id.error_warning_login).setVisibility(View.INVISIBLE);
    }
}
