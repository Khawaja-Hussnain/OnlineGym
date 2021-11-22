package com.activeminders.onlinegymapp.Gym.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.activeminders.onlinegymapp.Adapters.GymMembershipsAdapter;
import com.activeminders.onlinegymapp.Models.Gyms;
import com.activeminders.onlinegymapp.Models.Memberships;
import com.activeminders.onlinegymapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class GymHomeFragment extends Fragment {

    TextView tv_gymname;
    String gymname,currentGym;

    RecyclerView membershipsRecyclerview;
    List<Memberships> membershipsList;
    DatabaseReference membershipsRef;
    GymMembershipsAdapter adapter;
    Memberships memberships;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home_gym, container, false);

        membershipsRecyclerview=root.findViewById(R.id.membershipsRV);
        membershipsRecyclerview.setHasFixedSize(true);
        membershipsRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        membershipsList=new ArrayList<>();
        currentGym=FirebaseAuth.getInstance().getCurrentUser().getUid();

        membershipsRef=FirebaseDatabase.getInstance().getReference().child("Gyms").child(currentGym).child("Memberships").child("GymMemberships");
        membershipsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        memberships=dataSnapshot.getValue(Memberships.class);
                        membershipsList.add(memberships);
                    }
                    adapter=new GymMembershipsAdapter(membershipsList,getContext());
                    membershipsRecyclerview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), ""+membershipsList.size(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        FirebaseDatabase.getInstance().getReference().child("Gyms").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("GymInfo")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        tv_gymname=root.findViewById(R.id.gymname);
                        gymname=snapshot.child("gymname").getValue().toString();
                        tv_gymname.setText("Hello "+gymname+"!");
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        return root;
    }
}