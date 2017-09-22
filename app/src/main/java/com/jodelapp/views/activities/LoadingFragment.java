package com.jodelapp.views.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.jodelapp.R;

/**
 * Created by m.hemdan on 9/18/17.
 */

public class LoadingFragment extends Fragment {
    public static String FRAGMENT_TAG = "LoadingFragmentDialog";
    @BindView(R.id.progress)
    DilatingDotsProgressBar progress;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_loading, null);
        ButterKnife.bind(this,view);
        startAnimation();
        return view;
    }

    private void startAnimation(){
        progress.showNow();

    }
}
