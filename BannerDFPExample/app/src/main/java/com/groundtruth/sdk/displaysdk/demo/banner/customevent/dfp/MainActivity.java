package com.groundtruth.sdk.displaysdk.demo.banner.customevent.dfp;

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
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.xad.sdk.utils.Logger;

public class MainActivity extends BaseActivity {

    private PublisherAdView bannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.bannerView = (PublisherAdView)findViewById(R.id.publisherAdView);
        this.bannerView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
                Logger.logDebug("MainActivity", "Failed to show DFP banner");
            }

            @Override
            public void onAdLoaded() {
                Logger.logDebug("MainActivity", "banner is loaded, and about to be shown");
            }
        });

        FloatingActionButton loadAdButton = (FloatingActionButton) findViewById(R.id.load_banner_button);
        loadAdButton.setVisibility(View.VISIBLE);
        loadAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create banner and set up
                PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
                bannerView.loadAd(adRequest);
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
    protected void onResume() {
        super.onResume();
        if(bannerView != null) {
            bannerView.resume();
        }
    }

    @Override
    protected void onPause() {
        if(bannerView != null) {
            bannerView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if(bannerView != null) {
            bannerView.destroy();
        }
        super.onDestroy();
    }
}
