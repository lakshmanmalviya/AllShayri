package com.example.allshayri.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.allshayri.Adapters.TopicAdapter;
import com.example.allshayri.Classes.RecyclerItemClickListener;
import com.example.allshayri.Modals.TopicModal;
import com.example.allshayri.R;
import com.example.allshayri.databinding.ActivityShowTopicsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowTopics extends AppCompatActivity {
  ActivityShowTopicsBinding bnd;
  ArrayList<TopicModal>tlist;
  ArrayList<TopicModal>tlist2;
  TopicAdapter adapter;
  TopicAdapter adapter2;
  FirebaseDatabase database;
  GridLayoutManager gridLayoutManager;
  GridLayoutManager gridLayoutManager2;
  String allTopic = "allTopic";
  String ForYouTopic = "ForYouTopic";
  String topicId = "topicId";
  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityShowTopicsBinding.inflate(getLayoutInflater());
        database= FirebaseDatabase.getInstance();
        setContentView(bnd.getRoot());
        tlist = new ArrayList<>();
        tlist2 = new ArrayList<>();
        adapter = new TopicAdapter(tlist,getApplicationContext());
        adapter2 = new TopicAdapter(tlist2,getApplicationContext());
        bnd.showTopicRec.setAdapter(adapter);
        bnd.showTopicRec2.setAdapter(adapter2);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        gridLayoutManager2 = new GridLayoutManager(getApplicationContext(),3);
        bnd.showTopicRec.setLayoutManager(gridLayoutManager);
        bnd.showTopicRec2.setLayoutManager(gridLayoutManager2);
        database.getReference().child(allTopic).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    tlist.clear();
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                       TopicModal topicModal = dataSnapshot.getValue(TopicModal.class);
                       tlist.add(topicModal);
                    }
                    adapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Doesn't exits", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
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
                  Toast.makeText(getApplicationContext(), "Doesn't exits", Toast.LENGTH_SHORT).show();
              }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {
              Toast.makeText(getApplicationContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
          }
      });
     bnd.showTopicRec.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), bnd.showTopicRec, new RecyclerItemClickListener.OnItemClickListener() {
         @Override
         public void onItemClick(View view, int position) {
             TopicModal topicModal = tlist.get(position);
             Toast.makeText(getApplicationContext(), ""+topicModal.getTopicName(), Toast.LENGTH_SHORT).show();
             Intent intent = new Intent(getApplicationContext(),AddContentTopic.class);
             intent.putExtra(topicId,topicModal.getTopicId());
             intent.putExtra("allTopic",allTopic);
             intent.putExtra("topicName",topicModal.getTopicName());
             startActivity(intent);
         }
         @Override
         public void onLongItemClick(View view, int position) {

         }
     }));
     bnd.showTopicRec2.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), bnd.showTopicRec2, new RecyclerItemClickListener.OnItemClickListener() {
         @Override
         public void onItemClick(View view, int position) {
             TopicModal topicModal = tlist2.get(position);
             Toast.makeText(getApplicationContext(), ""+topicModal.getTopicName(), Toast.LENGTH_SHORT).show();
             Intent intent = new Intent(getApplicationContext(),AddContentTopic.class);
             intent.putExtra(topicId,topicModal.getTopicId());
             intent.putExtra("allTopic",ForYouTopic);
             intent.putExtra("topicName",topicModal.getTopicName());
             startActivity(intent);
         }
         @Override
         public void onLongItemClick(View view, int position) {

         }
     }));
    }
}