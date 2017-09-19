package com.jodelapp.features.photos.usecases;

import android.support.annotation.NonNull;

import com.jodelapp.data.api.ApiService;
import com.jodelapp.features.photos.models.AlbumPresentationModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by m.hemdan on 9/18/17.
 */

public interface GetAlbumsByUserID {
    Single<List<AlbumPresentationModel>> call(@NonNull String userId);

}
final class GetAlbumsByUserIDImpl implements GetAlbumsByUserID {

    private final ApiService apiService;

    @Inject
    public GetAlbumsByUserIDImpl(ApiService apiService) {
        this.apiService = apiService;
    }


    @Override
    public Single<List<AlbumPresentationModel>> call(@NonNull String userId) {
        //todo Don't know how but there are no use of the work thread need some clarification here
        return apiService.getAlbums(userId)
                .flatMapIterable(albumList-> albumList)
                .map(album -> new AlbumPresentationModel(album.getUserId(),
                        album.getId(),
                        album.getTitle()))
                .toList();
    }
}

