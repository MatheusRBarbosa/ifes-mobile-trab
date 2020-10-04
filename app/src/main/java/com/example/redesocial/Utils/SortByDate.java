package com.example.redesocial.Utils;

import com.example.redesocial.models.Post;

import java.util.Comparator;

public class SortByDate implements Comparator<Post> {
    @Override
    public int compare(Post o1, Post o2) {
        return o1.postDate.compareTo(o2.postDate);
    }
}
