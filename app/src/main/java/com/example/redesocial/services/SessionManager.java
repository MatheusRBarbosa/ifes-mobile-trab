package com.example.redesocial.services;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.redesocial.LoginActivity;

import java.util.HashMap;

public class SessionManager {

    private static final int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String AUTH_TOKEN = "AUTH_TOKEN";
    public static final String DEVICE_TOKEN = "DEVICE_TOKEN";

    public static void createSession(Context context, String login, String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(LOGIN, true);
        editor.putString(PREF_NAME, login);
        editor.putString(AUTH_TOKEN, token);

        editor.apply();
    }

    public static boolean isLoggin(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public static void checkLogin(Context context) {

        if (!isLoggin(context)){
            Intent i = new Intent(context, LoginActivity.class);
            context.startActivity(i);
            Activity activity = (Activity) context;
            activity.finish();
        }
    }

    public static HashMap<String, String> getUserDetail(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);

        HashMap<String, String> user = new HashMap<>();
        String login = sharedPreferences.getString(PREF_NAME, null);
        String token = sharedPreferences.getString(AUTH_TOKEN, null);
        user.put("login", login);
        user.put("token", token);

        return user;
    }

    public static void logout(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.commit();
        Intent i = new Intent(context, LoginActivity.class);
        context.startActivity(i);
        Activity activity = (Activity) context;
        activity.finish();
    }

    public static void setToken(Context context, String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DEVICE_TOKEN, token);
        editor.apply();
    }

    public static String getToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        return sharedPreferences.getString(DEVICE_TOKEN, "");
    }

}