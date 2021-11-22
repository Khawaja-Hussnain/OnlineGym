package com.activeminders.onlinegymapp.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.activeminders.onlinegymapp.Admin.Fragments.ApprovedGymFragment;
import com.activeminders.onlinegymapp.Admin.Fragments.AdminTrainersFragment;
import com.activeminders.onlinegymapp.Admin.Fragments.MembersFragment;
import com.activeminders.onlinegymapp.Admin.Fragments.SesstionsFragment;
import com.activeminders.onlinegymapp.Gym.Fragments.TrainersFragment;
import com.activeminders.onlinegymapp.Models.Adminvalues;
import com.activeminders.onlinegymapp.R;
import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Member;
import java.util.ArrayList;

public class GymsDetailsActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    ImageView backarrow;
    Adminvalues adminvalues;

   String gymid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyms_details);

        gymid=getIntent().getStringExtra("GymId");


        tabLayout=findViewById(R.id.tablayout);
        viewPager=findViewById(R.id.adminviewpager);
        backarrow=findViewById(R.id.backarrow);

        MembersFragment membersFragment=new MembersFragment();
        AdminTrainersFragment trainersFragment=new AdminTrainersFragment();
        SesstionsFragment sesstionsFragment=new SesstionsFragment();
        Bundle bundle=new Bundle();
        bundle.putString("gymid",gymid);
        trainersFragment.setArguments(bundle);
        membersFragment.setArguments(bundle);
        sesstionsFragment.setArguments(bundle);

        viewPagerAdpater viewpageradpater=new viewPagerAdpater(getSupportFragmentManager());
        viewpageradpater.addFragment(trainersFragment,"Trainers");
        viewpageradpater.addFragment(membersFragment,"Members");
        viewpageradpater.addFragment(sesstionsFragment,"Sessions");
        viewPager.setAdapter(viewpageradpater);
        tabLayout.setupWithViewPager(viewPager);

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    class viewPagerAdpater extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        public viewPagerAdpater(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment, String title){

            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}