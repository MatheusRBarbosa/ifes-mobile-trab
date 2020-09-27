package com.example.redesocial.models;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class Comment {
    public User user;
    public String text;
    public Date date;

    public Comment(User user, String text, String commentDate) {
        this.user = user;
        this.text = text;
        String date = StringUtils.rightPad(commentDate, 13, "0");
        this.date = new Date(Long.parseLong(date));
    }
}
