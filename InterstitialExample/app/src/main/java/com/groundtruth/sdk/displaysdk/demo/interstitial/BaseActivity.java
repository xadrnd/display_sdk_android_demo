package com.groundtruth.sdk.displaysdk.demo.interstitial;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Ray.Wu on 7/17/17.
 * Copyright (c) 2016 xAd. All rights reserved.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View mainLayout = getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout content_container = (FrameLayout) mainLayout.findViewById(R.id.content_container);
        getLayoutInflater().inflate(layoutResID, content_container, true);
        super.setContentView(mainLayout);
    }
}
