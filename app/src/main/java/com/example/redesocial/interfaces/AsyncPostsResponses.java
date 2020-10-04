package com.example.redesocial.interfaces;

import com.example.redesocial.models.Post;

import java.util.List;

public interface AsyncPostsResponses {
    void retrieve(List<Post> list);
}
