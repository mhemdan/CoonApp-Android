package com.jodelapp.features.profile.presentation;

import android.util.Log;

import com.jodelapp.features.profile.usercases.GetUsersList;
import com.jodelapp.utilities.rx.RxDisposableFactory;
import com.jodelapp.utilities.rx.RxDisposables;
import com.jodelapp.utilities.rx.ThreadTransformer;

import javax.inject.Inject;

/**
 * Created by m.hemdan on 9/18/17.
 */

public class UserProfilePresenter implements UserProfileContract.Presenter{
    private static final String USER_ID = "1";

    private final UserProfileContract.View view;
    private final GetUsersList getUsersList;
    private final ThreadTransformer threadTransformer;
    private final RxDisposables disposables;

    @Inject
    public UserProfilePresenter(UserProfileContract.View view,
                                 GetUsersList getUsersList,
                                 ThreadTransformer threadTransformer,
                                 RxDisposableFactory factory) {
        this.view = view;
        this.getUsersList = getUsersList;
        this.threadTransformer = threadTransformer;
        this.disposables = factory.get();
    }

    @Override
    public void onAttached() {
        // here all time will return viewTransformer which is logic as it will be provided by dagger
        disposables.add(getUsersList.call()
                .compose(threadTransformer.applySchedulers())
                .subscribe(
                        users ->{
                            if(users.size()>0)
                                view.loadUsers(users);
                            else
                                view.showEmptyView();
                        },
                        error ->view.showError(error.getMessage())
                ));
    }

    @Override
    public void onDetached() {
        disposables.clear();
    }
}
