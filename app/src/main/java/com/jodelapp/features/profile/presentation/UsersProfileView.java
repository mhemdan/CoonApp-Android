package com.jodelapp.features.profile.presentation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jodelapp.App;
import com.jodelapp.AppComponent;
import com.jodelapp.R;
import com.jodelapp.features.profile.model.UserPresentationModel;
import com.jodelapp.features.todos.presentation.DaggerUserTodoListComponent;
import com.jodelapp.features.todos.presentation.UserTodoListComponent;
import com.jodelapp.features.todos.presentation.UserTodoListModule;
import com.jodelapp.features.todos.presentation.UserTodoListView;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by m.hemdan on 9/18/17.
 */

public class UsersProfileView extends Fragment implements UserProfileContract.View {

    private UserProfileComponent scopeGraph;
    private Unbinder unbinder;

    @Inject
    UserProfilePresenter presenter;

    public static UsersProfileView getInstance() {
        return new UsersProfileView();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todos, container, false);
        setupScopeGraph(App.get(getActivity()).getAppComponent());
        unbinder = ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        presenter.onAttached();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDetached();
        unbinder.unbind();
    }

    @Override
    public void loadUsers(List<UserPresentationModel> providers) {

    }

    private void initViews(){

    }
    private void setupScopeGraph(AppComponent appComponent) {
        scopeGraph = DaggerUserProfileComponent.builder()
                .appComponent(appComponent)
                .userProfileModule(new UserProfileModule(this))
                .build();
        scopeGraph.inject(this);
    }
}
