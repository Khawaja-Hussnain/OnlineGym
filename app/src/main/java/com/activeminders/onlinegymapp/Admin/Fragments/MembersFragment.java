package com.activeminders.onlinegymapp.Admin.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.activeminders.onlinegymapp.Adapters.GymMembershipsAdapter;
import com.activeminders.onlinegymapp.Admin.AdminMainActivity;
import com.activeminders.onlinegymapp.Models.Adminvalues;
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


public class MembersFragment extends Fragment {

    RecyclerView adminmembershipsRecyclerview;
    List<Memberships> membershipsList;
    DatabaseReference membershipsRef;
    GymMembershipsAdapter adapter;
    Memberships memberships;

    public MembersFragment() {
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
        View view=inflater.inflate(R.layout.fragment_members, container, false);

        String gymid=getArguments().getString("gymid");

        adminmembershipsRecyclerview=view.findViewById(R.id.adminmemberRV);
        adminmembershipsRecyclerview.setHasFixedSize(true);
        adminmembershipsRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        membershipsList=new ArrayList<>();


        membershipsRef= FirebaseDatabase.getInstance().getReference().child("Gyms").child(gymid).child("Memberships").child("GymMemberships");
        membershipsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        memberships=dataSnapshot.getValue(Memberships.class);
                        membershipsList.add(memberships);
                    }
                    adapter=new GymMembershipsAdapter(membershipsList,getContext());
                    adminmembershipsRecyclerview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}