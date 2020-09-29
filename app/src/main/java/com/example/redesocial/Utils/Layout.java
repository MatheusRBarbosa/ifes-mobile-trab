package com.example.redesocial.Utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.redesocial.R;

public class Layout {
    public static int calculateNoOfColums(Context context, float columnWidthDp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels;
        int noOfColums = (int) (screenWidthDp / columnWidthDp + 0.5);
        return noOfColums;
    }

    public static void changeFollowIconOnClick(View view) {
        Drawable.ConstantState icFollow = view.getContext().getDrawable(R.drawable.ic_follow).getConstantState();
        Drawable.ConstantState icClear = view.getContext().getDrawable(R.drawable.ic_clear).getConstantState();

        Drawable.ConstantState state = view.getBackground().getConstantState();
        //Drawable.ConstantState state = view.getContext().
        System.out.println("LOGCAT");
        System.out.println(icFollow);
        System.out.println(icClear);
        System.out.println(state);

        if(state.equals(icFollow)) {
            System.out.println("FOLLOW PRA CLEAR");
        }
        else if(state.equals(icClear)) {
            System.out.println("CLEAR PRA FOLLOW");
        }
        else {
            System.out.println("NENUMA OPCAO ACIMA");
        }
    }
}
