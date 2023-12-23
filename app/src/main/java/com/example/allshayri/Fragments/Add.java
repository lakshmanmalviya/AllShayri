package com.example.allshayri.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.allshayri.Activities.AddContentTopic;
import com.example.allshayri.Adapters.TopicAdapter;
import com.example.allshayri.Classes.RecyclerItemClickListener;
import com.example.allshayri.Modals.TopicModal;
import com.example.allshayri.databinding.FragmentAddBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Add extends Fragment {
    FragmentAddBinding bnd;
    ArrayList<TopicModal>tlist;
    ArrayList<TopicModal>tlist2;
    TopicAdapter adapter;
    TopicAdapter adapter2;
    FirebaseDatabase database;
    GridLayoutManager gridLayoutManager;
    GridLayoutManager gridLayoutManager2;
    String allTopic = "allTopic";
    String topicId = "topicId";
    String ForYouTopic = "ForYouTopic";

    public Add() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      bnd = FragmentAddBinding.inflate(inflater, container, false);
        database= FirebaseDatabase.getInstance();
        tlist = new ArrayList<>();
        tlist2 = new ArrayList<>();
        adapter = new TopicAdapter(tlist,getContext());
        adapter2 = new TopicAdapter(tlist2,getContext());
        bnd.addRec.setAdapter(adapter);
        bnd.addRec2.setAdapter(adapter2);
        gridLayoutManager = new GridLayoutManager(getContext(),3);
        gridLayoutManager2 = new GridLayoutManager(getContext(),3);
        bnd.addRec.setLayoutManager(gridLayoutManager);
        bnd.addRec2.setLayoutManager(gridLayoutManager2);
        database.getReference().child(allTopic).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
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

        database.getReference().child(ForYouTopic).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        TopicModal topicModal = dataSnapshot.getValue(TopicModal.class);
                        tlist2.add(topicModal);
                    }
                    adapter2.notifyDataSetChanged();
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
        bnd.addRec.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), bnd.addRec, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TopicModal topicModal = tlist.get(position);
                Intent intent = new Intent(getContext(), AddContentTopic.class);
                intent.putExtra(topicId,topicModal.getTopicId());
                intent.putExtra("topicName",topicModal.getTopicName());
                intent.putExtra("allTopic",allTopic);
                startActivity(intent);
            }
            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        bnd.addRec2.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), bnd.addRec2, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TopicModal topicModal = tlist2.get(position);
                Intent intent = new Intent(getContext(), AddContentTopic.class);
                intent.putExtra(topicId,topicModal.getTopicId());
                intent.putExtra("topicName",topicModal.getTopicName());
                intent.putExtra("allTopic",ForYouTopic);
                startActivity(intent);
            }
            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
      return  bnd.getRoot();
    }
}