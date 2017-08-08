package com.groundtruth.sdk.displaysdk.demo.interstitial;

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
import com.xad.sdk.InterstitialAd;
import com.xad.sdk.listeners.BannerViewListener;
import com.xad.sdk.listeners.InterstitialAdListener;
import com.xad.sdk.utils.Logger;

public class MainActivity extends BaseActivity implements InterstitialAdListener {

    private InterstitialAd interstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Only show warning log message, default is none
        Logger.setLevel(Logger.Level.DEBUG);

        FloatingActionButton loadAdButton = (FloatingActionButton) findViewById(R.id.load_banner_button);
        loadAdButton.setVisibility(View.VISIBLE);
        loadAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create banner and set up
                //Please go to 'productFlavors' in build.gradle to change the access key if you want to get ad from live campaign
                interstitial = new InterstitialAd(MainActivity.this, BuildConfig.ACCESS_KEY);
                AdRequest adRequest = new AdRequest.Builder()
                        .setGender(AdRequest.Gender.MALE)
                        .build();
                interstitial.setAdRequest(adRequest);
                interstitial.setAdListener(MainActivity.this);
                interstitial.loadAd();
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

    @Override
    public void onAdLoaded(InterstitialAd interstitialAd) {
        Logger.logDebug("MainActivity", "Interstitial is loaded, and about to be shown");
        if(interstitialAd != null) {
            interstitialAd.show();
        }
    }

    @Override
    public void onAdFetchFailed(InterstitialAd interstitialAd, ErrorCode code) {
        Logger.logDebug("MainActivity", "Interstitial has failed to load with error: " + code.description);
    }

    @Override
    public void onInterstitialShown(InterstitialAd interstitialAd) {

    }

    @Override
    public void onInterstitialFailedToShow(InterstitialAd interstitialAd) {

    }

    @Override
    public void onAdClosed(InterstitialAd interstitialAd) {

    }

    @Override
    public void onAdOpened(InterstitialAd interstitialAd) {

    }

    @Override
    public void onAdLeftApplication(InterstitialAd interstitialAd) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        DisplaySdk.sharedInstance().resume();
        if(interstitial != null) {
            interstitial.pause();
        }
    }

    @Override
    protected void onPause() {
        DisplaySdk.sharedInstance().pause();
        if(interstitial != null) {
            interstitial.pause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        DisplaySdk.sharedInstance().destroy();
        if(interstitial != null) {
            interstitial.destroy();
        }
        super.onDestroy();
    }
}
