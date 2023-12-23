package com.example.allshayri.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.allshayri.Fragments.Home;
import com.example.allshayri.Fragments.Photos;
import com.example.allshayri.R;
import com.example.allshayri.databinding.ActivityNavBarBinding;
import com.google.android.material.navigation.NavigationView;

public class NavBar extends AppCompatActivity {
   ActivityNavBarBinding bnd;
   DrawerLayout drawerLayout ;
   NavigationView navView;
   Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityNavBarBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
          drawerLayout = findViewById(R.id.drawer);
          navView = findViewById(R.id.navView);
          toolbar  = findViewById(R.id.toolBar);
                 setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,
                toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id= item.getItemId();
                if (id==R.id.notes){
                    replaceFrag(new Home());
                }else if(id == R.id.photo){
                    replaceFrag(new Photos());
                } else{
                    Toast.makeText(getApplicationContext(), "Ban gya ", Toast.LENGTH_SHORT).show();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
    public void replaceFrag(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.repFragment,fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{ super.onBackPressed();
        }
    }
}