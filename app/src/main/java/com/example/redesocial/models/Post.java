package com.example.redesocial.models;

import com.example.redesocial.Utils.Const;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.util.ArrayList;
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
            String postDate,
            String title
        ) throws ParseException {
            this.id = id;
            this.user = user;
            this.comments = new ArrayList<>();
            this.title = title;
            String date = StringUtils.rightPad(postDate, 13, "0");
            this.postDate = new Date(Long.parseLong(date));
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

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public int getCommentsSize(){
        return this.comments.size();
    }

    public Comment getLastComment() {
        if(this.comments.size() == 0) {
            User user = new User(
                    "",
                    "",
                    ""
            );
            return new Comment(user, "Sem comentários", "1234567891");
        }
        return this.comments.get(this.comments.size()-1);
    }
}
