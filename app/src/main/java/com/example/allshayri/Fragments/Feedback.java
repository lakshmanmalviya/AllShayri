package com.example.allshayri.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.allshayri.R;
import com.example.allshayri.databinding.FragmentFeedbackBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Feedback extends Fragment {
    FragmentFeedbackBinding bnd;
    FirebaseDatabase myDatabase;
    DatabaseReference myref;
    String userFeeds = "userFeeds";
    String userMsg = "userMsg";
    String userName = "userName";
    String userId = "userId";
    public Feedback() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bnd =  FragmentFeedbackBinding.inflate(inflater, container, false);
        myDatabase = FirebaseDatabase.getInstance();
        bnd.insertFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bnd.userMsg.getText().toString().trim().isEmpty()){
                    Toast.makeText(getContext(), "The msg should not empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    myref = myDatabase.getReference().child(userFeeds).push();
                    Map<String,Object> map = new HashMap<>();
                    map.put(userName, bnd.userName.getText().toString());
                    map.put(userMsg, bnd.userMsg.getText().toString());
                    map.put(userId,myref.getKey());
                    myref.setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getContext(), "Your message has reached to us 😍 🤩", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        return  bnd.getRoot();
    }
}