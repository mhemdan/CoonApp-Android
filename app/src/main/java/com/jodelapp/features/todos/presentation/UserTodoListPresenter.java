package com.jodelapp.features.todos.presentation;


import android.util.Log;

import com.jodelapp.features.todos.usecases.GetTodoListByUser;
import com.jodelapp.utilities.rx.RxDisposableFactory;
import com.jodelapp.utilities.rx.RxDisposables;
import com.jodelapp.utilities.rx.ThreadTransformer;

import javax.inject.Inject;

public final class UserTodoListPresenter implements UserTodoListContract.Presenter {

    private String USER_ID = "1";

    private final UserTodoListContract.View view;
    private final GetTodoListByUser getTodoListByUser;
    private final ThreadTransformer threadTransformer;
    private final RxDisposables disposables;

    @Inject
    public UserTodoListPresenter(UserTodoListContract.View view,
                                 GetTodoListByUser getTodoListByUser,
                                 ThreadTransformer threadTransformer,
                                 RxDisposableFactory factory) {
        this.view = view;
        this.getTodoListByUser = getTodoListByUser;
        this.threadTransformer = threadTransformer;
        this.disposables = factory.get();
    }

    @Override
    public void onAttached() {
        // here all time will return viewTransformer which is logic as it will be provided by dagger
        disposables.add(getTodoListByUser.call(USER_ID)
                .compose(threadTransformer.applySchedulers())
                .subscribe(
                        todos -> view.loadToDoList(todos),
                        error -> Log.e("UserToDo", error.getMessage())
                ));
    }

    @Override
    public void setUserID(String userID) {
        this.USER_ID = userID;
    }

    @Override
    public void onDetached() {
        disposables.clear();
    }
}
