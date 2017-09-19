package com.jodelapp.features.profile.presentation;

import com.jodelapp.AppComponent;
import com.jodelapp.di.scope.ViewScope;
import com.jodelapp.features.profile.usercases.UsersListUseCaseModule;

import dagger.Component;

/**
 * Created by m.hemdan on 9/18/17.
 */
@ViewScope
@Component(dependencies = AppComponent.class,
        modules = {UserProfileModule.class,
                UsersListUseCaseModule.class})
public interface UserProfileComponent {
    void inject(UsersProfileView view);
}
