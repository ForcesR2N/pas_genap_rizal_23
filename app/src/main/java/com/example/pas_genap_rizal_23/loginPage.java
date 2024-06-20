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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        language_button = findViewById(R.id.language_button);

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

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(USERNAME_KEY, enteredUsername);
                editor.putString(PASSWORD_KEY, enteredPassword);
                editor.apply();

                Intent intent = new Intent(loginPage.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(loginPage.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                finish(); // Finish login activity
            } else {
                Toast.makeText(loginPage.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        });

        // Move this setup outside the login button's OnClickListener
        language_change = findViewById(R.id.change_language);
        header = findViewById(R.id.header);

        language_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] language = {"English", "Indonesia"};

                int checkedItem;

                if (lang_selected) {
                    checkedItem = 0;
                } else {
                    checkedItem = 1;
                }

                final AlertDialog.Builder builder = new AlertDialog.Builder(loginPage.this);

                builder.setTitle("Select a Language")
                        .setSingleChoiceItems(language, checkedItem, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                language_change.setText(language[which]);
                                if(language[which].equals("English")) {
                                    context = LocalHelper.setLocale(loginPage.this, "en");
                                    resources = context.getResources();
                                    header.setText(resources.getString(R.string.language));
                                } else if (language[which].equals("Indonesia")) {
                                    context = LocalHelper.setLocale(loginPage.this, "in");
                                    resources = context.getResources();
                                    header.setText(resources.getString(R.string.language));
                                }
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
        });
    }
}
