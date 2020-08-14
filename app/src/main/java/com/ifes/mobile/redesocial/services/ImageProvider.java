package com.ifes.mobile.redesocial.services;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.ifes.mobile.redesocial.Utils.Const;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageProvider {
    private Activity activity;

    private String currentPhotoPath;

    public ImageProvider(Activity activity) {
        this.activity = activity;
    }

    public void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(this.activity.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(this.activity.getApplicationContext(), "Erro ao criar a imagem", Toast.LENGTH_LONG);
                return;
            }
            if(photoFile != null) {
                Uri photoUri = FileProvider.getUriForFile(
                        this.activity.getApplicationContext(),
                        "com.ifes.mobile.redesocial.fileprovider",
                        photoFile
                );
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                this.activity.startActivityForResult(takePictureIntent, Const.RESULT_TAKE_PICTURE);
            }
        }
    }

    public void pickInGallery() {
        Intent imagePickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        imagePickerIntent.setType("image/*");
        this.activity.startActivityForResult(imagePickerIntent, Const.RESULT_PICK_IN_GALLERY);
    }

    public String getCurrentPhotoPath() {
        return currentPhotoPath;
    }

    public static Bitmap getBitmap(String imageLocation, int newWidth, int newHeight) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageLocation, bmOptions);

        int photoW = bmOptions.outWidth;
        int phothoH = bmOptions.outHeight;

        int scaleFactor = Math.max(photoW/newWidth, phothoH/newHeight);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeFile(imageLocation, bmOptions);
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp;
        File externalDir = this.activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpeg",
                externalDir
        );

        this.currentPhotoPath = image.getAbsolutePath();
        return image;
    }


}
