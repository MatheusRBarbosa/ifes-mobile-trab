package com.example.redesocial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.redesocial.Utils.MarginItemDecoration;
import com.example.redesocial.interfaces.AsyncCommentResponse;
import com.example.redesocial.interfaces.AsyncList;
import com.example.redesocial.models.Comment;
import com.example.redesocial.services.Api;
import com.example.redesocial.services.Mock;
import com.example.redesocial.services.SessionManager;
import com.example.redesocial.ui.comments.CommentsAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommentsActivity extends AppCompatActivity {
    Api api;
    String userToken;
    String userLogin;
    final List<Comment> comments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        // Create session
        final HashMap<String, String> loggedUser = SessionManager.getUserDetail(CommentsActivity.this);
        this.userToken = loggedUser.get("token");
        this.userLogin = loggedUser.get("login");

        this.api = new Api(getApplicationContext());

        // Setting up top menu
        Toolbar toolbar = findViewById(R.id.menu_top);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Comentários");
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Setting up adapter
        Intent i = getIntent();
        final int postId = i.getIntExtra("postId", 0);
        comments.addAll(api.getComements(postId));

        System.out.println("COMENTARIO TEXTO");
        //System.out.println(commentList.get(0).text);
        System.out.println(comments.size());

        // Setting up recycleview
        final RecyclerView rvComments = findViewById(R.id.rv_comments);
        rvComments.setLayoutManager(new LinearLayoutManager(CommentsActivity.this));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.post_spacing);
        rvComments.addItemDecoration(new MarginItemDecoration(spacingInPixels));

        this.api.setCommentResponse(new AsyncCommentResponse() {
            @Override
            public void retrieve(List<Comment> list) {
                comments.addAll(list);
                CommentsAdapter commentsAdapter = new CommentsAdapter(CommentsActivity.this, comments);
                rvComments.setAdapter(commentsAdapter);
            }
        });

        // Setting up send_comment
        final EditText etComment = findViewById(R.id.send_comment);
        Button btnSendComment = findViewById(R.id.btn_send_comment);

        if(!SessionManager.isLoggin(CommentsActivity.this)){
            btnSendComment.setEnabled(false);
            etComment.setEnabled(false);
            etComment.setText(R.string.loggin_needed);
        }

        btnSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = etComment.getText().toString();
                api.postComment(userLogin, userToken, postId, comment);
                CommentsActivity.this.finish();
            }
        });
    }
}
