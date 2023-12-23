package com.example.allshayri.Activities;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.allshayri.R;
import com.example.allshayri.databinding.ActivityInsertPhotosBinding;
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

public class InsertPhotos extends AppCompatActivity {
   ActivityInsertPhotosBinding bnd;
    ActivityResultLauncher<String> launcher;
    FirebaseDatabase database;
    FirebaseStorage storage;
    DatabaseReference myref;
    String imageUrl ="";
    String allImage ="allImage";
    String image ="image";
    String imageId ="imageId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityInsertPhotosBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        setContentView(bnd.getRoot());
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uri) {
                bnd.image.setImageURI(uri);
                final StorageReference reference = storage.getReference().child(allImage).child(new Date().getTime()+".png");
                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
//                             Toast.makeText(getApplicationContext(), "ImageURL Assigned", Toast.LENGTH_SHORT).show();
                                imageUrl = uri.toString();
                                bnd.insertImg.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                });
            }
        });
        bnd.browImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bnd.insertImg.setVisibility(View.GONE);
                launcher.launch("image/*");
            }
        });
        bnd.insertImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUrl.isEmpty()){
                    Toast.makeText(getApplicationContext(), "The area is empty", Toast.LENGTH_SHORT).show();
                }

                else{
                    myref = database.getReference().child(allImage).push();
                    Map<String,Object> map = new HashMap<>();
                    map.put(imageId,myref.getKey());
                    map.put(image,imageUrl);
                    myref.setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "Inserted 😎 🤩 😊", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Insertion Failed 😋 🤔 🙄"+e, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}