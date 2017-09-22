package com.jodelapp.views.activities;


import com.jodelapp.base.BaseView;

public interface MainActivityContract {

    interface View extends BaseView {

        void loadView();
    }

    interface Presenter {

        void onCreate();

        void onDestroy();
    }
}
