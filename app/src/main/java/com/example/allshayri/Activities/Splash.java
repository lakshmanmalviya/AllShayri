package com.example.allshayri.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.allshayri.MainActivity;
import com.example.allshayri.R;
import com.example.allshayri.databinding.ActivitySplashBinding;

public class Splash extends AppCompatActivity {
  ActivitySplashBinding bnd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd  =ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        Thread thread = new Thread(){
           @Override
           public void run() {
               try {
                   sleep(5000);
               }catch (Exception e){
                   e.printStackTrace();
               }
               finally {
                   startActivity(new Intent(getApplicationContext(), MainActivity.class));
                   finish();
               }
           }
       };
       thread.start();
    }
}