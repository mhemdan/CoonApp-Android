package com.jodelapp.features.todos.usecases;


import android.support.annotation.NonNull;

import com.jodelapp.data.api.ApiService;
import com.jodelapp.features.todos.models.TodoPresentationModel;

import java.util.List;

import javax.inject.Inject;
import javax.xml.transform.TransformerFactory;

import io.reactivex.Single;

public interface GetTodoListByUser {

    Single<List<TodoPresentationModel>> call(@NonNull String userId);
}

final class GetTodoListByUserImpl implements GetTodoListByUser {

    private final ApiService apiService;

    @Inject
    public GetTodoListByUserImpl(ApiService apiService) {
        this.apiService = apiService;
    }


    @Override
    public Single<List<TodoPresentationModel>> call(@NonNull String userId) {
        //todo Don't know how but there are no use of the work thread need some clarification here
        return apiService.getToDos(userId)
                .flatMapIterable(todos -> todos)
                .map(toDo -> new TodoPresentationModel(String.valueOf(toDo.getId()), toDo.getTitle(),
                        toDo.getCompleted() ? "done" : "todo"))
                .toList();
    }
}
