package com.jodelapp.views.activities;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.jodelapp.App;
import com.jodelapp.AppComponent;
import com.jodelapp.R;
import com.jodelapp.features.photos.presentation.UserPhotoListView;
import com.jodelapp.features.todos.presentation.UserTodoListView;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements MainActivityContract.View, OnTabSelectListener {

    @Inject
    MainActivityContract.Presenter presenter;

    @BindView(R.id.tb_app)
    Toolbar tbApp;

    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    private MainActivityComponent scopeGraph;

    @Override
    public void loadToDoPage() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.v_container, UserTodoListView.getInstance())
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
            case R.id.tab_photos:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.v_container, UserPhotoListView.getInstance())
                        .commit();
                break;
            case R.id.tab_user:
                break;
            default:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.v_container, UserTodoListView.getInstance())
                        .commit();
                break;
        }
    }
}
