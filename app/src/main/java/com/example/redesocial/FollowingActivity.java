package com.example.redesocial;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.redesocial.Utils.MarginItemDecoration;
import com.example.redesocial.models.User;
import com.example.redesocial.services.Mock;
import com.example.redesocial.services.SessionManager;
import com.example.redesocial.ui.following.FollowingAdapter;

import java.util.HashMap;

public class FollowingActivity extends AppCompatActivity {

    private int userId;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);

        sessionManager = new SessionManager(FollowingActivity.this);
        sessionManager.checkLogin();

        final HashMap<String, String> loggedUser = sessionManager.getUserDetail();

        // Setting up action bar
        Toolbar toolbar = findViewById(R.id.menu_top);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Seguindo");
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Setting up adapter
        Intent i = getIntent();
        this.userId = Integer.parseInt(loggedUser.get("ID"));
        User user = Mock.getUser(this.userId); //TODO: Pegar da API
        FollowingAdapter followingAdapter = new FollowingAdapter(this, user.following);

        // Setting up recycleview
        final RecyclerView rvComments = (RecyclerView) findViewById(R.id.rv_following);
        rvComments.setLayoutManager(new LinearLayoutManager(FollowingActivity.this));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.post_spacing);
        rvComments.addItemDecoration(new MarginItemDecoration(spacingInPixels));
        rvComments.setAdapter(followingAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_following_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.op_search:
                Intent i = new Intent(FollowingActivity.this, FindUsersActivity.class);
                i.putExtra("userId", this.userId);
                startActivity(i);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
