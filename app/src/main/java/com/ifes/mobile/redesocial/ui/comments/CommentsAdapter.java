package com.ifes.mobile.redesocial.ui.comments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ifes.mobile.redesocial.CommentsActivity;
import com.ifes.mobile.redesocial.R;
import com.ifes.mobile.redesocial.models.Comment;

import java.text.SimpleDateFormat;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter {
    private CommentsActivity commentsActivity;
    private List<Comment> comments;

    public CommentsAdapter(CommentsActivity commentsActivity, List<Comment> comments){
        this.comments = comments;
        this.commentsActivity = commentsActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(commentsActivity);
        View view = layoutInflater.inflate(R.layout.comments_item, parent, false);
        return new CommentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Comment comment = this.comments.get(position);

        // RequestOptions to use when load image is needed
        RequestOptions glideOptions = new RequestOptions()
                .placeholder(R.drawable.ic_everybody)
                .error(R.mipmap.ic_launcher_round);

        // Loading user name
        TextView tvUserName = holder.itemView.findViewById(R.id.user_name);
        tvUserName.setText(comment.user.name);

        // Loading user photo
        ImageView ivAvatar = holder.itemView.findViewById(R.id.user_photo);
        Glide.with(holder.itemView.getContext()).load(comment.user.thumbUrl).apply(glideOptions).into(ivAvatar);

        // Loading comment date
        TextView tvPostDate = holder.itemView.findViewById(R.id.comment_date);
        String date = new SimpleDateFormat("HH:mm dd/MM/yyyy").format(comment.date);
        tvPostDate.setText("Feito em: " + date);

        // Loading comment Text
        TextView tvCommentText = holder.itemView.findViewById(R.id.comment);
        tvCommentText.setText(comment.text);
    }

    @Override
    public int getItemCount() {
        return this.comments.size();
    }
}
