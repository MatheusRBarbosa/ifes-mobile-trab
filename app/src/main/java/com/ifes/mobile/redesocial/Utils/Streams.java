package com.ifes.mobile.redesocial.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Streams {
    public static String inputStream2String(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                is, "UTF-8"), 8);
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        is.close();
        return sb.toString();
    }

    /* Converte um InputStream para um Bitmap

		is -> InputStream a ser convertido

		Retorna um Bitmap resultado da conversão do InputStream

	*/
    public static Bitmap inputStream2Bitmap(InputStream is) throws IOException {
        try {
            return BitmapFactory.decodeStream(is);
        } finally {
            is.close();
        }
    }
}
