package com.example.redesocial.ui.gallery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.redesocial.GalleryActivity;
import com.example.redesocial.R;
import com.example.redesocial.Utils.Const;
import com.example.redesocial.models.Post;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter {

    GalleryActivity galleryActivity;
    List<String> photos;

    public GalleryAdapter (GalleryActivity galleryActivity, List<String> photos){
        this.galleryActivity = galleryActivity;
        this.photos = photos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.galleryActivity);
        View view = layoutInflater.inflate(R.layout.gallery_item, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String image = this.photos.get(position);
        String imageUrl = Const.apiUrl(image);

        // RequestOptions to use when load image is needed
        RequestOptions glideOptions = new RequestOptions()
                .placeholder(R.drawable.ic_everybody)
                .error(R.mipmap.ic_launcher_round);

        // Loading user post photo
        ImageView photo = holder.itemView.findViewById(R.id.gallery_item);
        Glide.with(holder.itemView.getContext()).load(imageUrl).apply(glideOptions).into(photo);

    }

    @Override
    public int getItemCount() {
        return this.photos.size();
    }
}
