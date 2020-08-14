package com.ifes.mobile.redesocial.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.ifes.mobile.redesocial.services.ImageProvider;

public class Dialog {

    private Context context;
    private ImageProvider imageProvider;
    private Activity activity;

    public Dialog (Context context, ImageProvider imageProvider) {
        this.context = context;
        this.imageProvider = imageProvider;
        this.activity = (Activity) context;
    }

    public AlertDialog createGalleryDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Escolha onde pegar a imagem");
        builder.setCancelable(true);

        String[] options = new String[]{"Galeria", "Camera"};

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){
                    case Const.OP_GALLERY:
                        if(Permissions.hasPermissions(context, Manifest.permission.READ_EXTERNAL_STORAGE)){
                            imageProvider.pickInGallery();
                        }
                        else {
                            ActivityCompat.requestPermissions( activity, new String[] {
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                            }, Const.REQUEST_PERMISSION_GALLERY);
                        }
                        break;
                    case Const.OP_CAMERA:
                        if(Permissions.hasPermissions(context, Manifest.permission.CAMERA)){
                            imageProvider.takePicture();
                        }
                        else {
                            ActivityCompat.requestPermissions( activity, new String[] {
                                    Manifest.permission.CAMERA,
                            }, Const.REQUEST_PERMISSION_CAMERA);
                        }
                        break;
                }
            }
        });

        AlertDialog alert = builder.create();
        return alert;
    }
}
