package com.example.ttcs_final.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.ttcs_final.Fragment.FragmentDanhSach;
import com.example.ttcs_final.Fragment.FragmentProfile;
import com.example.ttcs_final.Fragment.FragmentSearch;
import com.example.ttcs_final.Fragment.FragmentThongTin;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentDanhSach();
            case 1:
                return new FragmentThongTin();
            case 2:
                return new FragmentSearch();
            case 3:
                return new FragmentProfile();
            default:
                new FragmentDanhSach();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
//    public int getCount() {
//        return 3;
//    }
}
