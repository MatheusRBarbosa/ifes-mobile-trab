package com.ifes.mobile.redesocial.models;

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

    private final int POST_TEXT_TYPE = 1;
    private final int POST_PHOTO_TYPE = 2;

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
            return POST_TEXT_TYPE;
        }
        else if(this.postImage != null){
            return POST_PHOTO_TYPE;
        }
        return 0;
    }
}
