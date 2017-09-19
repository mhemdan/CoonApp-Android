package com.jodelapp.features.photos.usecases;

import android.support.annotation.NonNull;

import com.jodelapp.data.api.ApiService;
import com.jodelapp.features.photos.models.AlbumPresentationModel;
import com.jodelapp.features.photos.models.PhotoPresentationModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by m.hemdan on 9/18/17.
 */

public interface GetPhotosForUserByAlbumID {
    Single<List<PhotoPresentationModel>> call(@NonNull String albumId);
}
final class GetPhotosForUserByAlbumIDImpl implements GetPhotosForUserByAlbumID {

    private final ApiService apiService;

    @Inject
    public GetPhotosForUserByAlbumIDImpl(ApiService apiService) {
        this.apiService = apiService;
    }


    @Override
    public Single<List<PhotoPresentationModel>> call(@NonNull String userId) {
        //todo Don't know how but there are no use of the work thread need some clarification here
        return apiService.getPhotos(userId)
                .flatMapIterable(photoList -> photoList)
                .map(photo -> new PhotoPresentationModel(photo.getAlbumId(),
                        photo.getId(),
                        photo.getTitle(), photo.getUrl(), photo.getThumbnailUrl()))
                .toList();
    }
}
