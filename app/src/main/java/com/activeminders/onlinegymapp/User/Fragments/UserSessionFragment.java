package com.activeminders.onlinegymapp.User.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.activeminders.onlinegymapp.Adapters.GymSessionAdapter;
import com.activeminders.onlinegymapp.Adapters.UserSessionAdapater;
import com.activeminders.onlinegymapp.Models.Session;
import com.activeminders.onlinegymapp.R;
import com.activeminders.onlinegymapp.User.SearchSessionActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class UserSessionFragment extends Fragment {

    //Show list of Session code
    private RecyclerView sessionsrecyclerview;
    private List<Session> sessionsList;
    private List<String> List;
    private DatabaseReference sessionsRf;
    private Session session;
    private UserSessionAdapater adapter;
    String currentUser;
    RelativeLayout searchlayout;

    public UserSessionFragment() {
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
        View root= inflater.inflate(R.layout.fragment_session, container, false);

        searchlayout=root.findViewById(R.id.searchlayout);
        sessionsrecyclerview=root.findViewById(R.id.sessionRV);
        sessionsrecyclerview.setHasFixedSize(true);
        sessionsrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        sessionsList=new ArrayList<>();
        List=new ArrayList<>();
        currentUser= FirebaseAuth.getInstance().getCurrentUser().getUid();

        sessionsRf= FirebaseDatabase.getInstance().getReference().child("Gyms");

        sessionsRf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot gymsSnapshot:snapshot.getChildren()){
                        for (DataSnapshot sessionSnapshot:gymsSnapshot.child("Sessions").getChildren()){

                            session=sessionSnapshot.getValue(Session.class);
                            sessionsList.add(session);
                        }
                    }
                    adapter=new UserSessionAdapater(sessionsList,getContext());
                    sessionsrecyclerview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        searchlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SearchSessionActivity.class));
            }
        });

        return root;
    }
}