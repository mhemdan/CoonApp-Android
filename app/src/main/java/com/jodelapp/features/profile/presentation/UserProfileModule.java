package com.jodelapp.features.profile.presentation;

import com.jodelapp.features.todos.presentation.UserTodoListContract;
import com.jodelapp.utilities.database.DataBaseHelper;
import com.jodelapp.utilities.database.DataBaseHelperImplementation;

import dagger.Module;
import dagger.Provides;

/**
 * Created by m.hemdan on 9/18/17.
 */
@Module
public class UserProfileModule {
    private final UserProfileContract.View view;

    public UserProfileModule(UserProfileContract.View view) {
        this.view = view;
    }

    @Provides
    public UserProfileContract.View provideView() {
        return this.view;
    }

    @Provides
    public UserProfileContract.Presenter providePresenter(UserProfilePresenter presenter) {
        return presenter;
    }

//    @Provides
//    public DataBaseHelper provideDataBaseHelper(){
//        return new DataBaseHelperImplementation();
//    }
}
