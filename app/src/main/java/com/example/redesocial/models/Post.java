package com.example.redesocial.models;

import com.example.redesocial.Utils.Const;

import java.util.Date;
import java.util.List;

public class Post {
    public int id;
    public User user;
    public Date postDate;
    public List<Comment> comments;
    public String title;
    public PostText postText;
    public PostImage postImage;

    public Post(
            int id,
            User user,
            Date postDate,
            List<Comment> comments,
            String title
        )
        {
            this.id = id;
            this.user = user;
            this.postDate = postDate;
            this.comments = comments;
            this.title = title;
        }

    public int getPostType() {
        if(this.postText != null){
            return Const.POST_TEXT_TYPE;
        }
        else if(this.postImage != null){
            return Const.POST_PHOTO_TYPE;
        }
        return 0;
    }

    public int getCommentsSize(){
        return this.comments.size();
    }

    public Comment getLastComment() {
        return this.comments.get(this.comments.size()-1);
    }
}