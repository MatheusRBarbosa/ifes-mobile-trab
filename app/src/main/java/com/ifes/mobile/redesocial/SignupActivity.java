package com.ifes.mobile.redesocial;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
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

import com.ifes.mobile.redesocial.Utils.DateHandler;
import com.ifes.mobile.redesocial.Utils.Dialog;
import com.ifes.mobile.redesocial.Utils.FieldValidator;
import com.ifes.mobile.redesocial.Utils.Mask;
import com.ifes.mobile.redesocial.Utils.Const;
import com.ifes.mobile.redesocial.Utils.Streams;
import com.ifes.mobile.redesocial.services.HttpRequest;
import com.ifes.mobile.redesocial.services.ImageProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SignupActivity extends AppCompatActivity {

    private ImageProvider imageProvider;
    private Dialog dialog;
    private String photo;
    private ArrayList<EditText> form = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        this.imageProvider = new ImageProvider(this);
        this.dialog = new Dialog(this, this.imageProvider);

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
                        try {
                            SignupActivity.this.postUser(
                                    login,
                                    password,
                                    name,
                                    city,
                                    birthDate
                            );
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

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
            this.photo = this.imageProvider.getFullPath(imageUri);
            System.out.println(this.photo);
            ivUserPhoto.setImageURI(imageUri);
        }
    }

    private void postUser(String login, String password, String name, String city, String birthDate) throws ParseException {
        final String apiUrl = Const.apiUrl("cadastra_usuario.php");
        System.out.println("Posting On: " + apiUrl);

        long formatedDate = DateHandler.convertToLong(birthDate, Const.DATE_PATTERN);

        new AsyncTask<String, Void, JSONObject>() {
            HttpRequest http = new HttpRequest(apiUrl, "POST");
            @Override
            protected JSONObject doInBackground(String... strings) {
                http.addParam("login", strings[0]);
                http.addParam("senha", strings[1]);
                http.addParam("nome", strings[2]);
                http.addParam("cidade", strings[3]);
                http.addParam("data_nascimento", strings[4]);
                http.addFile("foto", new File(strings[5]));

                try {
                    InputStream is = http.execute();
                    String result = Streams.inputStream2String(is);
                    http.finish();
                    System.out.println(result);
                    return new JSONObject(result);
                }
                catch (IOException | JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(JSONObject json) {
                try {
                    http.handleResult(SignupActivity.this, json);
                    int status = json.getInt("status");
                    if(status == Const.SUCCESS) {
                        SignupActivity.this.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute(login, password, name, city, formatedDate + "", this.photo);

    }
}
