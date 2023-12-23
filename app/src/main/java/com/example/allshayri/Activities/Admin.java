package com.example.allshayri.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.allshayri.Fragments.DeleteOps;
import com.example.allshayri.Fragments.InsertOps;
import com.example.allshayri.R;
import com.example.allshayri.databinding.ActivityAdminBinding;

public class Admin extends AppCompatActivity {
  ActivityAdminBinding bnd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        replaceFrag(new InsertOps());
        bnd.insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFrag(new InsertOps());

            }
        });
        bnd.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFrag(new DeleteOps());

            }
        });
        bnd.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFrag(new DeleteOps());

            }
        });

    }
    public void replaceFrag(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.replaceFrag,fragment);
        transaction.commit();
    }
}