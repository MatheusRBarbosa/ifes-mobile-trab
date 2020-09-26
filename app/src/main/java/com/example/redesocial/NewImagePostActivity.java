package com.example.redesocial;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.redesocial.Utils.Const;
import com.example.redesocial.Utils.Dialog;
import com.example.redesocial.Utils.FieldValidator;
import com.example.redesocial.services.Api;
import com.example.redesocial.services.ImageProvider;
import com.example.redesocial.services.SessionManager;

import java.io.File;
import java.util.HashMap;

public class NewImagePostActivity extends AppCompatActivity {

    private ImageProvider imageProvider;
    private Dialog dialog;
    private String photo;
    private Api api;
    private String userToken;
    private String userLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SessionManager.checkLogin(NewImagePostActivity.this);

        setContentView(R.layout.activity_new_image_post);

        this.imageProvider = new ImageProvider(this);
        this.dialog = new Dialog(this, this.imageProvider);

        final HashMap<String, String> loggedUser = SessionManager.getUserDetail(NewImagePostActivity.this);
        this.userToken = loggedUser.get("token");
        this.userLogin = loggedUser.get("login");

        this.api = new Api(getApplicationContext());

        // Setting up action bar
        Toolbar toolbar = findViewById(R.id.menu_top);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Novo post de texto");
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Setting up image view
        ImageButton btnUpload = findViewById(R.id.btn_upload_photo);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = dialog.createGalleryDialog();
                alertDialog.show();
            }
        });

        // Setting up send button
        Button btnSend = findViewById(R.id.btn_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etTitle = findViewById(R.id.input_title);
                ImageView ivPhoto = findViewById(R.id.photo);
                if(FieldValidator.validateField(NewImagePostActivity.this, etTitle)
                        && FieldValidator.validateImage(NewImagePostActivity.this, ivPhoto))
                {
                    String photoTitle = etTitle.getText().toString();
                    System.out.println("====== PHOTO ======");
                    System.out.println(photo);
                    api.postPost(userLogin, userToken, photoTitle, photo);
                    finish();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == Const.REQUEST_PERMISSION_CAMERA) {
            if(permissions.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                this.imageProvider.takePicture();
            }
        }
        else if(requestCode == Const.REQUEST_PERMISSION_GALLERY) {
            if(permissions.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                this.imageProvider.pickInGallery();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView ivPhoto = findViewById(R.id.photo);

        if(requestCode == Const.RESULT_TAKE_PICTURE) {
            if(resultCode == Activity.RESULT_OK) {
                this.photo = this.imageProvider.getCurrentPhotoPath();

                int dimensions = (int) this.getResources().getDimension(R.dimen.input_image_preview);
                Bitmap bitmap = this.imageProvider.getBitmap(this.photo, dimensions, dimensions);
                ivPhoto.setImageBitmap(bitmap);
            }
            else {
                File f = new File(this.imageProvider.getCurrentPhotoPath());
                f.delete();
            }
        }
        else if(requestCode == Const.RESULT_PICK_IN_GALLERY) {
            Uri imageUri = data.getData();
            this.photo = this.imageProvider.getFullPath(imageUri);
            ivPhoto.setImageURI(imageUri);
        }
    }
}
