package com.jodelapp.features.photos.presentation;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jodelapp.R;
import com.jodelapp.features.photos.models.PhotoPresentationModel;
import com.squareup.picasso.Picasso;

import net.alhazmy13.mediagallery.library.activity.MediaGallery;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by m.hemdan on 9/18/17.
 */

public class UserPhotoAdapter  extends RecyclerView.Adapter<UserPhotoAdapter.ViewHolder>{
    private List<PhotoPresentationModel> items;
    public  UserPhotoAdapter(List<PhotoPresentationModel> items){
        this.items = items;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_photos, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(holder.imgPhotoThumbnail.getContext()).load(items.get(position).getThumbnailUrl()).fit().
                into(holder.imgPhotoThumbnail);
        holder.txtTitle.setText(items.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaGallery.Builder(((Activity) v.getContext()),
                        getPhotoUrlLis())
                        .title("Albums Gallery")
                        .backgroundColor(R.color.white)
                        .placeHolder(R.drawable.media_gallery_placeholder)
                        .selectedImagePosition(position)
                        .show();
            }
        });
    }

    private ArrayList<String> getPhotoUrlLis(){
        ArrayList<String> photoUrlList = new ArrayList<>();
        for (PhotoPresentationModel item : items) {
            photoUrlList.add(item.getUrl());
        }

        return photoUrlList;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.img_photo_thumbnail)
        ImageView imgPhotoThumbnail;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
