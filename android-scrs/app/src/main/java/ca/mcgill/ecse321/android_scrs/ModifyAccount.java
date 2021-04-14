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

import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class ModifyAccount extends Fragment
{
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    )
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.modify_account, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        // make sure a user is signed in at this point
        if (Variables.userID == -1)
        {
            NavHostFragment.findNavController(ModifyAccount.this).navigate(R.id.action_modifyAccount_to_dashboard);
        }

        // Inputs to query
        TextView nameField = view.findViewById(R.id.modify_account_name);
        TextView emailField = view.findViewById(R.id.modify_account_email);
        TextView phoneField = view.findViewById(R.id.modify_account_tel);
        TextView password_field = view.findViewById(R.id.modify_account_password);
        TextView repeatPasswordField = view.findViewById(R.id.modify_account_repeat_password);
        TextView modifyError = view.findViewById(R.id.modify_account_error);

        // Populate fields with data
        fetchData(nameField, emailField, phoneField, modifyError);


        // Create iterator for listeners
        TextView[] inputs = {nameField, emailField, phoneField, password_field, repeatPasswordField, modifyError};

        for (TextView input : inputs)
        {
            input.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
                {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
                {
                    hideError(view);
                }

                @Override
                public void afterTextChanged(Editable editable)
                {
                }
            });
        }

        // Handling back button press
        view.findViewById(R.id.button_back_modify_account).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                NavHostFragment.findNavController(ModifyAccount.this).navigate(R.id.action_modifyAccount_to_dashboard);
            }
        });

        // Handling submit button press
        view.findViewById(R.id.modify_account_submit_button).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String name = nameField.getText().toString();
                String email = emailField.getText().toString();
                String tel = phoneField.getText().toString();
                String password = password_field.getText().toString();
                String repeatPass = repeatPasswordField.getText().toString();

                // check password validity if entered
                if (password.trim().length() != 0 && !password.equals(repeatPass))
                {
                    modifyError.setText(R.string.err_non_matching_passwords);
                    modifyError.setVisibility(View.VISIBLE);
                    return;
                }

                // check for empty inputs
                if (name.trim().length() == 0 || email.trim().length() == 0 || tel.trim().length() == 0)
                {
                    modifyError.setText(R.string.err_missing_values);
                    modifyError.setVisibility(View.VISIBLE);
                    return;
                }

                StringEntity params = null;
                JSONObject json = new JSONObject();
                try
                {
                    json.put("name", name);
                    json.put("email", email);
                    json.put("phone", tel);
                    json.put("password", password);
                    json.put("scrsUserId", Variables.userID);
                    params = new StringEntity(json.toString());
                } catch (JSONException | UnsupportedEncodingException e)
                {
                    modifyError.setText(R.string.err_unknown_error);
                    modifyError.setVisibility(View.VISIBLE);
                    System.out.println(e.getMessage());
                }

                AsyncHttpResponseHandler resHandler = new AsyncHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
                    {
                        modifyError.setText(R.string.modify_acc_success);
                        modifyError.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
                    {
                        modifyError.setText(R.string.err_unknown_error);
                        System.out.println(statusCode);
                        modifyError.setVisibility(View.VISIBLE);
                    }
                };

                // handle both possible URL's in one line. Either customer or "not customer" (technician)
                String url = Variables.userType.equals("customer") ? Variables.getAbsoluteUrl("/api/customer/update") : Variables.getAbsoluteUrl("/api/technician/update");
                Variables.client.put(ModifyAccount.super.getContext(), url, params, "application/json", resHandler);
            }
        });
    }

    //Helper function
    public void hideError(@NonNull View view)
    {
        view.findViewById(R.id.modify_account_error).setVisibility(View.INVISIBLE);
    }

    /**
     * Fetches user data from the backend and sets the field values.
     * @param nameField field in which to store the name.
     * @param emailField field in which to store the email.
     * @param phoneField field in which to store the phone number.
     * @param modifyError field in which to display any errors.
     */
    private void fetchData(TextView nameField, TextView emailField, TextView phoneField, TextView modifyError)
    {
        AsyncHttpResponseHandler firstResponseHandler = new AsyncHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
            {
                String response = new String(responseBody);
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    if (Variables.userType.equals("customer"))
                    {
                        nameField.setText(jsonObject.getString("customerName"));
                        emailField.setText(jsonObject.getString("customerEmail"));
                        phoneField.setText(jsonObject.getString("customerPhone"));
                    } else
                    {
                        nameField.setText(jsonObject.getString("technicianName"));
                        emailField.setText(jsonObject.getString("technicianEmail"));
                        phoneField.setText(jsonObject.getString("technicianPhone"));
                    }

                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
            {
                modifyError.setText(R.string.err_fetch_acc_data_failed);
                modifyError.setVisibility(View.VISIBLE);
            }
        };
        // one-liner for the url for a customer OR technician
        String url = Variables.userType.equals("customer") ? Variables.getAbsoluteUrl("/api/customer/getByID/" + Variables.userID) : Variables.getAbsoluteUrl("/api/technician/getByID/" + Variables.userID);
        Variables.client.get(ModifyAccount.super.getContext(), url, null, "application/json", firstResponseHandler);
    }
}
