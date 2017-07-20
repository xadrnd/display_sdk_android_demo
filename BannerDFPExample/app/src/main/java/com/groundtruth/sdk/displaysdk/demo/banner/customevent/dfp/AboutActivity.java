package com.groundtruth.sdk.displaysdk.demo.banner.customevent.dfp;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class AboutActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView aboutVersion = (TextView)findViewById(R.id.about_version);
        aboutVersion.setText("SDK Version: " + com.xad.sdk.BuildConfig.VERSION_NAME);
    }
}
