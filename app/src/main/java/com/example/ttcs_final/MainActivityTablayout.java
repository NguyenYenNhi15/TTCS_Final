package com.example.ttcs_final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.ttcs_final.Adapter.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivityTablayout extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tablayout);
        tabLayout=findViewById(R.id.tab);
        viewPager=findViewById(R.id.viewPager);
        adapter=new FragmentAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_list);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_info);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_search);
        tabLayout.getTabAt(3).setIcon(R.drawable.user);
    }
}