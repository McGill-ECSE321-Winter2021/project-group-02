package ca.mcgill.ecse321.android_scrs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class Login_Signup extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.login_signup, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Login_Signup.this)
                        .navigate(R.id.action_login_Signup_to_login);
            }
        });

        view.findViewById(R.id.button_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Login_Signup.this)
                        .navigate(R.id.action_login_Signup_to_signup);
            }
        });

        view.findViewById(R.id.button_back1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Variables.userType = null;
                NavHostFragment.findNavController(Login_Signup.this)
                        .navigate(R.id.action_login_Signup_to_mainpage);
            }
        });
    }
}
