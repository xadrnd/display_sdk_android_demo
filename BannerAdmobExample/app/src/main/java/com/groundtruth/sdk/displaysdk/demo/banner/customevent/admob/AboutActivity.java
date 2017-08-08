package com.groundtruth.sdk.displaysdk.demo.banner.customevent.admob;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class AboutActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.groundtruth.sdk.displaysdk.demo.banner.customevent.admob.R.layout.content_about);
        Toolbar toolbar = (Toolbar) findViewById(com.groundtruth.sdk.displaysdk.demo.banner.customevent.admob.R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView aboutVersion = (TextView)findViewById(com.groundtruth.sdk.displaysdk.demo.banner.customevent.admob.R.id.about_version);
        aboutVersion.setText("SDK Version: " + com.xad.sdk.BuildConfig.VERSION_NAME);
    }
}
