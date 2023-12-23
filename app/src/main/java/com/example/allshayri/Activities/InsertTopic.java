package com.example.allshayri.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.allshayri.databinding.ActivityInsertTopicBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class InsertTopic extends AppCompatActivity {
  ActivityInsertTopicBinding bnd;
    String allTopic = "allTopic";
    FirebaseDatabase myDatabase;
    DatabaseReference myref;
    String topicName ="topicName";
    String topicId ="topicId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityInsertTopicBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        allTopic = getIntent().getStringExtra(allTopic);
        myDatabase = FirebaseDatabase.getInstance();
        bnd.insertTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bnd.topicName.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "The something is empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    myref = myDatabase.getReference().child(allTopic).push();
                    Map<String,Object> map = new HashMap<>();
                    map.put(topicName, bnd.topicName.getText().toString());
                    map.put(topicId,myref.getKey());
                    myref.setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "The topic is inserted 😍 🤩", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }
}