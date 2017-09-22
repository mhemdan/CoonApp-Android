package com.jodelapp.views.activities;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.jodelapp.App;
import com.jodelapp.AppComponent;
import com.jodelapp.R;
import com.jodelapp.base.BaseActivity;
import com.jodelapp.features.photos.presentation.UserPhotoListView;
import com.jodelapp.features.profile.model.UserPresentationModel;
import com.jodelapp.features.profile.presentation.UsersProfileView;
import com.jodelapp.features.todos.presentation.UserTodoListView;
import com.jodelapp.utilities.database.DataBaseHelper;
import com.jodelapp.utilities.database.DataBaseModule;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity implements MainActivityContract.View, OnTabSelectListener {

    @Inject
    MainActivityContract.Presenter presenter;

    @BindView(R.id.tb_app)
    Toolbar tbApp;

    @BindView(R.id.bottomBar)
    public BottomBar bottomBar;
    @BindView(R.id.txt_toolbar_title)
    TextView txtToolbarTitle;

    private MainActivityComponent scopeGraph;
    private String currentUserID = "1";



    @Override
    public void loadToDoPage() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.v_container, UsersProfileView.getInstance())
                .commit();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupScopeGraph(App.get(this).getAppComponent());
        initViews();
        presenter.onCreate();
    }

    public void setTitle(String title){
        txtToolbarTitle.setText(title);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }


    private void setupScopeGraph(AppComponent appComponent) {
        scopeGraph = DaggerMainActivityComponent.builder()
                .appComponent(appComponent)
                .mainActivityModule(new MainActivityModule(this))
                .build();
        scopeGraph.inject(this);
    }

    private void initViews() {
        ButterKnife.bind(this);
        setSupportActionBar(tbApp);
        bottomBar.setOnTabSelectListener(this);
    }

    @Override
    public void onTabSelected(@IdRes int tabId) {
        switch (tabId){
            case R.id.tab_user:
                txtToolbarTitle.setText(getResources().getString(R.string.app_bottom_navigation_profile));
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.v_container, UsersProfileView.getInstance())
                        .commit();
                break;
            case R.id.tab_photos:
                txtToolbarTitle.setText(getResources().getString(R.string.app_bottom_navigation_photos));
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.v_container, UserPhotoListView.getInstance())
                        .commit();
                break;
            case R.id.tab_tasks:
                txtToolbarTitle.setText(getResources().getString(R.string.app_bottom_navigation_tasks));
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.v_container, UserTodoListView.getInstance())
                        .commit();
                break;
        }
    }

}
