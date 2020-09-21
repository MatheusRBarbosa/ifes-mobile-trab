package com.example.redesocial.Utils;

import android.content.Context;
import android.util.DisplayMetrics;

public class Layout {
    public static int calculateNoOfColums(Context context, float columnWidthDp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels;
        int noOfColums = (int) (screenWidthDp / columnWidthDp + 0.5);
        return noOfColums;
    }
}
