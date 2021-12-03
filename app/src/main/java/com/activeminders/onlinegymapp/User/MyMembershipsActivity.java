package com.activeminders.onlinegymapp.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.activeminders.onlinegymapp.Adapters.GymMembershipsAdapter;
import com.activeminders.onlinegymapp.Adapters.UserMembershipsAdapter;
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

public class MyMembershipsActivity extends AppCompatActivity {

    RecyclerView membershipsRecyclerview;
    List<Memberships> membershipsList;
    DatabaseReference membershipsRef;
    UserMembershipsAdapter adapter;
    Memberships memberships;
    String currentMem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_memberships);

        membershipsRecyclerview=findViewById(R.id.memRV);
        membershipsRecyclerview.setHasFixedSize(true);
        membershipsRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        membershipsList=new ArrayList<>();
        currentMem= FirebaseAuth.getInstance().getCurrentUser().getUid();

        membershipsRef= FirebaseDatabase.getInstance().getReference().child("Users").child(currentMem).child("Memberships").child("GymMemberships");
        membershipsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        memberships=dataSnapshot.getValue(Memberships.class);
                        membershipsList.add(memberships);
                    }
                    adapter=new UserMembershipsAdapter(membershipsList,MyMembershipsActivity.this);
                    membershipsRecyclerview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MyMembershipsActivity.this, ""+membershipsList.size(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void back(View view) {
        finish();
    }
}