package com.jodelapp.features.todos.presentation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.NetworkUtils;
import com.jodelapp.App;
import com.jodelapp.AppComponent;
import com.jodelapp.R;
import com.jodelapp.base.BaseFragment;
import com.jodelapp.features.todos.models.TodoPresentationModel;
import com.jodelapp.utilities.database.DataBaseHelper;
import com.jodelapp.views.activities.MainActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class UserTodoListView extends BaseFragment implements UserTodoListContract.View {

    @Inject
    UserTodoListContract.Presenter presenter;

    @BindView(R.id.ls_user_todos)
    RecyclerView lsUserToDos;
    @BindView(R.id.empty_view)
    View emptyView;
    @BindView(R.id.offline_view)
    View offlineView;

    private UserTodoListComponent scopeGraph;
    private Unbinder unbinder;

    @Inject
    DataBaseHelper dataBaseHelper;
    public static UserTodoListView getInstance() {
        return new UserTodoListView();
    }

//    private static final String USER_ID_KEY = "USER_ID_KEY";

//    public static UserTodoListView getInstance(String userID) {
//        Bundle bundle = new Bundle();
//        bundle.putString(USER_ID_KEY,userID);
//        UserTodoListView fragment = new UserTodoListView();
//        fragment.setArguments(bundle);
//        return fragment;
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todos, container, false);
        setupScopeGraph(App.get(getActivity()).getAppComponent());
        unbinder = ButterKnife.bind(this, view);
        initViews();
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.setUserID(dataBaseHelper.getCurrentUser().getId());
        presenter.onAttached();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDetached();
        unbinder.unbind();
    }

    @Override
    public void loadToDoList(List<TodoPresentationModel> toDos) {
        lsUserToDos.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        offlineView.setVisibility(View.GONE);
        UserTodoListAdapter adapter = new UserTodoListAdapter(toDos);
        lsUserToDos.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyView() {
        lsUserToDos.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        offlineView.setVisibility(View.GONE);
    }

    @Override
    public void showOfflineView() {
        lsUserToDos.setVisibility(View.GONE);
        offlineView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
    }

    private void initViews() {
        lsUserToDos.setLayoutManager(new LinearLayoutManager(getActivity()));
        lsUserToDos.setHasFixedSize(true);
    }


    private void setupScopeGraph(AppComponent appComponent) {
        scopeGraph = DaggerUserTodoListComponent.builder()
                .appComponent(appComponent)
                .userTodoListModule(new UserTodoListModule(this))
                .build();
        scopeGraph.inject(this);
    }

    @Override
    public void showError(String message) {
        super.showError(message);
        if(!NetworkUtils.isConnected()){
            showOfflineView();
        }
    }

    @OnClick(R.id.btn_retry)
    public void onBtnRetryClick(View view){
        presenter.onAttached();
    }
}
