package com.jodelapp.utilities.database;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by m.hemdan on 8/21/17.
 */
@Module
public class DataBaseModule {
    @Provides
    @Singleton
    public DataBaseHelper provideDataBaseHelper(DataBaseHelperImplementation dataBaseHelperImplementation){
        return dataBaseHelperImplementation;
    }
}
