package com.example.redesocial;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.redesocial.Utils.Layout;
import com.example.redesocial.models.Post;
import com.example.redesocial.services.Mock;
import com.example.redesocial.services.SessionManager;
import com.example.redesocial.ui.gallery.GalleryAdapter;

import java.util.HashMap;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    //SessionManager sessionManager;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //sessionManager = new SessionManager(GalleryActivity.this);
        SessionManager.checkLogin(GalleryActivity.this);

        setContentView(R.layout.activity_gallery);

        // Setting up user photos
        final HashMap<String, String> loggedUser = SessionManager.getUserDetail(GalleryActivity.this);
        this.userId = Integer.parseInt(loggedUser.get("ID"));
        List<Post> posts = Mock.getAllPostsFromUserId(this.userId); //TODO: Pegar da API

        Toolbar toolbar = findViewById(R.id.menu_top);
        // Setting up action bar
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Suas fotos");
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Setting up recycleView
        GalleryAdapter galleryAdapter = new GalleryAdapter(GalleryActivity.this, posts);

        float w = getResources().getDimension(R.dimen.gallery_item);
        int numberOfColums = Layout.calculateNoOfColums(GalleryActivity.this, w);
        //TODO: BUG - O espacamento esta estranho, nas esta correto.
        GridLayoutManager gridLayoutManager = new GridLayoutManager(GalleryActivity.this, numberOfColums);
        RecyclerView rvGallery = findViewById(R.id.rvGallery);
        rvGallery.setAdapter(galleryAdapter);
        rvGallery.setLayoutManager(gridLayoutManager);
    }
}
