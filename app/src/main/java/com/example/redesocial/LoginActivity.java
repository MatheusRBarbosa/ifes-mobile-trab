package com.example.redesocial;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.redesocial.Utils.Const;
import com.example.redesocial.Utils.FieldValidator;
import com.example.redesocial.Utils.Streams;
import com.example.redesocial.models.User;
import com.example.redesocial.services.Api;
import com.example.redesocial.services.AsyncResponse;
import com.example.redesocial.services.Firebase;
import com.example.redesocial.services.HttpRequest;
import com.example.redesocial.services.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    ArrayList<EditText> form = new ArrayList<>();
    Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         api = new Api(LoginActivity.this, new AsyncResponse() {
             @Override
             public void authenticate(String login, String token) {
                 SessionManager.createSession(LoginActivity.this, login, token);
                 Intent i = new Intent(getApplicationContext(), MainActivity.class);
                 startActivity(i);
             }
         });

        // Setting up action bar
        Toolbar toolbar = findViewById(R.id.menu_top);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        final EditText etLogin = findViewById(R.id.username);
        this.form.add(etLogin);

        final EditText etPassword = findViewById(R.id.password);
        this.form.add(etPassword);

        Button btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(FieldValidator.validateForm(LoginActivity.this, form)) {
                    String login = etLogin.getText().toString();
                    String password = etPassword.getText().toString();
                    String appId = SessionManager.getToken(LoginActivity.this);
                    api.postLogin(login, password, appId);
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
