package com.example.redesocial.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class User {
    public String login;
    public String name;
    public String thumbUrl;
    public List<User> following;
    public String city;
    private String birthDate;
    private String email;

    public User(String login, String name, String thumbUrl){
        this.login = login;
        this.name = name;
        this.thumbUrl = thumbUrl;
    }

    public String getEmail() {
        return email;
    }

    /*
    public List<User> getFollowing() {
        return following;
    }
     */

    public String getBirthDate() {
        return birthDate;
    }

    public int getAge() throws ParseException {
        Date bd = new SimpleDateFormat("dd/MM/yyyy").parse(this.getBirthDate());
        Calendar birthDate = Calendar.getInstance();
        birthDate.setTime(bd);

        Calendar today = Calendar.getInstance();

        int age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
        if(today.get(Calendar.MONTH) < birthDate.get(Calendar.MONTH)) {
            age--;
        }
        else if(today.get(Calendar.MONTH) == birthDate.get(Calendar.MONTH) &&
                today.get(Calendar.DAY_OF_MONTH) < birthDate.get(Calendar.DAY_OF_MONTH)
        ) {
            age--;
        }

        return age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    public void setBirthDate(String birthDate){
        this.birthDate = birthDate;
    }
}
