package com.activeminders.onlinegymapp.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.activeminders.onlinegymapp.Admin.Fragments.ApprovedGymFragment;
import com.activeminders.onlinegymapp.Admin.Fragments.NewGymFragment;
import com.activeminders.onlinegymapp.LoginActivity;
import com.activeminders.onlinegymapp.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class AdminMainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    ImageView logout;
    public static final String PREFS_NAME = "LoginPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        logout=findViewById(R.id.logout);

        tabLayout=findViewById(R.id.tablayout);
        viewPager=findViewById(R.id.adminviewpager);

        viewPagerAdpater viewpageradpater=new viewPagerAdpater(getSupportFragmentManager());
        viewpageradpater.addFragment(new NewGymFragment(),"New Gyms");
        viewpageradpater.addFragment(new ApprovedGymFragment(),"Approved Gyms");
        viewPager.setAdapter(viewpageradpater);
        tabLayout.setupWithViewPager(viewPager);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.remove("adminlogged");
                editor.commit();
                startActivity(new Intent(AdminMainActivity.this, LoginActivity.class));
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