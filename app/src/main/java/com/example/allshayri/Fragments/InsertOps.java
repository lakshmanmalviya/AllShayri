package com.example.allshayri.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.allshayri.Activities.InsertPhotos;
import com.example.allshayri.Activities.InsertTopic;
import com.example.allshayri.Activities.ShowTopics;
import com.example.allshayri.Activities.addBackImage;
import com.example.allshayri.R;
import com.example.allshayri.databinding.FragmentInsertOpsBinding;

public class InsertOps extends Fragment {
    FragmentInsertOpsBinding bnd;
    public InsertOps() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bnd= FragmentInsertOpsBinding.inflate(inflater,container,false);
        bnd.insertTopics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getContext(),InsertTopic.class);
                intent.putExtra("allTopic","allTopic");
                startActivity(intent);
            }
        });
        bnd.insertForYouTopics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getContext(),InsertTopic.class);
                intent.putExtra("allTopic","ForYouTopic");
                startActivity(intent);
            }
        });
        bnd.insertContents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ShowTopics.class));
            }
        });
        bnd.insertBacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), addBackImage.class));
            }
        });
        bnd.insertImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), InsertPhotos.class));
            }
        });

        return bnd.getRoot();
    }
}