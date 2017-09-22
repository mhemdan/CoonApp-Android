package com.jodelapp;


import android.content.Context;
import android.content.res.Resources;

import com.jodelapp.data.DataComponent;
import com.jodelapp.data.DataModule;
import com.jodelapp.utilities.UtilsComponent;
import com.jodelapp.utilities.UtilsModule;
import com.jodelapp.utilities.database.DataBaseHelper;
import com.jodelapp.utilities.database.DataBaseModule;
import com.jodelapp.views.activities.MainActivity;
import com.jodelapp.views.activities.MainActivityModule;

import org.greenrobot.eventbus.EventBus;

import java.sql.DatabaseMetaData;
import java.util.Locale;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, UtilsModule.class,  DataModule.class,DataBaseModule.class})
public interface AppComponent extends UtilsComponent, DataComponent {

    void inject(App app);

    // expose dependencies to scope graph
    Context exposeAppContext();

    Resources exposeResources();

    Locale exposeLocale();

    EventBus exposeBus();

    DataBaseHelper exposeDataBaseHelper();

}
