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

import com.activeminders.onlinegymapp.Adapters.TrainersAdapter;
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

public class SearchGymTrainersActivity extends AppCompatActivity {

    private RecyclerView SerachTrainerRV;
    private List<Trainers> trainersList;
    private Button addMachines;
    private DatabaseReference trainersRef;
    private Trainers trainers;
    private TrainersAdapter adapter;
    String currentGym;
    SearchView searchView;
    ImageView backarrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_gym_trainers);

        searchView=findViewById(R.id.searchview);
        backarrow=findViewById(R.id.backarr);
        SerachTrainerRV=findViewById(R.id.searchtrainerRV);
        SerachTrainerRV.setHasFixedSize(true);
        SerachTrainerRV.setLayoutManager(new LinearLayoutManager(SearchGymTrainersActivity.this));
        trainersList=new ArrayList<>();
        currentGym= FirebaseAuth.getInstance().getCurrentUser().getUid();

        trainersRef= FirebaseDatabase.getInstance().getReference().child("Gyms").child(currentGym).child("Trainers");
        trainersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        trainers=dataSnapshot.getValue(Trainers.class);
                        trainersList.add(trainers);
                    }
                    adapter=new TrainersAdapter(trainersList,SearchGymTrainersActivity.this);
                    SerachTrainerRV.setAdapter(adapter);
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
                List<Trainers> list = new ArrayList<>();
                for (Trainers trainersModel : trainersList) {
                    String trainerName = trainersModel.getName();

                    if (trainerName.contains(newText)) {
                        list.add(trainersModel);
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