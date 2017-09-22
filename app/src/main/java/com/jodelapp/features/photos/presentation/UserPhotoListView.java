package com.jodelapp.features.photos.presentation;

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
import com.jodelapp.features.photos.models.AlbumPresentationModel;
import com.jodelapp.features.photos.models.PhotoPresentationModel;
import com.jodelapp.utilities.database.DataBaseHelper;
import com.jodelapp.views.activities.MainActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class UserPhotoListView extends BaseFragment implements UserPhotoListContract.View {


    @Inject
    UserPhotoListContract.Presenter presenter;

    private UserPhotoListComponent scopeGraph;
    private Unbinder unbinder;

    @BindView(R.id.ls_user_albums)
    RecyclerView lsUserAlbums;
    @BindView(R.id.empty_view)
    View emptyView;
    @BindView(R.id.offline_view)
    View offlineView;
    @Inject
    DataBaseHelper dataBaseHelper;
    private UserAlbumsAdapter adapter;
    public static UserPhotoListView getInstance(){
        return new UserPhotoListView();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_photos, container, false);
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

    private void setupScopeGraph(AppComponent appComponent) {
        scopeGraph = DaggerUserPhotoListComponent.builder()
                .appComponent(appComponent)
                .userPhotoListModule(new UserPhotoListModule(this))
                .build();
        scopeGraph.inject(this);
    }

    private void initViews() {
        lsUserAlbums.setLayoutManager(new LinearLayoutManager(getActivity()));
        lsUserAlbums.setHasFixedSize(true);
    }

    @Override
    public void loadAlbumsList(List<AlbumPresentationModel> providers) {
        emptyView.setVisibility(View.GONE);
        offlineView.setVisibility(View.GONE);
        lsUserAlbums.setVisibility(View.VISIBLE);
        adapter = new UserAlbumsAdapter(providers);
        adapter.setPresenter(presenter);
        lsUserAlbums.setAdapter(adapter);
    }

    @Override
    public void loadPhotosList(List<PhotoPresentationModel> providers,int albumIndex) {
        if(adapter!=null)
            adapter.setAlbumPhotos(albumIndex,providers);
    }

    @Override
    public void showEmptyView() {
        emptyView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        lsUserAlbums.setVisibility(View.GONE);
    }

    @Override
    public void showOfflineView() {
        offlineView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        lsUserAlbums.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        super.showError(message);
        if(!NetworkUtils.isConnected()){
           showOfflineView();
        }
    }
}
