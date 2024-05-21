package com.example.pas_genap_rizal_23.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.pas_genap_rizal_23.R;
import com.example.pas_genap_rizal_23.loginPage;

import static android.content.Context.MODE_PRIVATE;

public class profile extends Fragment{

    TextView tvUsername, tvPassword;
    Button btnLogOut;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREFS = "myPrefs";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvUsername = view.findViewById(R.id.tvUsername);
        tvPassword = view.findViewById(R.id.tvPassword);
        btnLogOut = view.findViewById(R.id.btnLogOut);

        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        String username = sharedPreferences.getString(USERNAME_KEY, null);
        String password = sharedPreferences.getString(PASSWORD_KEY, null);

        if (username != null && password != null) {
            tvUsername.setText("Username: " + username);
            tvPassword.setText("Password: " + password);
        }

        btnLogOut.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            Toast.makeText(getActivity(), "Logged Out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), loginPage.class);
            startActivity(intent);
            getActivity().finish();
        });

        return view;
    }
}
