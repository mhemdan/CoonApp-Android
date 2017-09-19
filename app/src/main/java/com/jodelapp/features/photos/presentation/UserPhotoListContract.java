package com.jodelapp.features.photos.presentation;


import com.jodelapp.features.photos.models.AlbumPresentationModel;
import com.jodelapp.features.photos.models.PhotoPresentationModel;

import java.util.List;

public interface UserPhotoListContract {

    interface View {
        void loadAlbumsList(List<AlbumPresentationModel> providers);
        void loadPhotosList(List<PhotoPresentationModel> providers,int albumIndex);
    }

    interface Presenter {
        void onAttached();

        void setUserID(String userID);
        void getPhotosByAlbumID(String albumID,int albumIndex);

        void onDetached();
    }
}
