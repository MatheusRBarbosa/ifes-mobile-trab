package com.ifes.mobile.redesocial;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ifes.mobile.redesocial.Utils.FieldValidator;
import com.ifes.mobile.redesocial.models.User;
import com.ifes.mobile.redesocial.services.SessionManager;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    SessionManager sessionManager;
    ArrayList<EditText> form = new ArrayList<>();
    User mockUser = new User(
            1,
            "Matheus",
            "http://clipart-library.com/images/kiKo7BoqT.png",
            "Serra"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Setting up action bar
        Toolbar toolbar = findViewById(R.id.menu_top);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Session
        sessionManager = new SessionManager(LoginActivity.this);
        mockUser.setEmail("matheuskleber@live.com");

        EditText username = findViewById(R.id.username);
        this.form.add(username);
        EditText password = findViewById(R.id.password);
        this.form.add(password);

        Button btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(FieldValidator.validateForm(LoginActivity.this, form)) {
                    //TODO: enviar username e senha, receber user da API
                    sessionManager.createSession(mockUser.id, mockUser.name, mockUser.getEmail());
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }
            }
        });

        Button btnSignup = findViewById(R.id.btn_signup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(i);
            }
        });
    }
}
