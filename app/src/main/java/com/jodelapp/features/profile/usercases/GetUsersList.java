package com.jodelapp.features.profile.usercases;

import android.support.annotation.NonNull;

import com.jodelapp.data.api.ApiService;
import com.jodelapp.features.profile.model.UserPresentationModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by m.hemdan on 9/18/17.
 */

public interface GetUsersList {
    Single<List<UserPresentationModel>> call();
}
final class GetUsersListImpl implements GetUsersList {

    private final ApiService apiService;

    @Inject
    public GetUsersListImpl(ApiService apiService) {
        this.apiService = apiService;
    }


    @Override
    public Single<List<UserPresentationModel>> call() {
        //todo Don't know how but there are no use of the work thread need some clarification here
        return apiService.getUsers()
                .flatMapIterable(users-> users)
                .map(user -> new UserPresentationModel(user.getId(),user.getName()
                        ,user.getUsername(),user.getEmail(),user.getAddress(),
                        user.getPhone(),user.getWebsite(),user.getCompany()))
                .toList();
    }
}
