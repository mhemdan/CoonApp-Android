package com.jodelapp.features.profile.presentation;

import com.jodelapp.features.profile.model.UserPresentationModel;

import java.util.List;

/**
 * Created by m.hemdan on 9/18/17.
 */

public interface UserProfileContract {
    interface View {
        void loadUsers(List<UserPresentationModel> providers);
    }

    interface Presenter {
        void onAttached();

        void onDetached();
    }
}
