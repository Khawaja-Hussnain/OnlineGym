package com.activeminders.onlinegymapp.Gym.Fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.activeminders.onlinegymapp.Adapters.AssignTrainerAdapter;
import com.activeminders.onlinegymapp.Adapters.TrainersAdapter;
import com.activeminders.onlinegymapp.Gym.AddTrainerActivity;
import com.activeminders.onlinegymapp.Gym.SearchGymTrainersActivity;
import com.activeminders.onlinegymapp.Models.Trainers;
import com.activeminders.onlinegymapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SelectTrainerActivity extends AppCompatActivity {

    private RecyclerView trainersrecyclerview;
    private List<Trainers> trainersList;
    private DatabaseReference trainersRef;
    private Trainers trainers;
    private AssignTrainerAdapter adapter;
    String currentGym;
    RelativeLayout trainersearchlayout;

    //Collect member data coming from GymMembershipsAdapter
   public String membername,memberid,membership,membershipkey,membershipdate,gymid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_trainer);

        membername=getIntent().getStringExtra("membername");
        memberid=getIntent().getStringExtra("memberid");
        membership=getIntent().getStringExtra("membership");
        membershipkey=getIntent().getStringExtra("membershipkey");
        membershipdate=getIntent().getStringExtra("membershipdate");


        trainersearchlayout=findViewById(R.id.trainersearchlayout);
        trainersrecyclerview=findViewById(R.id.selecttrainerRV);
        trainersrecyclerview.setHasFixedSize(true);
        trainersrecyclerview.setLayoutManager(new LinearLayoutManager(SelectTrainerActivity.this));
        trainersList=new ArrayList<>();
        currentGym= FirebaseAuth.getInstance().getCurrentUser().getUid();

        trainersRef= FirebaseDatabase.getInstance().getReference().child("Gyms").child(currentGym).child("Trainers");
        trainersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        trainers=dataSnapshot.child("Info").getValue(Trainers.class);
                        trainersList.add(trainers);
                    }
                    adapter=new AssignTrainerAdapter(trainersList,SelectTrainerActivity.this,membername,
                            memberid,membership,membershipkey,membershipdate);
                    trainersrecyclerview.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /*trainersearchlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectTrainerActivity.this, SearchGymTrainersActivity.class));
            }
        });*/
    }
}