package com.activeminders.onlinegymapp.Trainer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.activeminders.onlinegymapp.Adapters.GymsAdapter;
import com.activeminders.onlinegymapp.Adapters.TrainerMembershipsAdapter;
import com.activeminders.onlinegymapp.LoginActivity;
import com.activeminders.onlinegymapp.Models.Gyms;
import com.activeminders.onlinegymapp.Models.TrainerMemberships;
import com.activeminders.onlinegymapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TrainerMainActivity extends AppCompatActivity {

    RecyclerView trainerRecyclerview;
    List<TrainerMemberships> trainerMembershipsList;
    DatabaseReference TrainerRef;
    TrainerMembershipsAdapter trainerMembershipsAdapter;
    TrainerMemberships trainerMemberships;
    String currentTrainer;

    ImageView logout;

    public static final String PREFS_NAME = "LoginPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_main);

        logout=findViewById(R.id.trainerlogout);
        trainerRecyclerview=findViewById(R.id.trainermembershipsRV);
        trainerRecyclerview.setHasFixedSize(true);
        trainerRecyclerview.setLayoutManager(new LinearLayoutManager(TrainerMainActivity.this));
        trainerMembershipsList=new ArrayList<>();
        currentTrainer=FirebaseAuth.getInstance().getCurrentUser().getUid();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Signout();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        TrainerRef= FirebaseDatabase.getInstance().getReference().child("Gyms");
        TrainerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                trainerMembershipsList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String gymkey=dataSnapshot.getKey();
                    TrainerRef.child(gymkey).child("Trainers").child(currentTrainer).child("Memberships").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                for (DataSnapshot membershipsnapshot:snapshot.getChildren()){
                                    trainerMemberships=membershipsnapshot.getValue(TrainerMemberships.class);
                                    trainerMembershipsList.add(trainerMemberships);
                                    Toast.makeText(TrainerMainActivity.this, ""+trainerMembershipsList.size(), Toast.LENGTH_SHORT).show();
                                }
                                trainerMembershipsAdapter=new TrainerMembershipsAdapter(trainerMembershipsList,TrainerMainActivity.this);
                                trainerRecyclerview.setAdapter(trainerMembershipsAdapter);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    public void Signout(){
        FirebaseAuth.getInstance().signOut();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove("trainerlogged");
        editor.commit();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
        Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_LONG).show();
    }
}