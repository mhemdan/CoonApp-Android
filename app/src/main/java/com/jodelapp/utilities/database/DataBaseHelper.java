package com.jodelapp.utilities.database;

import com.jodelapp.features.profile.model.UserPresentationModel;


/**
 * Created by m.hemdan on 8/21/17.
 */

public interface DataBaseHelper {
    void saveCurrentUser(UserPresentationModel userModel);
    void updateCurrrentUser(UserPresentationModel userModel);
    boolean isThereActiveUser();
    UserPresentationModel getCurrentUser();
    void Destroy();
}
