package com.activeminders.onlinegymapp.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import com.activeminders.onlinegymapp.Adapters.UserSessionAdapater;
import com.activeminders.onlinegymapp.Models.Session;
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

public class SearchSessionActivity extends AppCompatActivity {

    //Show list of Session code
    private RecyclerView sessionsrecyclerview;
    private java.util.List<Session> sessionsList;
    private List<String> List;
    private DatabaseReference sessionsRf;
    private Session session;
    private UserSessionAdapater adapter;
    String currentUser;
    SearchView searchView;
    ImageView backarrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_session);

        sessionsrecyclerview=findViewById(R.id.searchusersessionrRV);
        searchView=findViewById(R.id.searchview);
        backarrow=findViewById(R.id.backarr);
        sessionsrecyclerview.setHasFixedSize(true);
        sessionsrecyclerview.setLayoutManager(new LinearLayoutManager(SearchSessionActivity.this));
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
                    adapter=new UserSessionAdapater(sessionsList,SearchSessionActivity.this);
                    sessionsrecyclerview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        searchView.setQueryHint("Enter Trainer Name");
        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Session> list = new ArrayList<>();
                for (Session sessionModel : sessionsList) {
                    String SessionTitle = sessionModel.getSessiontitle();

                    if (SessionTitle.contains(newText)) {
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