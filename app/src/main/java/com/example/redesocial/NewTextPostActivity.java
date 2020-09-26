package com.example.redesocial;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.redesocial.Utils.FieldValidator;
import com.example.redesocial.services.Api;
import com.example.redesocial.services.SessionManager;

import java.util.HashMap;

public class NewTextPostActivity extends AppCompatActivity {

    Api api;
    String userToken;
    String userLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SessionManager.checkLogin(NewTextPostActivity.this);
        final HashMap<String, String> loggedUser = SessionManager.getUserDetail(NewTextPostActivity.this);

        this.userToken = loggedUser.get("token");
        this.userLogin = loggedUser.get("login");

        this.api = new Api(getApplicationContext());

        setContentView(R.layout.activity_new_text_post);

        // Setting up action bar
        Toolbar toolbar = findViewById(R.id.menu_top);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Novo post de texto");
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Setting up send action
        Button btnSend = findViewById(R.id.btn_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etText = findViewById(R.id.input_text);

                if(FieldValidator.validateField(NewTextPostActivity.this, etText)) {
                    String text = etText.getText().toString();

                    api.postPost(userLogin, userToken, text, null);
                    finish();
                }
            }
        });
    }
}
