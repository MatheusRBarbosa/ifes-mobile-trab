package com.example.redesocial;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.redesocial.Utils.Layout;
import com.example.redesocial.interfaces.AsyncList;
import com.example.redesocial.interfaces.AsyncResponse;
import com.example.redesocial.models.Post;
import com.example.redesocial.services.Api;
import com.example.redesocial.services.Mock;
import com.example.redesocial.services.SessionManager;
import com.example.redesocial.ui.gallery.GalleryAdapter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    String userToken;
    String userLogin;
    //List<String> photos = new ArrayList<>();
    Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SessionManager.checkLogin(GalleryActivity.this);

        setContentView(R.layout.activity_gallery);

        api = new Api(getApplicationContext());

        // Setting up user photos
        final HashMap<String, String> loggedUser = SessionManager.getUserDetail(GalleryActivity.this);
        this.userToken = loggedUser.get("token");
        this.userLogin = loggedUser.get("login");

        System.out.println(userLogin);
        System.out.println(userToken);

        this.api.getGallery(userLogin, userToken);

        Toolbar toolbar = findViewById(R.id.menu_top);
        // Setting up action bar
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Suas fotos");
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Setting up recycleView

        float w = getResources().getDimension(R.dimen.gallery_item);
        int numberOfColums = Layout.calculateNoOfColums(GalleryActivity.this, w);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(GalleryActivity.this, numberOfColums);
        final RecyclerView rvGallery = findViewById(R.id.rvGallery);
        rvGallery.setLayoutManager(gridLayoutManager);

        api.setListResponse(new AsyncList() {
            @Override
            public void retrieve(List list) {
                GalleryAdapter galleryAdapter = new GalleryAdapter(GalleryActivity.this, list);
                rvGallery.setAdapter(galleryAdapter);
            }
        });
    }
}
