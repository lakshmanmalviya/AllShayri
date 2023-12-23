package com.example.allshayri.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.allshayri.Fragments.Add;
import com.example.allshayri.Fragments.Feedback;
import com.example.allshayri.Fragments.ForYou;
import com.example.allshayri.Fragments.Home;
import com.example.allshayri.Fragments.Photos;
import com.example.allshayri.Fragments.Status;
import com.example.allshayri.Fragments.Videos;

public class FragAdapter extends FragmentPagerAdapter {

    public FragAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public FragAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return  new Home();
            case 1:
                return  new ForYou();
            case 2:
                return  new Photos();
            case 3:
                return  new Status();
            case 4:
                return  new Videos();
            case 5:
                return  new Add();
            case 6:
                return  new Feedback();
            default: return new Home();
        }

    }

    @Override
    public int getCount() {
        return 7;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
//        return super.getPageTitle(position);
        String title = null;
        switch (position){
            case 0:
                title = "Home";
                break;
            case 1:
                title = "ForYou";
                break;
            case 2:
                title = "Photos";
                break;
            case 3:
                title = "Status";
                break;
            case 4:
                title = "All Favorite";
                break;
            case 5:
                title = "Add";
                break;
            case 6:
                title = "Feedback";
                break;
            default: title = "Home";
        }
        return  title;

    }
}
