package com.jodelapp.features.profile.presentation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.NetworkUtils;
import com.jodelapp.App;
import com.jodelapp.AppComponent;
import com.jodelapp.R;
import com.jodelapp.base.BaseFragment;
import com.jodelapp.features.photos.presentation.UserPhotoAdapter;
import com.jodelapp.features.profile.model.UserPresentationModel;
import com.jodelapp.features.todos.presentation.DaggerUserTodoListComponent;
import com.jodelapp.features.todos.presentation.UserTodoListComponent;
import com.jodelapp.features.todos.presentation.UserTodoListModule;
import com.jodelapp.features.todos.presentation.UserTodoListView;
import com.jodelapp.utilities.database.DataBaseHelper;
import com.jodelapp.views.activities.MainActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by m.hemdan on 9/18/17.
 */

public class UsersProfileView extends BaseFragment implements UserProfileContract.View {

    private UserProfileComponent scopeGraph;
    private Unbinder unbinder;

    @BindView(R.id.ls_users)
    RecyclerView lsUsers;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_email)
    TextView txtEmail;
    @BindView(R.id.txt_phone)
    TextView txtPhone;
    @BindView(R.id.txt_website)
    TextView txtWebsite;
    @BindView(R.id.txt_company)
    TextView txtCompany;
    @BindView(R.id.txt_address)
    TextView txtAddress;
    @BindView(R.id.empty_view)
    View emptyView;
    @BindView(R.id.offline_view)
    View offlineView;
    @BindView(R.id.container_users)
    View containerUsers;

    @Inject
    UserProfilePresenter presenter;
    @Inject
    DataBaseHelper dataBaseHelper;





    public static UsersProfileView getInstance() {
        return new UsersProfileView();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        setupScopeGraph(App.get(getActivity()).getAppComponent());
        unbinder = ButterKnife.bind(this, view);
        initViews();
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.onAttached();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDetached();
        unbinder.unbind();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void loadUsers(List<UserPresentationModel> providers) {
        containerUsers.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        offlineView.setVisibility(View.GONE);
        lsUsers.setAdapter(new UsersListAdapter(providers));
        if(!dataBaseHelper.isThereActiveUser() && providers.size() > 0){
            updateCurrentUser(providers.get(0));
        }
    }

    @Override
    public void showEmptyView() {
        emptyView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        containerUsers.setVisibility(View.GONE);
    }

    @Override
    public void showOfflineView() {
        offlineView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        containerUsers.setVisibility(View.GONE);
    }

    private void initViews() {
        lsUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        lsUsers.setHasFixedSize(true);
        if(dataBaseHelper.isThereActiveUser())
            updateCurrentUser(dataBaseHelper.getCurrentUser());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateCurrentUser(UserPresentationModel userPresentationModel){
        txtName.setText(userPresentationModel.getName());
        txtEmail.setText(userPresentationModel.getEmail());
        txtPhone.setText(userPresentationModel.getPhone());
        txtCompany.setText(userPresentationModel.getCompany().getName());
        txtAddress.setText(userPresentationModel.getAddress().getCity()+
                "/" + userPresentationModel.getAddress().getStreet());
        txtWebsite.setText(userPresentationModel.getWebsite());
        dataBaseHelper.saveCurrentUser(userPresentationModel);
    }
    private void setupScopeGraph(AppComponent appComponent) {
        scopeGraph = DaggerUserProfileComponent.builder()
                .appComponent(appComponent)
                .userProfileModule(new UserProfileModule(this))
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

}
