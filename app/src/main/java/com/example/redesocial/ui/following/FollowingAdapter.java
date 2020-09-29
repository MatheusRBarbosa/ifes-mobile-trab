package com.example.redesocial.ui.following;

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
import com.example.redesocial.FollowingActivity;
import com.example.redesocial.R;
import com.example.redesocial.Utils.Const;
import com.example.redesocial.models.User;
import com.example.redesocial.services.Api;

import java.text.ParseException;
import java.util.List;

public class FollowingAdapter extends RecyclerView.Adapter{
    private FollowingActivity followingActivity;
    private List<User> users;
    String login;
    String token;
    Api api;

    public FollowingAdapter(FollowingActivity followingActivity, List<User> users, String userLogin, String userToken) {
        this.users = users;
        this.followingActivity = followingActivity;
        this.login = userLogin;
        this.token = userToken;
        this.api = new Api(followingActivity.getApplicationContext());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.followingActivity);
        View view = layoutInflater.inflate(R.layout.following_item, parent, false);
        return new FollowingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final User user = this.users.get(position);

        // RequestOptions to use when load image is needed
        RequestOptions glideOptions = new RequestOptions()
                .placeholder(R.drawable.ic_everybody)
                .error(R.mipmap.ic_launcher_round);

        // Loading user name
        TextView tvUserName = holder.itemView.findViewById(R.id.user_name);
        tvUserName.setText(user.name);

        // Loading user photo
        ImageView ivAvatar = holder.itemView.findViewById(R.id.user_photo);
        Glide.with(holder.itemView.getContext()).load(user.thumbUrl).apply(glideOptions).into(ivAvatar);

        // Loading user age
        TextView tvAge = holder.itemView.findViewById(R.id.user_age);
        String age = null;
        try {
            age = user.getAge() + " anos";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tvAge.setText(age);

        // Loagin user city
        TextView tvCity = holder.itemView.findViewById(R.id.user_city);
        tvCity.setText(user.city);
        Button btnUnFollow = holder.itemView.findViewById(R.id.btn_follow);
        btnUnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setBackgroundResource(R.drawable.ic_clear);
                api.postUnFollow(login, token, user.login);
                users.remove(position);
                FollowingAdapter.this.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }
}
