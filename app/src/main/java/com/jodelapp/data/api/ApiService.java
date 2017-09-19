package com.jodelapp.data.api;

import com.jodelapp.data.models.Album;
import com.jodelapp.data.models.Photo;
import com.jodelapp.data.models.ToDo;
import com.jodelapp.data.models.user.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiService {

    String USER_ID = "userId";
    String ALBUM_ID = "albumId";

    @GET(URLS.TODOS_URL)
    Observable<List<ToDo>> getToDos(@Query(USER_ID) String userId);

    @GET(URLS.ALBUMS_URL)
    Observable<List<Album>> getAlbums(@Query(USER_ID) String userId);

    @GET(URLS.PHOTOS_URL)
    Observable<List<Photo>> getPhotos(@Query(ALBUM_ID) String albumID);

    @GET(URLS.USERS_URL)
    Observable<List<User>> getUsers();
}
