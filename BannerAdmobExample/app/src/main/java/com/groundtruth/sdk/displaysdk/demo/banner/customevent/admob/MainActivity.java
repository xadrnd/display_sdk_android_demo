package com.groundtruth.sdk.displaysdk.demo.banner.customevent.admob;

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

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.xad.sdk.utils.Logger;

public class MainActivity extends BaseActivity {

    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.groundtruth.sdk.displaysdk.demo.banner.customevent.admob.R.layout.content_main);
        Toolbar toolbar = (Toolbar) findViewById(com.groundtruth.sdk.displaysdk.demo.banner.customevent.admob.R.id.toolbar);
        setSupportActionBar(toolbar);

        this.adView = (AdView) findViewById(com.groundtruth.sdk.displaysdk.demo.banner.customevent.admob.R.id.adView);
        this.adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
                Logger.logDebug("MainActivity", "Failed to show Admob banner");
            }

            @Override
            public void onAdLoaded() {
                Logger.logDebug("MainActivity", "banner is loaded, and about to be shown");
            }
        });

        FloatingActionButton loadAdButton = (FloatingActionButton) findViewById(com.groundtruth.sdk.displaysdk.demo.banner.customevent.admob.R.id.load_banner_button);
        loadAdButton.setVisibility(View.VISIBLE);
        loadAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create banner and set up
                AdRequest adRequest = new AdRequest.Builder().build();
                adView.loadAd(adRequest);
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
        getMenuInflater().inflate(com.groundtruth.sdk.displaysdk.demo.banner.customevent.admob.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == com.groundtruth.sdk.displaysdk.demo.banner.customevent.admob.R.id.action_about) {
            Intent aboutIntent = new Intent(this, AboutActivity.class);
            startActivity(aboutIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adView != null) {
            adView.resume();
        }
    }

    @Override
    protected void onPause() {
        if(adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if(adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}
