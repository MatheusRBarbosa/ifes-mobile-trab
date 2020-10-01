package com.example.redesocial.interfaces;

import com.example.redesocial.models.Comment;

import java.util.List;

public interface AsyncCommentResponse {
    void retrieve(List<Comment> list);
}
