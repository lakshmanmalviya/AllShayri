package com.example.allshayri.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.example.allshayri.Adapters.TopCAdapter;
import com.example.allshayri.Modals.TopContentModal;
import com.example.allshayri.databinding.ActivitySeeTopContentBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SeeTopContent extends AppCompatActivity {
    ActivitySeeTopContentBinding bnd;
    ArrayList<TopContentModal> tlist;
    TopCAdapter adapter;
    FirebaseDatabase database;
    GridLayoutManager gridLayoutManager;
    String allTopic = "allTopic";
    String nTopicId = "topicId";
    String topicId = "-NOPwSts1a7YjQP9Z53S";
    String topContent = "topContent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivitySeeTopContentBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        allTopic = getIntent().getStringExtra(allTopic);
        topicId = getIntent().getStringExtra(nTopicId);
        database = FirebaseDatabase.getInstance();
        tlist = new ArrayList<>();
        adapter = new TopCAdapter(tlist, getApplicationContext());
        bnd.seeTopRec.setAdapter(adapter);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        bnd.seeTopRec.setLayoutManager(gridLayoutManager);
        database.getReference().child(allTopic).child(topicId).child(topContent).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        TopContentModal topicModal = dataSnapshot.getValue(TopContentModal.class);
                        tlist.add(topicModal);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "Doesn't exits", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}