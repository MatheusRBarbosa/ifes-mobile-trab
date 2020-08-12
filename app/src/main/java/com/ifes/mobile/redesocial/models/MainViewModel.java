package com.ifes.mobile.redesocial.models;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {

    List<Post> postList = new ArrayList<>();

    public List<Post> getPostList() {
        return  postList;
    }
}
