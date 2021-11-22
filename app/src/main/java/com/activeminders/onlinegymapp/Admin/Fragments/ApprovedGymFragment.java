package com.activeminders.onlinegymapp.Admin.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activeminders.onlinegymapp.Adapters.AdminApprovedGymAdpater;
import com.activeminders.onlinegymapp.Adapters.AdminNewGymAdapter;
import com.activeminders.onlinegymapp.Models.Gyms;
import com.activeminders.onlinegymapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ApprovedGymFragment extends Fragment {

    RecyclerView GymsRecyclerview;
    List<Gyms> gymsList;
    DatabaseReference gymRef;
    AdminApprovedGymAdpater gymsAdapter;
    Gyms gyms;

    public ApprovedGymFragment() {
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
        View view=inflater.inflate(R.layout.fragment_approved, container, false);

        GymsRecyclerview=view.findViewById(R.id.approvedgymsRV);
        GymsRecyclerview.setHasFixedSize(true);
        GymsRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        gymsList=new ArrayList<>();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        gymRef= FirebaseDatabase.getInstance().getReference().child("Gyms");
        gymRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    gymsList.clear();
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        if (dataSnapshot.child("GymInfo").child("status").getValue().equals("approved")) {
                            gyms = dataSnapshot.child("GymInfo").getValue(Gyms.class);
                            gymsList.add(gyms);
                        }
                    }
                }
                gymsAdapter=new AdminApprovedGymAdpater(gymsList,getContext());
                GymsRecyclerview.setAdapter(gymsAdapter);
                gymsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}