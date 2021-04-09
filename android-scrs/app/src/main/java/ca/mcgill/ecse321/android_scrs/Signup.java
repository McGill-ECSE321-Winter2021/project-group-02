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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.BasicHttpEntity;
import cz.msebera.android.httpclient.entity.StringEntity;

public class Signup extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.signup, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //view querying
        TextView error_warning = view.findViewById(R.id.error_warning_signup);
        TextView name_input = view.findViewById(R.id.signup_name);
        TextView email_input = view.findViewById(R.id.signup_email);
        TextView tel_input = view.findViewById(R.id.signup_tel);
        TextView password_input = view.findViewById(R.id.signup_password);
        TextView repeat_password_input = view.findViewById(R.id.signup_repeat_password);

        TextView[] inputs = new TextView[] {name_input, email_input, tel_input, password_input, repeat_password_input};

        for(TextView input:inputs){
            input.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    removeWarning(view);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

        view.findViewById(R.id.button_back2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Variables.customerType == "customer"){
                    NavHostFragment.findNavController(Signup.this)
                            .navigate(R.id.action_signup_to_login_Signup);
                } else{

                    NavHostFragment.findNavController(Signup.this)
                            .navigate(R.id.action_login_Signup_to_mainpage);
                }

            }
        });

        view.findViewById(R.id.button_submit_signup).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                String name = name_input.getText().toString();
                String email = email_input.getText().toString();
                String tel = tel_input.getText().toString();
                String password = password_input.getText().toString();
                String passwordRepeat = repeat_password_input.getText().toString();

                //if passwords do not match
                if(!password.equals(passwordRepeat)){
                    error_warning.setText("Passwords do not match.");
                    error_warning.setVisibility(View.VISIBLE);
                    return;
                }


                // putting everything in a json object
                StringEntity params = null;
                JSONObject json = new JSONObject();
                try {
                    json.put("name", name);
                    json.put("email", email);
                    json.put("tel", tel);
                    json.put("password", password);
                    params = new StringEntity(json.toString());
                }catch(Exception e){
                    System.out.println(e);
                }

                AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        System.out.println("success");
                        NavHostFragment.findNavController(Signup.this)
                                .navigate(R.id.action_signup_to_login);
                    }

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        System.out.println(statusCode);
                        error_warning.setText("An error occured. Please try again later.");
                        error_warning.setVisibility(View.VISIBLE);
                    }
                };
                Variables.client.post(Signup.super.getContext(), Variables.getAbsoluteUrl("/api/customer/create"), params,"application/json", responseHandler);
            }
        });
    }

    //helper functions
    public void removeWarning(@NonNull View view){
        view.findViewById(R.id.error_warning_signup).setVisibility(View.INVISIBLE);
    }
}
