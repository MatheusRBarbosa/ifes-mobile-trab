package com.example.redesocial;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.redesocial.Utils.MarginItemDecoration;
import com.example.redesocial.interfaces.AsyncList;
import com.example.redesocial.models.Comment;
import com.example.redesocial.models.User;
import com.example.redesocial.services.Api;
import com.example.redesocial.services.Mock;
import com.example.redesocial.ui.comments.CommentsAdapter;
import com.example.redesocial.ui.findUsers.FindUsersAdapter;

import java.util.List;

public class FindUsersActivity extends AppCompatActivity {

    private String userLogin;
    Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_users);

        Intent i = getIntent();
        this.userLogin = i.getStringExtra("login");
        String search = i.getStringExtra("search");

        this.api = new Api(getApplicationContext());

        // Setting up action bar
        Toolbar toolbar = findViewById(R.id.menu_top);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Encontrar pessoas");
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Setting up adapter
        final List<User> users = this.api.getFindUsers(userLogin, search);

        // Setting up recycleview
        final RecyclerView rvComments = (RecyclerView) findViewById(R.id.rv_new_users);
        rvComments.setLayoutManager(new LinearLayoutManager(FindUsersActivity.this));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.post_spacing);
        rvComments.addItemDecoration(new MarginItemDecoration(spacingInPixels));

        this.api.setListResponse(new AsyncList<User>() {
            @Override
            public void retrieve(List<User> list) {
                users.addAll(list);
                FindUsersAdapter findUsersAdapter = new FindUsersAdapter(FindUsersActivity.this, users, userLogin);
                rvComments.setAdapter(findUsersAdapter);
            }
        });
    }
}
