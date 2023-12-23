package com.example.allshayri.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.allshayri.databinding.ActivityAddBackImageBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class addBackImage extends AppCompatActivity {
    ActivityAddBackImageBinding bnd;
    ActivityResultLauncher<String> launcher;
    FirebaseDatabase database;
    FirebaseStorage storage;
    DatabaseReference myref;
    String imageUrl = "";
    String allBacks = "allBacks";
    String backImage = "backImage";
    String backImageId = "backImageId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityAddBackImageBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uri) {
                bnd.backImage.setImageURI(uri);
                final StorageReference reference = storage.getReference().child(allBacks).child("" + new Date().getTime() + ".png");
                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
//                             Toast.makeText(getApplicationContext(), "ImageURL Assigned", Toast.LENGTH_SHORT).show();
                                imageUrl = uri.toString();
                                bnd.insertBack.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                });
            }
        });
        bnd.browImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bnd.insertBack.setVisibility(View.GONE);
                launcher.launch("image/*");
            }
        });
        bnd.insertBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUrl.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "The area is empty", Toast.LENGTH_SHORT).show();
                } else {
                    // code goes here to insert sentences to a lesson
                    myref = database.getReference().child(allBacks).push();
                    Map<String, Object> map = new HashMap<>();
                    map.put(backImageId, myref.getKey());
                    map.put(backImage, imageUrl);
                    myref.setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "Inserted 😎 🤩 😊", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Insertion Failed 😋 🤔 🙄" + e, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}