package com.example.allshayri.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.allshayri.databinding.ActivityAddContentTopicBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddContentTopic extends AppCompatActivity {
  ActivityAddContentTopicBinding bnd;
    FirebaseDatabase myDatabase;
    DatabaseReference myref;
    String allTopic = "allTopic";
    String topicId = "topicId";
    String shayri = "shayri";
    String shayriId = "shayriId";
    String topContent = "topContent";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityAddContentTopicBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        allTopic = getIntent().getStringExtra(allTopic);
        topicId = getIntent().getStringExtra(topicId);
        bnd.topicTextName.setText("You are adding the new Content to "+getIntent().getStringExtra("topicName"));
        myDatabase = FirebaseDatabase.getInstance();
        bnd.insertContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bnd.contentName.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "The something is empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    myref = myDatabase.getReference().child(allTopic).child(topicId).child(topContent).push();
                    Map<String,Object> map = new HashMap<>();
                    map.put(shayri, bnd.contentName.getText().toString());
                    map.put(shayriId,myref.getKey());
                    myref.setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "The content is inserted 😍 🤩", Toast.LENGTH_SHORT).show();
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