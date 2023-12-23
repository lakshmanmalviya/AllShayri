package com.example.allshayri.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.allshayri.Activities.SeeTopContent;
import com.example.allshayri.Adapters.TopCAdapter;
import com.example.allshayri.Adapters.TopicAdapter;
import com.example.allshayri.Classes.RecyclerItemClickListener;
import com.example.allshayri.Modals.TopContentModal;
import com.example.allshayri.Modals.TopicModal;
import com.example.allshayri.R;
import com.example.allshayri.databinding.FragmentHomeBinding;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class Home extends Fragment {
    Handler handler;
    FragmentHomeBinding bnd;
    ArrayList<TopicModal> tlist;
    ArrayList<TopContentModal> tlist2;
    TopicAdapter adapter;
    TopCAdapter adapter2;
    FirebaseDatabase database;
    GridLayoutManager gridLayoutManager,gridLayoutManager2;
    String allTopic = "allTopic",topContent = "topContent",topicId = "-NOUHTa0xeKaS_hMzuNW";
    //    private InterstitialAd mInterstitialAd;
//    AdRequest adRequest;
    int hiderec = 0;

    public Home() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bnd = FragmentHomeBinding.inflate(inflater, container, false);
        handler = new Handler();
        // Create a Runnable to change text color every 10 seconds
        Runnable colorChangeRunnable = new Runnable() {
            @Override
            public void run() {
                if (!isInternetAvailable(getContext())) {
                    bnd.inet.setText("Internet is not Available \n Please turn on data");
                    bnd.inet.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Internet is not available", Toast.LENGTH_SHORT).show();
                }
                handler.postDelayed(this, 10000); // Run the Runnable again after 10 seconds
            }
        };

        // Start the color change process
        handler.post(colorChangeRunnable);
        bnd.textView.setText("close to see the clear pic of shayries");
        // checking connection continuously
        //  checkingConnection();
//        adRequest = new AdRequest.Builder().build();
//        InterstitialAd.load(getContext(), "ca-app-pub-3650621484566387/5770713146", adRequest,
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
//        if (mInterstitialAd != null) {
//            mInterstitialAd.show(getActivity());
//        } else {
//            mInterstitialAd = null;
//        }
        database = FirebaseDatabase.getInstance();
        tlist = new ArrayList<>();
        Collections.shuffle(tlist);
        adapter = new TopicAdapter(tlist, getContext());
        bnd.homeRec.setAdapter(adapter);
        gridLayoutManager = new GridLayoutManager(getContext(), 3);
        bnd.homeRec.setLayoutManager(gridLayoutManager);
//        for rec two
        tlist2 = new ArrayList<>();
        Collections.shuffle(tlist2);
        adapter2 = new TopCAdapter(tlist2, getContext());
        bnd.homeRecTwo.setAdapter(adapter2);
        gridLayoutManager2 = new GridLayoutManager(getContext(), 1);
        bnd.homeRecTwo.setLayoutManager(gridLayoutManager2);
//
        database.getReference().child(allTopic).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    tlist.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        TopicModal topicModal = dataSnapshot.getValue(TopicModal.class);
                        tlist.add(topicModal);
                    }
                    adapter.notifyDataSetChanged();
                    bnd.progressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getContext(), "Doesn't exits", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        database.getReference().child(allTopic).child(topicId).child(topContent).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    tlist2.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        TopContentModal topicModal = dataSnapshot.getValue(TopContentModal.class);
                        tlist2.add(topicModal);
                    }
                    Collections.shuffle(tlist2);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Doesn't exits", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        bnd.seeRecLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hiderec == 0) {
                    bnd.textView.setText("Click to see the  topics");
                    bnd.imageView.setImageResource(R.drawable.right);
                    bnd.homeRec.setVisibility(View.GONE);
                    hiderec = 1;
                } else {
                    bnd.textView.setText("close to see the clear pic of shayries");
                    bnd.imageView.setImageResource(R.drawable.wrong);
                    bnd.homeRec.setVisibility(View.VISIBLE);
                    hiderec = 0;
                }
            }
        });
        bnd.homeRec.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), bnd.homeRec, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TopicModal topicModal = tlist.get(position);
                Intent intent = new Intent(getContext(), SeeTopContent.class);
                intent.putExtra("topicId", topicModal.getTopicId());
                intent.putExtra("allTopic", allTopic);
                getContext().startActivity(intent);
//                if (mInterstitialAd != null) {
//                    mInterstitialAd.show(getActivity());
//                } else {
//                    mInterstitialAd = null;
//                }
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        return bnd.getRoot();
    }

    public boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
//    public void checkingConnection(){
////        Thread t = new Thread(){
////            @Override
////            public void run() {
////                try {
////                    bnd.inet.setVisibility(View.VISIBLE);
////                    if(!isInternetAvailable(getContext())){
////                        bnd.inet.setText("checking connection......");
////                    }else{
////                        bnd.inet.setText("Not checking connection......");
////                    }
////                    sleep(3000);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
////            }
////        }; t.start();
////        new Handler().postDelayed(new Runnable() {
////            @Override
////            public void run() {
////                bnd.inet.setVisibility(View.VISIBLE);
////                if(!isInternetAvailable(getContext())){
////                    bnd.inet.setText("checking connection......");
////                }else{
////                    bnd.inet.setText("Not checking connection......");
////                }
////            }
////        },3000);
//    }
}