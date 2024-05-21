package com.example.pas_genap_rizal_23;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class loginPage extends AppCompatActivity {

    EditText txtUsername, txtPassword;
    Button btnLogin;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREFS = "myPrefs";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        String username = sharedPreferences.getString(USERNAME_KEY, null);
        String password = sharedPreferences.getString(PASSWORD_KEY, null);
        if (username != null && password != null) {
            Intent intent = new Intent(loginPage.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        btnLogin.setOnClickListener(v -> {
            String enteredUsername = txtUsername.getText().toString();
            String enteredPassword = txtPassword.getText().toString();

            if (enteredUsername.equals("admin") && enteredPassword.equals("admin")) {
                // Save login information to SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(USERNAME_KEY, enteredUsername);
                editor.putString(PASSWORD_KEY, enteredPassword);
                editor.apply();

                // Navigate to MainActivity or any desired fragment
                Intent intent = new Intent(loginPage.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(loginPage.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                finish(); // Finish login activity
            } else {
                Toast.makeText(loginPage.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
