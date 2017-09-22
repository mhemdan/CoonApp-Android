package com.jodelapp.features.photos.presentation;

import android.util.Log;

import com.jodelapp.features.photos.usecases.GetAlbumsByUserID;
import com.jodelapp.features.photos.usecases.GetPhotosForUserByAlbumID;
import com.jodelapp.utilities.rx.RxDisposables;
import com.jodelapp.utilities.rx.ThreadTransformer;
import com.jodelapp.utilities.rx.RxDisposableFactory;
import com.jodelapp.utilities.rx.RxDisposables;
import com.jodelapp.utilities.rx.ThreadTransformer;

import java.net.UnknownHostException;

import javax.inject.Inject;


public final class UserPhotoListPresenter implements UserPhotoListContract.Presenter {

    private  String USER_ID = "1";

    private final UserPhotoListContract.View view;
    private final GetAlbumsByUserID getAlbumsByUserID;
    private final GetPhotosForUserByAlbumID getPhotosForUserByAlbumID;
    private final ThreadTransformer threadTransformer;
    private final RxDisposables disposables;
    @Inject
    public UserPhotoListPresenter(UserPhotoListContract.View view,
                                  GetAlbumsByUserID getAlbumsByUserID,
                                  GetPhotosForUserByAlbumID getPhotosForUserByAlbumID,
                                  ThreadTransformer threadTransformer,
                                  RxDisposableFactory factory) {
        // TODO: 8/13/17 your dependencies inject here
        this.view = view;
        this.getAlbumsByUserID = getAlbumsByUserID;
        this.getPhotosForUserByAlbumID = getPhotosForUserByAlbumID;
        this.threadTransformer = threadTransformer;
        this.disposables = factory.get();
    }


    @Override
    public void onAttached() {
        disposables.add(getAlbumsByUserID.call(USER_ID)
                .compose(threadTransformer.applySchedulers())
                .subscribe(
                        albums ->
                        {
                            if(albums.size()>0)
                                view.loadAlbumsList(albums);
                            else
                                view.showEmptyView();

                        },
                        error -> view.showError(error.getMessage())
                ));
    }

    @Override
    public void setUserID(String userID) {
        this.USER_ID = userID;
    }

    @Override
    public void getPhotosByAlbumID(String albumID,int albumIndex) {
        disposables.add(getPhotosForUserByAlbumID.call(albumID)
                .compose(threadTransformer.applySchedulers())
                .subscribe(
                        photos -> {
                            view.loadPhotosList(photos,albumIndex);
                        },
                        error -> view.showError(error.getMessage())));
    }

    @Override
    public void onDetached() {
        disposables.clear();
    }


}
