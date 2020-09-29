package com.example.redesocial.ui.findUsers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.redesocial.FindUsersActivity;
import com.example.redesocial.R;
import com.example.redesocial.Utils.Const;
import com.example.redesocial.models.User;
import com.example.redesocial.services.Api;
import com.example.redesocial.services.Mock;

import java.text.ParseException;
import java.util.List;

public class FindUsersAdapter extends RecyclerView.Adapter {
    List<User> following;
    String login;
    String token;
    FindUsersActivity findUsersActivity;
    Api api;

    public FindUsersAdapter(FindUsersActivity findUsersActivity, List<User> following, String login, String token){
        this.findUsersActivity = findUsersActivity;
        this.following = following;
        this.login = login;
        this.token = token;
        this.api = new Api(findUsersActivity.getApplicationContext());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.findUsersActivity);
        View view = layoutInflater.inflate(R.layout.following_item, parent, false);
        return new FindUsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final User user = this.following.get(position);

        if(!user.login.equals(this.login)) {
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

            // Button behavior controller
            Button btnUnFollow = holder.itemView.findViewById(R.id.btn_follow);

            // Set button icon
            if(user.getIsFollowing() == Const.isFollowing) {
                btnUnFollow.setBackgroundResource(R.drawable.ic_clear);
            }
            else {
                btnUnFollow.setBackgroundResource(R.drawable.ic_follow);
            }

            // Follow logic
            btnUnFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(user.getIsFollowing() == Const.isNotFollowing){
                        view.setBackgroundResource(R.drawable.ic_follow);
                        api.postFollow(login, token, user.login);
                    }
                    else{
                        view.setBackgroundResource(R.drawable.ic_clear);
                    }

                }
            });
        }
        else{
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, 0);
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(params);
        }
    }

    @Override
    public int getItemCount() {
        return this.following.size();
    }
}
