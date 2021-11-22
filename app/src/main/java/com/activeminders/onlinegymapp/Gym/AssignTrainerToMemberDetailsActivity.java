package com.activeminders.onlinegymapp.Gym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.activeminders.onlinegymapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class AssignTrainerToMemberDetailsActivity extends AppCompatActivity {

    TextView tv_membername,tv_trainername;
    Button addtrainer;

    //This data is coming from AssignTrainerAdapter
    String membername,memberid,membership,membershipkey,membershipdate,trainername,trainerid,trainercontact,gymid,gymname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_trainer_to_member_details);

        membername=getIntent().getStringExtra("membername");
        memberid=getIntent().getStringExtra("memberid");
        membership=getIntent().getStringExtra("membership");
        membershipkey=getIntent().getStringExtra("membershipkey");
        membershipdate=getIntent().getStringExtra("membershipdate");
        trainername=getIntent().getStringExtra("trainername");
        trainerid=getIntent().getStringExtra("trainerid");
        trainercontact=getIntent().getStringExtra("trainercontact");
        gymid= FirebaseAuth.getInstance().getCurrentUser().getUid();

        //collecting current gym name etc
        FirebaseDatabase.getInstance().getReference().child("Gyms").child(gymid).child("GymInfo").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                gymname=snapshot.child("gymname").getValue().toString();

                //assigning trainer
                addtrainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference trainerRef=FirebaseDatabase.getInstance().getReference().child("Gyms").child(gymid).child("Trainers")
                                .child(trainerid).child("Memberships");
                        Map<String,Object> trainerMap=new HashMap<>();
                        trainerMap.put("membername",membername);
                        trainerMap.put("memberid",memberid);
                        trainerMap.put("membership",membership);
                        trainerMap.put("membershipkey",membershipkey);
                        trainerMap.put("membershipdate",membershipdate);
                        trainerMap.put("gymid",gymid);
                        trainerMap.put("gymname",gymname);
                        trainerRef.child(membershipkey).updateChildren(trainerMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if (task.isSuccessful()){

                                    DatabaseReference mRef=FirebaseDatabase.getInstance().getReference().child("Gyms").child(gymid).child("Memberships")
                                            .child("GymMemberships").child(membershipkey);

                                    Map<String,Object> map=new HashMap<>();
                                    map.put("trainer",trainername);
                                    mRef.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                DatabaseReference userRef=FirebaseDatabase.getInstance().getReference().child("Users").child(memberid).child("Memberships")
                                                        .child("GymMemberships").child(membershipkey);
                                                Map<String,Object> userMap=new HashMap<>();
                                                userMap.put("trainer",trainername);
                                                userRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                        if (task.isSuccessful()){
                                                            Toast.makeText(AssignTrainerToMemberDetailsActivity.this, ""+trainername+" is assigned to "+membername, Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    });

                                }
                            }
                        });
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

       /* Toast.makeText(this, ""+membername, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, ""+memberid, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, ""+membership, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, ""+membershipkey, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, ""+membershipdate, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, ""+trainername, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, ""+trainerid, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, ""+trainercontact, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, ""+gymid, Toast.LENGTH_SHORT).show();*/

        tv_membername=findViewById(R.id.addmembername);
        tv_trainername=findViewById(R.id.addtrainername);
        addtrainer=findViewById(R.id.traineradd);
        tv_membername.setText(membername);
        tv_trainername.setText(trainername);


    }

    public void back(View view) {
        finish();
    }
}