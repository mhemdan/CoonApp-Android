package com.jodelapp.features.photos.usecases;



import dagger.Module;
import dagger.Provides;

@Module
public class UserPhotoListUseCaseModule {
    @Provides
    public GetAlbumsByUserID provideGetAlbumByUserID(GetAlbumsByUserIDImpl usecase) {
        return usecase;
    }
    @Provides
    public GetPhotosForUserByAlbumID getPhotosForUserByAlbumID(GetPhotosForUserByAlbumIDImpl usecase) {
        return usecase;
    }
}
