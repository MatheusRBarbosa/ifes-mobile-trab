package com.ifes.mobile.redesocial;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ifes.mobile.redesocial.Utils.FieldValidator;
import com.ifes.mobile.redesocial.services.SessionManager;

public class NewTextPostActivity extends AppCompatActivity {

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = new SessionManager(NewTextPostActivity.this);
        sessionManager.checkLogin();

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
                    //TODO: Enviar para API
                    finish();
                }
            }
        });
    }
}
