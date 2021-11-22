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

import com.activeminders.onlinegymapp.Adapters.AdminSessionsAdapter;
import com.activeminders.onlinegymapp.Adapters.GymSessionAdapter;
import com.activeminders.onlinegymapp.Models.Session;
import com.activeminders.onlinegymapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class SesstionsFragment extends Fragment {

    private RecyclerView sessionsrecyclerview;
    private List<Session> sessionsList;
    private DatabaseReference sessionsRf;
    private Session session;
    private AdminSessionsAdapter adapter;

    public SesstionsFragment() {
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
        View view= inflater.inflate(R.layout.fragment_sesstions, container, false);
        String gymid=getArguments().getString("gymid");

        sessionsrecyclerview=view.findViewById(R.id.adminsessionRV);
        sessionsrecyclerview.setHasFixedSize(true);
        sessionsrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        sessionsList=new ArrayList<>();

        sessionsRf= FirebaseDatabase.getInstance().getReference().child("Gyms").child(gymid).child("Sessions");

        sessionsRf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        session=dataSnapshot.getValue(Session.class);
                        sessionsList.add(session);
                    }
                    adapter=new AdminSessionsAdapter(sessionsList,getContext());
                    sessionsrecyclerview.setAdapter(adapter);
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