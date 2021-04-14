package ca.mcgill.ecse321.android_scrs;

import android.annotation.SuppressLint;
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

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class Signup extends Fragment
{

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    )
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.signup, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        // view querying
        TextView error_warning = view.findViewById(R.id.error_warning_signup);
        TextView name_input = view.findViewById(R.id.signup_name);
        TextView email_input = view.findViewById(R.id.signup_email);
        TextView tel_input = view.findViewById(R.id.signup_tel);
        TextView password_input = view.findViewById(R.id.signup_password);
        TextView repeat_password_input = view.findViewById(R.id.signup_repeat_password);

        // adding change listeners
        TextView[] inputs = new TextView[]{name_input, email_input, tel_input, password_input, repeat_password_input};

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

        // back click handler
        view.findViewById(R.id.button_back2).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (Variables.userType.equals("customer"))
                {
                    NavHostFragment.findNavController(Signup.this)
                            .navigate(R.id.action_signup_to_login_Signup);
                } else
                {
                    Variables.userType = null;
                    NavHostFragment.findNavController(Signup.this)
                            .navigate(R.id.action_login_Signup_to_mainpage);
                }

            }
        });

        // signup click handler
        view.findViewById(R.id.button_submit_signup).setOnClickListener(new View.OnClickListener()
        {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view)
            {
                String name = name_input.getText().toString();
                String email = email_input.getText().toString();
                String tel = tel_input.getText().toString();
                String password = password_input.getText().toString();
                String passwordRepeat = repeat_password_input.getText().toString();

                //if passwords do not match
                if (password.trim().length() != 0 && !password.equals(passwordRepeat))
                {
                    error_warning.setText(R.string.err_non_matching_passwords);
                    error_warning.setVisibility(View.VISIBLE);
                    return;
                }

                // check for empty inputs
                if (name.trim().length() == 0 || email.trim().length() == 0 || tel.trim().length() == 0)
                {
                    error_warning.setText(R.string.err_missing_values);
                    error_warning.setVisibility(View.VISIBLE);
                    return;
                }


                // putting everything in a json object
                StringEntity params = null;
                JSONObject json = new JSONObject();
                try
                {
                    json.put("name", name);
                    json.put("email", email);
                    json.put("phone", tel);
                    json.put("password", password);
                    params = new StringEntity(json.toString());
                } catch (Exception e)
                {
                    System.out.println(e);
                }

                AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
                    {
                        NavHostFragment.findNavController(Signup.this).navigate(R.id.action_signup_to_login);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
                    {
                        error_warning.setText(R.string.err_unknown_error);
                        error_warning.setVisibility(View.VISIBLE);
                    }
                };
                Variables.client.post(Signup.super.getContext(), Variables.getAbsoluteUrl("/api/customer/create"), params, "application/json", responseHandler);
            }
        });
    }

    //helper function
    public void removeWarning(@NonNull View view)
    {
        view.findViewById(R.id.error_warning_signup).setVisibility(View.INVISIBLE);
    }
}
