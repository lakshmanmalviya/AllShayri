package com.example.allshayri.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.allshayri.Adapters.ImageAdapter;
import com.example.allshayri.Classes.RecyclerItemClickListener;
import com.example.allshayri.Modals.ImageModal;
import com.example.allshayri.R;
import com.example.allshayri.databinding.FragmentStatusBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class Status extends Fragment {
    FragmentStatusBinding bnd;
    ArrayList<ImageModal> tlist;
    ImageAdapter adapter;
    FirebaseDatabase database;
    GridLayoutManager gridLayoutManager;
    String allImage = "allImage";
 public Status() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bnd = FragmentStatusBinding.inflate(inflater,container,false);
        database= FirebaseDatabase.getInstance();
        tlist = new ArrayList<>();
        Collections.shuffle(tlist);
        adapter = new ImageAdapter(tlist,getContext());
        bnd.statusRec.setAdapter(adapter);
        gridLayoutManager = new GridLayoutManager(getContext(),1);
        bnd.statusRec.setLayoutManager(gridLayoutManager);
        database.getReference().child(allImage).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        ImageModal topicModal = dataSnapshot.getValue(ImageModal.class);
                        tlist.add(topicModal);
                    }
                    adapter.notifyDataSetChanged();
                    bnd.progressBar2.setVisibility(View.GONE);
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