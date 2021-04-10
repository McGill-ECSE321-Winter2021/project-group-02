package ca.mcgill.ecse321.android_scrs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class Mainpage extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.mainpage, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_customer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Variables.userType = "customer";
                NavHostFragment.findNavController(Mainpage.this)
                        .navigate(R.id.action_mainpage_to_login_Signup);
            }
        });

        view.findViewById(R.id.button_technician).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Variables.userType = "technician";
                NavHostFragment.findNavController(Mainpage.this)
                        .navigate(R.id.action_mainpage_to_login);
            }
        });

        view.findViewById(R.id.button_assistant).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Variables.userType = "assistant";
                NavHostFragment.findNavController(Mainpage.this)
                        .navigate(R.id.action_mainpage_to_login);
            }
        });
    }
}