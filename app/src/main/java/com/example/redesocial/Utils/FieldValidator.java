package com.example.redesocial.Utils;

import android.content.Context;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.redesocial.R;

import java.util.ArrayList;

public class FieldValidator {

    public static boolean validateForm(Context context, ArrayList<EditText> form) {
        boolean hasErrors = false;
        for(EditText et: form){
            hasErrors = !(validateField(context, et));
        }
        return !hasErrors;
    }

    public static boolean validateField(Context context, EditText editText) {
        boolean hasErrors = false;
        if(isEmpty(editText)){
            String error = context.getResources().getString(R.string.required_field);
            editText.setError(error);
            hasErrors = true;
        }
        else{
            hasErrors = false;
            editText.setError(null);
        }

        return !hasErrors;
    }

    public static boolean validateDate(Context context, EditText editText) {
        String date = editText.getText().toString();
        boolean hasError;

        if(date.length() == 10) {
            editText.setError(null);
            hasError = false;
        }
        else {
            String error = context.getResources().getString(R.string.date_size_field);
            editText.setError(error);
            hasError = true;
        }
        return !hasError;
    }

    public static boolean validateImage(Context context, ImageView imageView){
        boolean hasError;
        if(imageView.getDrawable() != null) {
            hasError = false;
        }
        else {
            String error = context.getResources().getString(R.string.image_required);
            Toast toast = Toast.makeText(context, error, Toast.LENGTH_LONG);
            toast.show();
            hasError = true;
        }
        return !hasError;
    }

    private static boolean isEmpty(EditText editText) {
        String text = editText.getText().toString().trim();
        return text.length() == 0;
    }
}
