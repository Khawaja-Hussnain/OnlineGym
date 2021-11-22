package com.activeminders.onlinegymapp.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.activeminders.onlinegymapp.LoginActivity;
import com.activeminders.onlinegymapp.R;
import com.activeminders.onlinegymapp.User.Fragments.ActivityFragment;
import com.activeminders.onlinegymapp.User.Fragments.HomeFragment;
import com.activeminders.onlinegymapp.User.Fragments.ProfileFragment;
import com.activeminders.onlinegymapp.User.Fragments.UserSessionFragment;
import com.google.firebase.auth.FirebaseAuth;

public class UserMainActivity extends AppCompatActivity {

    LinearLayout home,activity,session,profile;
    public static final String PREFS_NAME = "LoginPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         home=findViewById(R.id.homelayout);
         activity=findViewById(R.id.activitylayout);
         session=findViewById(R.id.sessiolayout);
         profile=findViewById(R.id.profilelayuot);

        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.Frame,new HomeFragment()).commit();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager=getSupportFragmentManager();
                FragmentTransaction transaction=manager.beginTransaction();
                transaction.replace(R.id.Frame,new HomeFragment()).commit();
            }
        });
        activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager=getSupportFragmentManager();
                FragmentTransaction transaction=manager.beginTransaction();
                transaction.replace(R.id.Frame,new ActivityFragment()).commit();
            }
        });
        session.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager=getSupportFragmentManager();
                FragmentTransaction transaction=manager.beginTransaction();
                transaction.replace(R.id.Frame,new UserSessionFragment()).commit();
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager=getSupportFragmentManager();
                FragmentTransaction transaction=manager.beginTransaction();
                transaction.replace(R.id.Frame,new ProfileFragment()).commit();
            }
        });

    }

    public void Signout(){
        FirebaseAuth.getInstance().signOut();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove("logged");
        editor.commit();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

}