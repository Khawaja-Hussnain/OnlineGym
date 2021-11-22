package com.activeminders.onlinegymapp.Gym;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeminders.onlinegymapp.LoginActivity;
import com.activeminders.onlinegymapp.R;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DrawerMainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    //User Define work start from here.......................................................
    DatabaseReference mRef;
    String gymName,gymEmail,Uid;
    public static final String PREFS_NAME = "LoginPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       /* FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_machines, R.id.nav_Session,R.id.nav_Trainers,R.id.nav_profile_gym)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //User Define work start from here.......................................................

        View hview=navigationView.getHeaderView(0);
        TextView name=hview.findViewById(R.id.gymname);
        TextView email=hview.findViewById(R.id.gymemail);
        ImageView logo=hview.findViewById(R.id.imageView);

        mRef=FirebaseDatabase.getInstance().getReference().child("Gyms");
        Uid= FirebaseAuth.getInstance().getCurrentUser().getUid();

        mRef.child(Uid).child("GymInfo").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              gymName=snapshot.child("gymname").getValue().toString();
               gymEmail=snapshot.child("email").getValue().toString();
                String profileurl=snapshot.child("profileimage").getValue().toString();
                if (!profileurl.equals("default")){
                    Glide.with(DrawerMainActivity.this).load(profileurl).into(logo);
                }
                else
                {
                    logo.setImageResource(R.drawable.user);
                }
                name.setText(gymName);
                email.setText(gymEmail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_logout:
               Signout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void Signout(){
        FirebaseAuth.getInstance().signOut();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove("gymlogged");
        editor.commit();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
        Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_LONG).show();
    }

}