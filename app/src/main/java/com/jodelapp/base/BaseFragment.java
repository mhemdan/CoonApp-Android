package com.jodelapp.base;

import android.support.v4.app.Fragment;

/**
 * Created by m.hemdan on 9/22/17.
 */

public class BaseFragment extends Fragment implements BaseView {
    @Override
    public void showError(String message) {
        ((BaseActivity)getActivity()).showError(message);
    }

    @Override
    public void showLoading() {
        ((BaseActivity)getActivity()).showLoading();
    }

    @Override
    public void hideLoading() {
        ((BaseActivity)getActivity()).hideLoading();
    }
}
