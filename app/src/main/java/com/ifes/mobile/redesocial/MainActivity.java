package com.ifes.mobile.redesocial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ifes.mobile.redesocial.Utils.MarginItemDecoration;
import com.ifes.mobile.redesocial.models.Post;
import com.ifes.mobile.redesocial.models.User;
import com.ifes.mobile.redesocial.services.Mock;
import com.ifes.mobile.redesocial.services.SessionManager;
import com.ifes.mobile.redesocial.ui.post.PostAdapter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SessionManager sessionManager;

    BottomNavigationView bottomNavigationView;
    List<Post> posts = new ArrayList<>();
    List<Post> filteredPosts = new ArrayList<>();
    PostAdapter postAdapter;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Creating session
        sessionManager = new SessionManager(MainActivity.this);
        final HashMap<String, String> loggedUser = sessionManager.getUserDetail();

        // Mocking data = Mudar para API
        posts = Mock.getAllPosts(); //TODO: Consumir dados da API
        this.user = Mock.getUser(Integer.parseInt(loggedUser.get("ID"))); //TODO: pegar user da API

        // Setting up Adapter
        filteredPosts.addAll(posts);
        postAdapter = new PostAdapter(this, filteredPosts);

        // Setting up top menu
        Toolbar toolbar = findViewById(R.id.menu_top);
        setSupportActionBar(toolbar);

        // Setting up RecycleView
        final RecyclerView rvFeed = (RecyclerView) findViewById(R.id.rv_feed);
        rvFeed.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.post_spacing);
        rvFeed.addItemDecoration(new MarginItemDecoration(spacingInPixels));
        rvFeed.setAdapter(postAdapter);

        // Setting up bottom menu
        this.bottomNavigationView = findViewById(R.id.menu_bottom);

        //this.bottomNavigationView.setSelectedItemId(R.id.op_everybody); //TODO: BUG - Isso deveria fazer com que quando
                                                                          //TODO: voltasse da tela de login o app nao fechasse, abrisse na aba geral
        this.bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        filteredPosts.clear();
                        switch (item.getItemId()){
                            case R.id.op_everybody:
                                filteredPosts.addAll(posts);
                                break;
                            case R.id.op_my_world:
                                sessionManager.checkLogin();
                                List<User> following = user.getFollowing();
                                for(Post post: posts) {
                                    for(User follow: following) {
                                        if(post.user.id == follow.id){
                                            filteredPosts.add(post);
                                        }
                                    }
                                }
                                break;
                            case R.id.op_me:
                                sessionManager.checkLogin();
                                for(Post post: posts) {
                                    if(post.user.id == user.id) {
                                        filteredPosts.add(post);
                                    }
                                }
                                break;
                        }
                        postAdapter.notifyDataSetChanged();
                        return true;
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.op_friends:
                Intent i = new Intent(MainActivity.this, FollowingActivity.class);
                startActivity(i);
                return true;
            case R.id.op_new_post:
                return true;
            case R.id.op_gallery:
                return true;
            case R.id.op_logout:
                this.sessionManager.logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}