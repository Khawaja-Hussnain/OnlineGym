package com.activeminders.onlinegymapp.Gym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;

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

public class SearchGymSessionActivity extends AppCompatActivity {

    private RecyclerView sessionsrecyclerview;
    private List<Session> sessionsList;
    private Button addMachines;
    private DatabaseReference sessionsRf;
    private Session session;
    private GymSessionAdapter adapter;
    String currentGym,gymname,gymlogo;
    SearchView searchView;
    ImageView backarrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_gym_session);

        sessionsrecyclerview=findViewById(R.id.searchsessionRV);
        searchView=findViewById(R.id.searchview);
        backarrow=findViewById(R.id.backarr);
        sessionsrecyclerview.setHasFixedSize(true);
        sessionsrecyclerview.setLayoutManager(new LinearLayoutManager(SearchGymSessionActivity.this));
        sessionsList=new ArrayList<>();
        currentGym= FirebaseAuth.getInstance().getCurrentUser().getUid();

        sessionsRf= FirebaseDatabase.getInstance().getReference().child("Gyms").child(currentGym).child("Sessions");

        sessionsRf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        session=dataSnapshot.getValue(Session.class);
                        sessionsList.add(session);
                    }
                    adapter=new GymSessionAdapter(sessionsList,SearchGymSessionActivity.this);
                    sessionsrecyclerview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        searchView.setQueryHint("Enter Session Title");
        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Session> list = new ArrayList<>();
                for (Session sessionModel : sessionsList) {
                    String sessionTitle = sessionModel.getSessiontitle();
                    String gymName = sessionModel.getGymname();

                    if (sessionTitle.contains(newText)) {
                        list.add(sessionModel);
                        adapter.SetSearchItems(list);
                    } else
                    if (gymName.contains(newText)){
                        list.add(sessionModel);
                        adapter.SetSearchItems(list);
                    }
                }

                return false;
            }
        });

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}