package com.jodelapp;


import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.Utils;

import io.paperdb.Paper;

public class App extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initComponents();
        init();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }



    private void initComponents() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        appComponent.inject(this);
    }

    private void init(){
        Paper.init(this);
        Utils.init(this);
    }
}
