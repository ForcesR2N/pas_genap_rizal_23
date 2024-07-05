package com.example.pas_genap_rizal_23;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class loginPage extends AppCompatActivity {

    EditText txtUsername, txtPassword;
    Button btnLogin;
    SharedPreferences sharedPreferences;
    TextView language_change, header;
    boolean lang_selected = true;
    Context context;
    Resources resources;
    private RelativeLayout language_button;

    private static final String SHARED_PREFS = "myPrefs";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";
    private static final String LANGUAGE_KEY = "language";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String language = sharedPreferences.getString(LANGUAGE_KEY, "en");
        context = LocalHelper.setLocale(this, language);
        resources = context.getResources();

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        language_button = findViewById(R.id.language_button);
        language_change = findViewById(R.id.change_language);
        header = findViewById(R.id.header);

        updateText();

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
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(USERNAME_KEY, enteredUsername);
                editor.putString(PASSWORD_KEY, enteredPassword);
                editor.apply();

                Intent intent = new Intent(loginPage.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(loginPage.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(loginPage.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        });

        language_button.setOnClickListener(v -> {
            final String[] languageOptions = {"English", "Indonesia"};
            int checkedItem = language.equals("en") ? 0 : 1;

            final AlertDialog.Builder builder = new AlertDialog.Builder(loginPage.this);
            builder.setTitle("Select a Language")
                    .setSingleChoiceItems(languageOptions, checkedItem, (dialog, which) -> {
                        String selectedLanguage = which == 0 ? "en" : "in";
                        language_change.setText(languageOptions[which]);

                        // Save the selected language to SharedPreferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(LANGUAGE_KEY, selectedLanguage);
                        editor.apply();

                        // Apply the new locale and update the text
                        context = LocalHelper.setLocale(loginPage.this, selectedLanguage);
                        resources = context.getResources();
                        updateText();
                    })
                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

            builder.create().show();
        });
    }

        private void updateText() {
           txtUsername.setHint(resources.getString(R.string.username));
           txtPassword.setHint(resources.getString(R.string.password));
           btnLogin.setText(resources.getString(R.string.button_login));
           language_change.setText(resources.getString(R.string.language));
           header.setText(resources.getString(R.string.header));

           String language = sharedPreferences.getString(LANGUAGE_KEY, "en");
           language_change.setText(language.equals("en") ? "English" : "Indonesia");
        }
    }
