package com.activeminders.onlinegymapp.Gym.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.activeminders.onlinegymapp.Adapters.MachinesAdapter;
import com.activeminders.onlinegymapp.Adapters.TrainersAdapter;
import com.activeminders.onlinegymapp.Gym.AddTrainerActivity;
import com.activeminders.onlinegymapp.Gym.SearchGymTrainersActivity;
import com.activeminders.onlinegymapp.Models.Machines;
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


public class TrainersFragment extends Fragment {

    private RecyclerView trainersrecyclerview;
    private List<Trainers> trainersList;
    private DatabaseReference trainersRef;
    private Trainers trainers;
    private TrainersAdapter adapter;
    String currentGym;
    RelativeLayout trainersearchlayout;

    AppCompatButton addtTrainer;

    public TrainersFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view=inflater.inflate(R.layout.fragment_trainers, container, false);


        addtTrainer=view.findViewById(R.id.addtrainer);
        trainersearchlayout=view.findViewById(R.id.trainersearchlayout);
        trainersrecyclerview=view.findViewById(R.id.trainerRV);
        trainersrecyclerview.setHasFixedSize(true);
        trainersrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
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
                    adapter=new TrainersAdapter(trainersList,getContext());
                    trainersrecyclerview.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       addtTrainer.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getContext(), AddTrainerActivity.class));
           }
       });
       trainersearchlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SearchGymTrainersActivity.class));
            }
        });
       return view;
    }
}