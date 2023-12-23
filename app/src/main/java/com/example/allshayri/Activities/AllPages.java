package com.example.allshayri.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.allshayri.Adapters.FragAdapter;
import com.example.allshayri.R;
import com.example.allshayri.databinding.ActivityAllPagesBinding;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.tabs.TabLayout;

public class AllPages extends AppCompatActivity {
    ActivityAllPagesBinding bnd;

  //    private InterstitialAd mInterstitialAd;
  //    AdRequest adRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityAllPagesBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        adRequest = new AdRequest.Builder().build();
//        InterstitialAd.load(this,"ca-app-pub-3650621484566387/5770713146", adRequest,
//                new InterstitialAdLoadCallback() {
//                    @Override
//                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
//                        mInterstitialAd = interstitialAd;
//                    }
//
//                    @Override
//                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                        mInterstitialAd = null;
//                    }
//                });
//        if (mInterstitialAd!=null){
//            mInterstitialAd.show(AllPages.this);
//         }
//        else{
//            mInterstitialAd =null;
//        }

        bnd.viewPager.setAdapter(new FragAdapter(getSupportFragmentManager()));
        bnd.tabLayout.setupWithViewPager(bnd.viewPager);
        bnd.tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}