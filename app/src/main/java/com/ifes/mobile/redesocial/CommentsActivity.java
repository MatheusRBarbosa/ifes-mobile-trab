package com.ifes.mobile.redesocial;

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

import com.ifes.mobile.redesocial.Utils.MarginItemDecoration;
import com.ifes.mobile.redesocial.models.Comment;
import com.ifes.mobile.redesocial.services.Mock;
import com.ifes.mobile.redesocial.services.SessionManager;
import com.ifes.mobile.redesocial.ui.comments.CommentsAdapter;

import java.util.List;

public class CommentsActivity extends AppCompatActivity {

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        sessionManager = new SessionManager(CommentsActivity.this);

        // Setting up top menu
        Toolbar toolbar = findViewById(R.id.menu_top);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Comentários");
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Setting up adapter
        Intent i = getIntent();
        int postId = i.getIntExtra("postId", 0);
        List<Comment> commentList = Mock.getComments(postId); //TODO: Pegar da API
        CommentsAdapter commentsAdapter = new CommentsAdapter(this, commentList);

        // Setting up recycleview
        final RecyclerView rvComments = (RecyclerView) findViewById(R.id.rv_comments);
        rvComments.setLayoutManager(new LinearLayoutManager(CommentsActivity.this));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.post_spacing);
        rvComments.addItemDecoration(new MarginItemDecoration(spacingInPixels));
        rvComments.setAdapter(commentsAdapter);

        // Setting up send_comment
        final EditText etComment = findViewById(R.id.send_comment);
        Button btnSendComment = findViewById(R.id.btn_send_comment);

        if(!sessionManager.isLoggin()){
            btnSendComment.setEnabled(false);
            etComment.setEnabled(false);
            etComment.setText(R.string.loggin_needed);
        }

        btnSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Mandar post para API
                Toast toast = Toast.makeText(
                        CommentsActivity.this,
                        etComment.getText(),
                        Toast.LENGTH_SHORT
                );
                toast.show();
            }
        });
    }
}
