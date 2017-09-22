package com.jodelapp.base;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.Utils;
import com.jodelapp.R;

/**
 * Created by m.hemdan on 9/22/17.
 */

public class BaseActivity extends AppCompatActivity implements BaseView {
    @Override
    public void showError(String message) {
        TSnackbar snackbar = TSnackbar.make(findViewById(android.R.id.content),
                NetworkUtils.isConnected()? message : getResources().getString(R.string.app_internet_connection_error)
                , TSnackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(this, R.color.app_snackbar_error_background));
        TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
