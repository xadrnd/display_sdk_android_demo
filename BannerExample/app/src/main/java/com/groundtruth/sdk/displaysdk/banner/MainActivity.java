package com.groundtruth.sdk.displaysdk.banner;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.xad.sdk.AdRequest;
import com.xad.sdk.BannerView;
import com.xad.sdk.DisplaySdk;
import com.xad.sdk.ErrorCode;
import com.xad.sdk.listeners.BannerViewListener;
import com.xad.sdk.utils.Logger;

public class MainActivity extends BaseActivity implements BannerViewListener {
    private BannerView bannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DisplaySdk.sharedInstance().init(this);

        //Only show warning log message, default none
        Logger.setLevel(Logger.Level.WARNING);

        //Create banner and set up
        this.bannerView = (BannerView)findViewById(R.id.banner_view);
        //Please go to 'productFlavors' in build.gradle to change with a valid access key if you want to get ad from live campaign
        this.bannerView.setAccessKey(BuildConfig.ACCESS_KEY);
        AdRequest adRequest = new AdRequest.Builder()
//Uncomment this to see test creative from our test channel, which doesn't require access key
//                .setTestMode(true)
                .build();
        this.bannerView.setAdRequest(adRequest);
        this.bannerView.setAdListener(this);
        FloatingActionButton loadAdButton = (FloatingActionButton) findViewById(R.id.load_banner_button);
        loadAdButton.setVisibility(View.VISIBLE);
        loadAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bannerView.loadAd();
            }
        });

        //Request permissions at runtime. All permission is required per MRAID spec.
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                android.Manifest.permission.READ_CONTACTS,
                android.Manifest.permission.WRITE_CONTACTS,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_CALENDAR,
                android.Manifest.permission.SEND_SMS,
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.CALL_PHONE,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.WAKE_LOCK};

        if(!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }

    private boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && context != null
                && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            Intent aboutIntent = new Intent(this, AboutActivity.class);
            startActivity(aboutIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //BannerViewListener callbacks implementation
    @Override
    public void onAdLoaded(BannerView bannerView) {
        Logger.logDebug("MainActivity", "Banner is loaded");
    }

    @Override
    public void onAdFetchFailed(BannerView bannerView, ErrorCode code) {
        Logger.logDebug("MainActivity", "Banner has failed to load, with error: " + code.description);
    }

    @Override
    public void onAdClosed(BannerView bannerView) {

    }

    @Override
    public void onAdOpened(BannerView bannerView) {

    }

    @Override
    public void onAdLeftApplication(BannerView bannerView) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        DisplaySdk.sharedInstance().resume();
        if(bannerView != null) {
            bannerView.resume();
        }
    }

    @Override
    protected void onPause() {
        DisplaySdk.sharedInstance().pause();
        if(bannerView != null) {
            bannerView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        DisplaySdk.sharedInstance().destroy();
        if(bannerView != null) {
            bannerView.destroy();
        }
        super.onDestroy();
    }
}
