package com.example.allshayri;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.allshayri.Activities.Admin;
import com.example.allshayri.Activities.AllPages;
import com.example.allshayri.databinding.ActivityMainBinding;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding bnd;
    private InterstitialAd mInterstitialAd;
    AdRequest adRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
//      PRDownloader.initialize(getApplicationContext());
        MobileAds.initialize(this);
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.FULL_BANNER);
        adView.setAdUnitId("ca-app-pub-3650621484566387/5638017058");
        adRequest = new AdRequest.Builder().build();
        bnd.adView.loadAd(adRequest);
        bnd.adView2.loadAd(adRequest);
        bnd.adView3.loadAd(adRequest);
        bnd.adView5.loadAd(adRequest);
        bnd.adView11.loadAd(adRequest);
        InterstitialAd.load(getApplicationContext(), "ca-app-pub-3650621484566387/5770713146", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mInterstitialAd = null;
                    }
                });

        bnd.admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Admin.class));
            }
        });
        bnd.user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AllPages.class));
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(MainActivity.this);
                } else {
                    mInterstitialAd = null;
                }
            }
        });
//        bnd.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////        bnd.imageView
//                // from View
//                Blurry.with(getApplicationContext()).capture(v).into(bnd.imageView);
//            }
//        });
    }
}
