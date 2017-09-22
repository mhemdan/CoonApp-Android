package com.jodelapp.features.photos.presentation;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.jodelapp.R;
import com.jodelapp.views.activities.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by m.hemdan on 9/22/17.
 */
@RunWith(AndroidJUnit4.class)
public class UserPhotoListViewTest {
    @Rule
    public final ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp() throws Exception {
    }
    @Test
    public void loadAlbumsList() throws Exception {

    }

    @Test
    public void loadPhotosList() throws Exception {

    }

}