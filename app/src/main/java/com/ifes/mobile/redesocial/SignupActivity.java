package com.ifes.mobile.redesocial;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ifes.mobile.redesocial.Utils.Dialog;
import com.ifes.mobile.redesocial.Utils.FieldValidator;
import com.ifes.mobile.redesocial.Utils.Mask;
import com.ifes.mobile.redesocial.Utils.Const;
import com.ifes.mobile.redesocial.services.ImageProvider;

import java.io.File;
import java.util.ArrayList;

public class SignupActivity extends AppCompatActivity {

    private ImageProvider imageProvider;
    private Dialog dialog;
    private String photo;
    private boolean hasError;
    private ArrayList<EditText> form = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        this.imageProvider = new ImageProvider(this);
        this.dialog = new Dialog(this, this.imageProvider);
        this.hasError = false;

        // Setting up action bar
        Toolbar toolbar = findViewById(R.id.menu_top);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Cadastrar");
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Mask date
        final EditText etBrithDate = findViewById(R.id.input_date);
        form.add(etBrithDate);
        etBrithDate.addTextChangedListener(Mask.mask(etBrithDate, Mask.FORMAT_DATE));

        // Setting up photo preview
        ImageButton btnImagePicker = findViewById(R.id.btn_image_picker);
        btnImagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = dialog.createGalleryDialog();
                alertDialog.show();
            }
        });

        // On submit
        Button btnSignup = findViewById(R.id.btn_signup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etPassword = findViewById(R.id.input_password);
                form.add(etPassword);
                String password = etPassword.getText().toString();

                EditText etConfirmPassword = findViewById(R.id.input_password_confirm);
                form.add(etConfirmPassword);
                String confirmPassword = etConfirmPassword.getText().toString();

                // Armazenar valores caso a senha esteja correta, evita desperdicio de memoria

                if(password.equals(confirmPassword)){
                    EditText etLogin = findViewById(R.id.input_login);
                    form.add(etLogin);
                    String login = etLogin.getText().toString();

                    EditText etName = findViewById(R.id.input_name);
                    form.add(etName);
                    String name = etName.getText().toString();

                    EditText etCity = findViewById(R.id.input_city);
                    form.add(etCity);
                    String city = etCity.getText().toString();

                    String birthDate = etBrithDate.getText().toString();

                    ImageView ivUserPhoto = findViewById(R.id.input_image_preview);

                    if(FieldValidator.validateForm(SignupActivity.this, form)
                            && FieldValidator.validateImage(SignupActivity.this, ivUserPhoto))
                    {
                        //TODO: Enviar dados para api
                        finish();
                    }
                }
                else{
                    Toast toast = Toast.makeText(SignupActivity.this, "Senha e confirmação não batem", Toast.LENGTH_LONG);
                    toast.show();
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
        ImageView ivUserPhoto = findViewById(R.id.input_image_preview);

        if(requestCode == Const.RESULT_TAKE_PICTURE) {
            if(resultCode == Activity.RESULT_OK) {
                this.photo = this.imageProvider.getCurrentPhotoPath();

                int dimensions = (int) this.getResources().getDimension(R.dimen.input_image_preview);
                Bitmap bitmap = this.imageProvider.getBitmap(this.photo, dimensions, dimensions);
                ivUserPhoto.setImageBitmap(bitmap);
            }
            else {
                File f = new File(this.imageProvider.getCurrentPhotoPath());
                f.delete();
            }
        }
        else if(requestCode == Const.RESULT_PICK_IN_GALLERY) {
            Uri imageUri = data.getData();
            ivUserPhoto.setImageURI(imageUri);
        }
    }
}
