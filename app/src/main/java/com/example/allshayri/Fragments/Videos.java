package com.example.allshayri.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.allshayri.Adapters.TopCAdapter;
import com.example.allshayri.Adapters.TopicAdapter;
import com.example.allshayri.Modals.TopContentModal;
import com.example.allshayri.Modals.TopicModal;
import com.example.allshayri.R;
import com.example.allshayri.databinding.FragmentVideosBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class Videos extends Fragment {
 FragmentVideosBinding bnd;
    ArrayList<TopContentModal> tlist2;
    TopCAdapter adapter2;
    FirebaseDatabase database;
    GridLayoutManager gridLayoutManager2;
    String allFavs = "allFavs";
 public Videos() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       bnd =  FragmentVideosBinding.inflate(inflater, container, false);
       database= FirebaseDatabase.getInstance();
        tlist2 = new ArrayList<>();
        Collections.shuffle(tlist2);
        adapter2 = new TopCAdapter(tlist2,getContext());
        bnd.allFavRec.setAdapter(adapter2);
        gridLayoutManager2 = new GridLayoutManager(getContext(),1);
        bnd.allFavRec.setLayoutManager(gridLayoutManager2);
        database.getReference().child(allFavs).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        TopContentModal topicModal = dataSnapshot.getValue(TopContentModal.class);
                        tlist2.add(topicModal);
                    }
                    adapter2.notifyDataSetChanged();
                    bnd.progressBar4.setVisibility(View.GONE);
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
       return  bnd.getRoot();
    }
}