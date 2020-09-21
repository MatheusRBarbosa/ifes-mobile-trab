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
import com.example.redesocial.services.Firebase;
import com.example.redesocial.services.HttpRequest;
import com.example.redesocial.services.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
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

        EditText etLogin = findViewById(R.id.username);
        final String login = etLogin.getText().toString();
        this.form.add(etLogin);

        final EditText etPassword = findViewById(R.id.password);
        final String password = etPassword.getText().toString();
        this.form.add(etPassword);

        Button btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(FieldValidator.validateForm(LoginActivity.this, form)) {
                    //TODO: enviar username e senha, receber user da API
                    String token = SessionManager.getToken(LoginActivity.this);
                    System.out.println("===== TOKEN =====");
                    System.out.println(token);
                    LoginActivity.this.postLogin(login, password, token);
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

    private void postLogin(String login, String password, String token) {
        final String apiUrl = Const.apiUrl("login.php");

        new AsyncTask<String, Void, JSONObject>() {
            HttpRequest http = new HttpRequest(apiUrl, "POST");
            @Override
            protected JSONObject doInBackground(String... strings) {
                http.addParam("login", strings[0]);
                http.addParam("senha", strings[1]);
                http.addParam("appid", strings[2]);

                try {
                    InputStream is = http.execute();
                    String result = Streams.inputStream2String(is);
                    http.finish();
                    return new JSONObject(result);
                }
                catch (IOException | JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(JSONObject json) {
                try {
                    http.handleResult(LoginActivity.this, json);
                    int status = json.getInt("status");
                    if(status == Const.SUCCESS) {
                        sessionManager.createSession(mockUser.id, mockUser.name, mockUser.getEmail());
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute(login, password, token);
    }
}
