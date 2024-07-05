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

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class loginPage extends AppCompatActivity {

    EditText txtUsername, txtPassword;
    Button btnLogin;
    SharedPreferences sharedPreferences;
    TextView language_change, header, tvUsername, tvPassword;
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

        // Retrieve saved language preference and set the locale
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String language = sharedPreferences.getString(LANGUAGE_KEY, "en");
        context = LocalHelper.setLocale(this, language);
        resources = context.getResources();

        setContentView(R.layout.activity_login_page);

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        language_button = findViewById(R.id.language_button);
        language_change = findViewById(R.id.change_language);
        header = findViewById(R.id.header);
        tvUsername = findViewById(R.id.tvUsername);
        tvPassword = findViewById(R.id.tvPassword);

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
            String currentLanguage = sharedPreferences.getString(LANGUAGE_KEY, "en");
            int checkedItem = currentLanguage.equals("en") ? 0 : 1;

            final AlertDialog.Builder builder = new AlertDialog.Builder(loginPage.this);
            builder.setTitle("Select a Language")
                    .setSingleChoiceItems(languageOptions, checkedItem, null)
                    .setPositiveButton("OK", (dialog, which) -> {
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        String selectedLanguage = selectedPosition == 0 ? "en" : "in";

                        // Save the selected language to SharedPreferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(LANGUAGE_KEY, selectedLanguage);
                        editor.apply();

                        // Apply the new locale and update the text
                        context = LocalHelper.setLocale(loginPage.this, selectedLanguage);
                        resources = context.getResources();
                        updateText();
                    });

            builder.create().show();
        });
    }

    private void updateText() {

        updateTextInputHint(R.id.txtUsername, R.id.txtUsernameLayout, R.string.add_username);
        updateTextInputHint(R.id.txtPassword, R.id.txtPasswordLayout, R.string.add_password);

        tvUsername.setText(resources.getString(R.string.username));
        tvPassword.setText(resources.getString(R.string.password));
        btnLogin.setText(resources.getString(R.string.button_login));
        header.setText(resources.getString(R.string.header));

        String language = sharedPreferences.getString(LANGUAGE_KEY, "en");
        language_change.setText(language.equals("en") ? "English" : "Indonesia");
    }
    private void updateTextInputHint(int editTextId, int inputLayoutId, int hintStringId) {
        TextInputEditText editText = findViewById(editTextId);
        TextInputLayout inputLayout = findViewById(inputLayoutId);
        if(inputLayout != null){
            inputLayout.setHint(resources.getString(hintStringId));
        }
    }
}
