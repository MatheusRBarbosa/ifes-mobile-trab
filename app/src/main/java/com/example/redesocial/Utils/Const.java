package com.example.redesocial.Utils;

import java.util.Locale;

public class Const {

    // Dialogs options
    public static final int OP_GALLERY = 0;
    public static final int OP_CAMERA = 1;
    public static final int OP_TEXT_POST = 0;
    public static final int OP_IMAGE_POST = 1;

    // Requests starts with 1xx
    public static int REQUEST_PERMISSION_CAMERA = 100;
    public static int REQUEST_PERMISSION_GALLERY = 101;

    // Results starts with 2xx
    public static int RESULT_TAKE_PICTURE = 200;
    public static  int RESULT_PICK_IN_GALLERY = 201;

    // Posts Types
    public static int POST_TEXT_TYPE = 1;
    public static int POST_PHOTO_TYPE = 2;


    // API Status responses
    public static final int SUCCESS = 0;
    public static final int UNAUTHORIZED = 1;

    // Date
    public static Locale LOCALE_BRAZIL = new Locale("pt", "BR");
    public static String DATE_PATTERN = "dd/MM/yyyy";

    // Follow
    public static int isFollowing = 1;
    public static int isNotFollowing = 0;

    // Create API uri
    public static String apiUrl(String resource) {
        return "http://34.125.85.252/social/" + resource;
    }
}
