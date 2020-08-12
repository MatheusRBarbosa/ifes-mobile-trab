package com.ifes.mobile.redesocial.ui.post;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ifes.mobile.redesocial.CommentsActivity;
import com.ifes.mobile.redesocial.MainActivity;
import com.ifes.mobile.redesocial.R;
import com.ifes.mobile.redesocial.models.Comment;
import com.ifes.mobile.redesocial.models.Post;
import com.ifes.mobile.redesocial.models.User;

import java.text.SimpleDateFormat;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter {
    private MainActivity mainActivity;
    private List<Post> posts;

    final int POST_TEXT_TYPE = 1;
    final int POST_PHOTO_TYPE = 2;

    public PostAdapter(MainActivity mainActivity, List<Post> posts){
        this.posts = posts;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mainActivity);
        View view = null;

        if(viewType == this.POST_TEXT_TYPE){
            view = layoutInflater.inflate(R.layout.post_text_item, parent, false);
        }
        else if(viewType == this.POST_PHOTO_TYPE){
            view = layoutInflater.inflate(R.layout.post_image_item, parent, false);
        }
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Post post = this.posts.get(position);

        // RequestOptions to use when load image is needed
        RequestOptions glideOptions = new RequestOptions()
                .placeholder(R.drawable.ic_everybody)
                .error(R.mipmap.ic_launcher_round);

        // Loading user name
        TextView tvUserName = holder.itemView.findViewById(R.id.user_name);
        tvUserName.setText(post.user.name);

        // Loading user photo
        ImageView ivAvatar = holder.itemView.findViewById(R.id.user_photo);
        Glide.with(holder.itemView.getContext()).load(post.user.thumbUrl).apply(glideOptions).into(ivAvatar);


        // Loading post date
        TextView tvPostDate = holder.itemView.findViewById(R.id.post_date);
        String date = new SimpleDateFormat("HH:mm dd/MM/yyyy").format(post.postDate);
        tvPostDate.setText(date);

        // Loading last comment
        TextView tvLastComment = holder.itemView.findViewById(R.id.last_comment);
        Comment comment = post.comments.get(post.comments.size()-1);
        // TODO: Criar metodo dentro da classe de Comment que pega o ultimo comentario
        String lastCommentText = comment.text;
        User lastCommentUser = comment.user;
        String lastComment = lastCommentUser.name + ": " + lastCommentText;
        tvLastComment.setText(lastComment);

        // If text post, load it
        if(this.getItemViewType(position) == this.POST_TEXT_TYPE){
            TextView tvPostText = holder.itemView.findViewById(R.id.post_text);
            tvPostText.setText(post.postText.textContent);
        }

        // If image post, load it
        else if(this.getItemViewType(position) == this.POST_PHOTO_TYPE){
            ImageView ivPost = holder.itemView.findViewById(R.id.post_photo);
            Glide.with(holder.itemView.getContext()).load(post.postImage.imageUrl).apply(glideOptions).into(ivPost);

            TextView tvPhotoTitle = holder.itemView.findViewById(R.id.photo_title);
            tvPhotoTitle.setText(post.postImage.title);
        }

        // Loading Comments button
        Button btnComments = (Button) holder.itemView.findViewById(R.id.btn_comments);
        btnComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), CommentsActivity.class);
                Bundle bundle = new Bundle();
                i.putExtra("postId", post.id);
                view.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public int getItemViewType(int position) {
        return this.posts.get(position).getPostType();
    }

    private void sendComment(String comment){
        Toast.makeText(mainActivity, "Comentario: " + comment, Toast.LENGTH_LONG);
    }
}
