package com.activeminders.onlinegymapp.Gym.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.activeminders.onlinegymapp.Adapters.GymSessionAdapter;
import com.activeminders.onlinegymapp.Gym.SearchGymSessionActivity;
import com.activeminders.onlinegymapp.Models.Session;
import com.activeminders.onlinegymapp.R;
import com.activeminders.onlinegymapp.User.Fragments.UserSessionFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class GymSessionFragment extends Fragment {

    //Show list of Session code
    private RecyclerView sessionsrecyclerview;
    private List<Session> sessionsList;
    private Button addMachines;
    private DatabaseReference sessionsRf;
    private Session session;
    private GymSessionAdapter adapter;
    String currentGym,gymname,gymlogo;

   AppCompatButton addSession;
   RelativeLayout searchlayout;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_session_gym, container, false);

        searchlayout=root.findViewById(R.id.searchlayout);
        sessionsrecyclerview=root.findViewById(R.id.sessionRV);
        sessionsrecyclerview.setHasFixedSize(true);
        sessionsrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        sessionsList=new ArrayList<>();
        currentGym=FirebaseAuth.getInstance().getCurrentUser().getUid();

        sessionsRf=FirebaseDatabase.getInstance().getReference().child("Gyms").child(currentGym).child("Sessions");

        sessionsRf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        session=dataSnapshot.getValue(Session.class);
                        sessionsList.add(session);
                    }
                    adapter=new GymSessionAdapter(sessionsList,getContext());
                    sessionsrecyclerview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addSession=root.findViewById(R.id.addsession);
        addSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddSession();
            }
        });

        searchlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SearchGymSessionActivity.class));
            }
        });

        getGymInfo();

        return root;
    }

    private void getGymInfo() {

        FirebaseDatabase.getInstance().getReference().child("Gyms").child(currentGym).child("GymInfo").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gymname=snapshot.child("gymname").getValue().toString();
                gymlogo=snapshot.child("profileimage").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void AddSession() {

        AlertDialog.Builder sessionDialog=new AlertDialog.Builder(getContext());
        LayoutInflater inflater=getLayoutInflater();
        View view=inflater.inflate(R.layout.add_session_layout,null);
        sessionDialog.setView(view);
        sessionDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog here
                dialog.dismiss();
            }
        });
        sessionDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Add ok operation here
                dialog.dismiss();
            }
        });

        sessionDialog.show();

        EditText ed_sessiontitle = (EditText) view.findViewById(R.id.sessiontitle);
        EditText ed_days = (EditText) view.findViewById(R.id.sessiondays);
        EditText ed_fee = (EditText) view.findViewById(R.id.fee);
        //machineimage = (ImageView)view.findViewById(R.id.machineimage);
        TextView tv_machineimage = (TextView) view.findViewById(R.id.imgtext);
        Button addsessionbtn=view.findViewById(R.id.addsessionbtn);


        final ProgressDialog progressDialog = new ProgressDialog(getContext());
       /* String filePathAndName = "Machine_Images/" + "" + FirebaseAuth.getInstance().getCurrentUser().getUid();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);*/

        progressDialog.setTitle("Adding Session");
        progressDialog.setMessage("Please wait, while we are adding session to your list");
        progressDialog.setCanceledOnTouchOutside(false);

        addsessionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sessiontitle=ed_sessiontitle.getText().toString();
                String sessiondays=ed_days.getText().toString();
                String sessionfee=ed_fee.getText().toString();
                if (sessiontitle.equals("")){
                    Toast.makeText(getContext(), "Please Enter Session Title", Toast.LENGTH_SHORT).show();
                }else if (sessiondays.equals("")){
                    Toast.makeText(getContext(), "Please Enter Session Number of Days", Toast.LENGTH_SHORT).show();
                }else if (sessionfee.equals("")){
                    Toast.makeText(getContext(), "Please Enter Session Fee", Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.show();

                    DatabaseReference sessionRef= FirebaseDatabase.getInstance().getReference().child("Gyms").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("Sessions");
                    HashMap sessionMap=new HashMap();
                    String key=sessionRef.push().getKey();
                    sessionMap.put("sessiontitle",sessiontitle);
                    sessionMap.put("sessiondays",sessiondays+" Days Training Session");
                    sessionMap.put("sessionfee","Rs."+sessionfee);
                    sessionMap.put("gymid",currentGym);
                    sessionMap.put("gymname",gymname);
                    sessionMap.put("gymlogo",gymlogo);
                    sessionMap.put("key",key);
                    sessionRef.child(key).setValue(sessionMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                progressDialog.dismiss();
                                ed_sessiontitle.setText("");
                                ed_days.setText("");
                                ed_fee.setText("");
                                Toast.makeText(getContext(), "Session Added Successfully", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "Error....", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

}