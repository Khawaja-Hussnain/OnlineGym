package com.activeminders.onlinegymapp.User.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.activeminders.onlinegymapp.Adapters.GymsAdapter;
import com.activeminders.onlinegymapp.Models.Gyms;
import com.activeminders.onlinegymapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    RecyclerView GymsRecyclerview;
    List<Gyms> gymsList;
    DatabaseReference gymRef;
    GymsAdapter gymsAdapter;
    Gyms gyms;

    public HomeFragment() {
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
       View view=inflater.inflate(R.layout.fragment_home, container, false);

       GymsRecyclerview=view.findViewById(R.id.gymsRV);
       GymsRecyclerview.setHasFixedSize(true);
       GymsRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
       gymsList=new ArrayList<>();
        TextView tv_Hello=view.findViewById(R.id.hellousertext);
       FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("UserInfo")
               .addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {

                       tv_Hello.setText("Hello "+snapshot.child("name").getValue().toString()+"!");
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });
       return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        gymRef= FirebaseDatabase.getInstance().getReference().child("Gyms");
        gymRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    gymsList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    if (dataSnapshot.child("GymInfo").child("status").getValue().equals("approved")) {
                        gyms = dataSnapshot.child("GymInfo").getValue(Gyms.class);
                        gymsList.add(gyms);
                        Toast.makeText(getContext(), "" + gymsList.size(), Toast.LENGTH_SHORT).show();
                    }
                }
                }
                gymsAdapter=new GymsAdapter(gymsList,getContext());
                GymsRecyclerview.setAdapter(gymsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}