package com.jodelapp.features.photos.presentation;

import android.util.Log;

import com.jodelapp.features.photos.usecases.GetAlbumsByUserID;
import com.jodelapp.features.photos.usecases.GetPhotosForUserByAlbumID;
import com.jodelapp.utilities.rx.RxDisposables;
import com.jodelapp.utilities.rx.ThreadTransformer;
import com.jodelapp.utilities.rx.RxDisposableFactory;
import com.jodelapp.utilities.rx.RxDisposables;
import com.jodelapp.utilities.rx.ThreadTransformer;

import javax.inject.Inject;


public final class UserPhotoListPresenter implements UserPhotoListContract.Presenter {

    private static final String USER_ID = "1";

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
                        todos -> view.loadAlbumsList(todos),
                        error -> Log.e("UserToDo", error.getMessage())
                ));
    }

    @Override
    public void getPhotosByAlbumID(String albumID,int albumIndex) {
        disposables.add(getPhotosForUserByAlbumID.call(albumID)
                .compose(threadTransformer.applySchedulers())
                .subscribe(
                        todos -> view.loadPhotosList(todos,albumIndex),
                        error -> Log.e("UserToDo", error.getMessage())
                ));
    }

    @Override
    public void onDetached() {
        disposables.clear();
    }


}
