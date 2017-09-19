package com.jodelapp.features.photos.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AlbumPresentationModel {

    private int userId;
    private int id;
    private String title;

    private List<PhotoPresentationModel> photoPresentationModelList;

    public AlbumPresentationModel(int userID, int id, String title) {
        this.userId = userID;
        this.id = id;
        this.title = title;
    }

    public int getUserId() {
        return userId;
    }


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<PhotoPresentationModel> getPhotoPresentationModelList() {
        return photoPresentationModelList;
    }

    public void setPhotoPresentationModelList(List<PhotoPresentationModel> photoPresentationModelList) {
        this.photoPresentationModelList = photoPresentationModelList;
    }
}