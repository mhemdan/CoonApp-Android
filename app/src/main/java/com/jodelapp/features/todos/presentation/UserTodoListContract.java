package com.jodelapp.features.todos.presentation;


import com.jodelapp.base.BaseView;
import com.jodelapp.features.todos.models.TodoPresentationModel;

import java.util.List;

public interface UserTodoListContract {

    interface View extends BaseView {

        void loadToDoList(List<TodoPresentationModel> providers);
    }

    interface Presenter {

        void onAttached();
        void setUserID(String userID);
        void onDetached();
    }
}
