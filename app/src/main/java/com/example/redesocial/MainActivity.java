package com.example.redesocial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.redesocial.interfaces.AsyncList;
import com.example.redesocial.services.Api;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.redesocial.Utils.Dialog;
import com.example.redesocial.Utils.MarginItemDecoration;
import com.example.redesocial.models.Post;
import com.example.redesocial.models.User;
import com.example.redesocial.services.ImageProvider;
import com.example.redesocial.services.Mock;
import com.example.redesocial.services.SessionManager;
import com.example.redesocial.ui.post.PostAdapter;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    List<Post> posts = new ArrayList<>();
    List<Post> filteredPosts = new ArrayList<>();
    List<User> following = new ArrayList<>();
    Api api;
    PostAdapter postAdapter;
    Dialog dialog;
    ImageProvider imageProvider;
    String userLogin;
    String userToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.imageProvider = new ImageProvider(this);
        this.dialog = new Dialog(this, this.imageProvider);

        // Creating session
        final HashMap<String, String> loggedUser = SessionManager.getUserDetail(MainActivity.this);
        this.userToken = loggedUser.get("token");
        this.userLogin = loggedUser.get("login");

        this.api = new Api(getApplicationContext());
        following = this.api.getFollowing(userLogin, userToken);
        this.api.setListResponse(new AsyncList<User>() {
            @Override
            public void retrieve(List<User> list) {
                following.addAll(list);
            }
        });

        // Setting up Adapter
        //TODO: Sempre que a opcao de meu mundo for selecionada, deve carregar os dados
        //TODO: Desse jeito vai carregar um post quando for recem criado
        //TODO: Order os posts por data de criacao, atualmente estao aleatorios

        posts = api.getPosts("", "",  0);
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

        this.bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        filteredPosts.clear();
                        switch (item.getItemId()) {
                            case R.id.op_everybody:
                                filteredPosts.addAll(posts);
                                break;
                            case R.id.op_my_world:
                                SessionManager.checkLogin(MainActivity.this);

                                for(Post post: posts) {
                                    String fLogin = "";
                                    for(int i = 0; i < following.size(); i++) {
                                        fLogin = following.get(i).login;

                                        if(post.user.login.equals(fLogin)){
                                            filteredPosts.add(post);
                                        }
                                    }
                                }
                                break;
                            case R.id.op_me:
                                SessionManager.checkLogin(MainActivity.this);
                                for(Post post: posts) {
                                    if(post.user.login.equals(userLogin)) {
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

        //View view = this.bottomNavigationView.findViewById(R.id.op_everybody);
        //view.performClick();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i;
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.op_friends:
                i = new Intent(MainActivity.this, FollowingActivity.class);
                startActivity(i);
                return true;
            case R.id.op_new_post:
                AlertDialog alertDialog = this.dialog.createNewPostDialog();
                alertDialog.show();
                return true;
            case R.id.op_gallery:
                i = new Intent(MainActivity.this, GalleryActivity.class);
                startActivity(i);
                return true;
            case R.id.op_logout:
                SessionManager.logout(MainActivity.this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}