package com.example.redesocial.models;

import java.util.Date;

public class Comment {
    public User user;
    public String text;
    public Date date;

    public Comment(User user, String text, Date date) {
        this.user = user;
        this.text = text;
        this.date = date;
    }
}
