package com.jodelapp.utilities.database;

import com.jodelapp.di.scope.ActivityScope;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by m.hemdan on 8/21/17.
 */
@Module
public class DataBaseModule {
    @Provides
    @ActivityScope
    public DataBaseHelper provideDataBaseHelper(DataBaseHelperImplementation dataBaseHelperImplementation){
        return dataBaseHelperImplementation;
    }
}
