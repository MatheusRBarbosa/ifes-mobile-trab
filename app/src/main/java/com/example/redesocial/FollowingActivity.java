package com.example.redesocial;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SearchEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.redesocial.Utils.MarginItemDecoration;
import com.example.redesocial.interfaces.AsyncList;
import com.example.redesocial.models.Comment;
import com.example.redesocial.models.User;
import com.example.redesocial.services.Api;
import com.example.redesocial.services.Mock;
import com.example.redesocial.services.SessionManager;
import com.example.redesocial.ui.following.FollowingAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FollowingActivity extends AppCompatActivity {

    String userToken;
    String userLogin;
    Api api;
    final List<User> follwing = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);

        // Create session
        final HashMap<String, String> loggedUser = SessionManager.getUserDetail(FollowingActivity.this);
        this.userToken = loggedUser.get("token");
        this.userLogin = loggedUser.get("login");

        this.api = new Api(getApplicationContext());

        // Setting up action bar
        Toolbar toolbar = findViewById(R.id.menu_top);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Seguindo");
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Setting up adapter
        this.follwing.addAll(api.getFollowing(userLogin, userToken));

        // Setting up recycleview
        final RecyclerView rvComments = (RecyclerView) findViewById(R.id.rv_following);
        rvComments.setLayoutManager(new LinearLayoutManager(FollowingActivity.this));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.post_spacing);
        rvComments.addItemDecoration(new MarginItemDecoration(spacingInPixels));

        this.api.setListResponse(new AsyncList<User>() {
            @Override
            public void retrieve(List<User> list) {
                follwing.addAll(list);
                FollowingAdapter followingAdapter = new FollowingAdapter(FollowingActivity.this, follwing);
                rvComments.setAdapter(followingAdapter);
            }
        });
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
                SearchView searchView = (SearchView) item.getActionView();
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        Intent i = new Intent(FollowingActivity.this, FindUsersActivity.class);
                        i.putExtra("login", userLogin);
                        i.putExtra("token", userToken);
                        i.putExtra("search", query);
                        startActivity(i);
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                });
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
