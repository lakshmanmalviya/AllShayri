package com.example.allshayri.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.allshayri.Activities.SeeTopContent;
import com.example.allshayri.Adapters.TopCAdapter;
import com.example.allshayri.Adapters.TopicAdapter;
import com.example.allshayri.Classes.RecyclerItemClickListener;
import com.example.allshayri.Modals.TopContentModal;
import com.example.allshayri.Modals.TopicModal;
import com.example.allshayri.R;
import com.example.allshayri.databinding.FragmentForYouBinding;
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

public class ForYou extends Fragment {
  FragmentForYouBinding bnd;
    ArrayList<TopicModal> tlist;
    ArrayList<TopContentModal> tlist2;
    TopicAdapter adapter;
    TopCAdapter adapter2;
    FirebaseDatabase database;
    GridLayoutManager gridLayoutManager;
    GridLayoutManager gridLayoutManager2;
    String allTopic = "ForYouTopic";
    String topContent = "topContent";
    String topicId = "-NP63h-A9M7D98m8Pf-S";
    private InterstitialAd mInterstitialAd;
    AdRequest adRequest;
   public ForYou() {   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       bnd =  FragmentForYouBinding.inflate(inflater, container, false);

        adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(getContext(),"ca-app-pub-3650621484566387/5770713146", adRequest,
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
        if (mInterstitialAd!=null){
            mInterstitialAd.show(getActivity());
        }
        else{
            mInterstitialAd =null;
        }
        database= FirebaseDatabase.getInstance();
        tlist = new ArrayList<>();
        Collections.shuffle(tlist);
        adapter = new TopicAdapter(tlist,getContext());
        bnd.forYouRec.setAdapter(adapter);
        gridLayoutManager = new GridLayoutManager(getContext(),3);
        bnd.forYouRec.setLayoutManager(gridLayoutManager);
//        for rec two
        tlist2 = new ArrayList<>();
        adapter2 = new TopCAdapter(tlist2,getContext());
        bnd.forRecTwo.setAdapter(adapter2);
        gridLayoutManager2 = new GridLayoutManager(getContext(),1);
        bnd.forRecTwo.setLayoutManager(gridLayoutManager2);
//
        database.getReference().child(allTopic).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        TopicModal topicModal = dataSnapshot.getValue(TopicModal.class);
                        tlist.add(topicModal);
                    }
                    adapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(getContext(), "Doesn't exits", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        database.getReference().child(allTopic).child(topicId).child(topContent).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        TopContentModal topicModal = dataSnapshot.getValue(TopContentModal.class);
                        tlist2.add(topicModal);
                    }
                    Collections.shuffle(tlist2);
                    adapter.notifyDataSetChanged();
                    bnd.progressBar3.setVisibility(View.GONE);
                }
                else{
                    Toast.makeText(getContext(), "Doesn't exits", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        bnd.forYouRec.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), bnd.forYouRec, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TopicModal topicModal = tlist.get(position);
                Intent intent = new Intent(getContext(), SeeTopContent.class);
                intent.putExtra("topicId",topicModal.getTopicId());
                intent.putExtra("allTopic",allTopic);
                getContext().startActivity(intent);
                if (mInterstitialAd!=null){
                    mInterstitialAd.show(getActivity());
                }
                else{
                    mInterstitialAd =null;
                }
            }
            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
       return bnd.getRoot();
    }
}