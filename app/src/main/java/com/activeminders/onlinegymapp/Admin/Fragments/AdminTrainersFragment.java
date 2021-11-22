package com.activeminders.onlinegymapp.Admin.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.activeminders.onlinegymapp.Adapters.AdminTrainerAdapter;
import com.activeminders.onlinegymapp.Adapters.TrainersAdapter;
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


public class AdminTrainersFragment extends Fragment {

    private RecyclerView trainersrecyclerview;
    private List<Trainers> trainersList;
    private DatabaseReference trainersRef;
    private Trainers trainers;
    private AdminTrainerAdapter adapter;

    public AdminTrainersFragment() {
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
        View view= inflater.inflate(R.layout.fragment_trainer_admin, container, false);

        String gymid=getArguments().getString("gymid");

        trainersrecyclerview=view.findViewById(R.id.admintrainerRV);
        trainersrecyclerview.setHasFixedSize(true);
        trainersrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        trainersList=new ArrayList<>();
        //gymid= FirebaseAuth.getInstance().getCurrentUser().getUid();

        trainersRef= FirebaseDatabase.getInstance().getReference().child("Gyms").child(gymid).child("Trainers");
        trainersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        trainers=dataSnapshot.getValue(Trainers.class);
                        trainersList.add(trainers);
                    }
                    adapter=new AdminTrainerAdapter(trainersList,getContext());
                    trainersrecyclerview.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}