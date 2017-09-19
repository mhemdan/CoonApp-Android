package com.jodelapp.features.profile.usercases;


import dagger.Module;
import dagger.Provides;

/**
 * Created by m.hemdan on 9/18/17.
 */
@Module
public class UsersListUseCaseModule {
    @Provides
    public GetUsersList provideGetUserList(GetUsersListImpl usecase) {
        return usecase;
    }
}
