package com.jodelapp.utilities.database;

import android.content.Context;

import com.jodelapp.features.profile.model.UserPresentationModel;

import javax.inject.Inject;

import io.paperdb.Paper;

/**
 * Created by m.hemdan on 8/20/17.
 */

public class DataBaseHelperImplementation implements DataBaseHelper{

    @Inject
    public DataBaseHelperImplementation(){

    }

    @Override
    public void saveCurrentUser(UserPresentationModel userModel) {
        Paper.book().write(DataBaseConstants.USER,userModel);
    }

    @Override
    public void updateCurrrentUser(UserPresentationModel userModel) {
        saveCurrentUser(userModel);
    }


    @Override
    public boolean isThereActiveUser() {
        return  Paper.book().exist(DataBaseConstants.USER);
    }

    @Override
    public UserPresentationModel getCurrentUser() {
            if(isThereActiveUser()){
                return Paper.book().read(DataBaseConstants.USER);
            }
            return null;
    }

    @Override
    public void Destroy() {
        Paper.book().destroy();
    }


}
