package com.activeminders.onlinegymapp.User.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ActivityFragment extends Fragment {

    LinearLayout fatrate,weight,bodysize,chest,height,biseps;
    TextView tv_fat,tv_fatrate,tv_weight,tv_weighttext,tv_body,tv_bodysize,
            tv_chest,tv_chestsize,tv_height,tv_heightsize,tv_bispes,tv_bisepsize;
    String clicktype,hint;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_activity, container, false);

        fatrate=view.findViewById(R.id.fatratelayout);
        weight=view.findViewById(R.id.weightlayout);
        bodysize=view.findViewById(R.id.bodylayout);
        chest=view.findViewById(R.id.chestlayout);
        height=view.findViewById(R.id.heightlayout);
        biseps=view.findViewById(R.id.bisepslayout);

        fatrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicktype="FatRate";
                hint="Enter Fate";
                UpdateActivity();
            }
        });

        weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicktype="Weight";
                hint="Enter Weight";
                UpdateActivity();
            }
        });
        bodysize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicktype="BodySize";
                hint="Enter Waste";
                UpdateActivity();
            }
        });
        chest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicktype="Chest";
                hint="Enter Chest Size";
                UpdateActivity();
            }
        });
        height.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicktype="Height";
                hint="Enter Your Height";
                UpdateActivity();
            }
        });
        biseps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicktype="Biseps";
                hint="Enter Biseps";
                UpdateActivity();
            }
        });


        tv_fat=view.findViewById(R.id.fat);
        tv_fatrate=view.findViewById(R.id.fatrate);

        tv_weight=view.findViewById(R.id.weight);
        tv_weighttext=view.findViewById(R.id.weighttext);

        tv_body=view.findViewById(R.id.body);
        tv_bodysize=view.findViewById(R.id.bodysize);

        tv_chest=view.findViewById(R.id.chest);
        tv_chestsize=view.findViewById(R.id.chestsize);

        tv_height=view.findViewById(R.id.height);
        tv_heightsize=view.findViewById(R.id.heighttext);

        tv_bispes=view.findViewById(R.id.biseps);
        tv_bisepsize=view.findViewById(R.id.bispesize);

        return view;
    }

    private void UpdateActivity() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_diolog_layout,null);
        EditText ed_activityname = (EditText) view.findViewById(R.id.name);
        ed_activityname.setHint(hint);
        Button update=view.findViewById(R.id.updatebtn);

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        String filePathAndName = "Machine_Images/" + "" + FirebaseAuth.getInstance().getCurrentUser().getUid();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);

        progressDialog.setTitle("Updating Activity");
        progressDialog.setMessage("Please wait, while we are updating "+clicktype);
        progressDialog.setCanceledOnTouchOutside(false);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_activityname.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Please Enter "+clicktype, Toast.LENGTH_SHORT).show();
                } else{
                    progressDialog.show();
                    if (clicktype.equals("FatRate")){
                        DatabaseReference fatRef= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("Activity");
                        Map<String,Object> fatMap=new HashMap<>();
                        fatMap.put("fatrate",ed_activityname.getText().toString());
                        fatRef.updateChildren(fatMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(), "Fatrate Updated", Toast.LENGTH_SHORT).show();
                                }else
                                {
                                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }
                        });
                    }else  if (clicktype.equals("Weight")){
                        DatabaseReference fatRef= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("Activity");
                        Map<String,Object> fatMap=new HashMap<>();
                        fatMap.put("weight",ed_activityname.getText().toString());
                        fatRef.updateChildren(fatMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(), "Weight Updated", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else if (clicktype.equals("BodySize")){
                        DatabaseReference fatRef= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("Activity");
                        Map<String,Object> fatMap=new HashMap<>();
                        fatMap.put("bodysize",ed_activityname.getText().toString());
                        fatRef.updateChildren(fatMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(), "Body Size Updated", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else  if (clicktype.equals("Chest")){
                        DatabaseReference fatRef= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("Activity");
                        Map<String,Object> fatMap=new HashMap<>();
                        fatMap.put("chest",ed_activityname.getText().toString());
                        fatRef.updateChildren(fatMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(), "Chest Size Updated", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else if (clicktype.equals("Height")){
                        DatabaseReference fatRef= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("Activity");
                        Map<String,Object> fatMap=new HashMap<>();
                        fatMap.put("height",ed_activityname.getText().toString());
                        fatRef.updateChildren(fatMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(), "Height Updated", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else  if (clicktype.equals("Biseps")){
                        DatabaseReference fatRef= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("Activity");
                        Map<String,Object> fatMap=new HashMap<>();
                        fatMap.put("biseps",ed_activityname.getText().toString());
                        fatRef.updateChildren(fatMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(), "Biseps Updated", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                }
            }
        });

        builder.setView(view);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog here
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Add ok operation here
                dialog.dismiss();
            }
        });

        builder.show();

    }

    @Override
    public void onStart() {
        super.onStart();

        DatabaseReference fatRef= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Activity");
        fatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){





                    if (snapshot.child("fatrate").exists()){
                        String faterate=snapshot.child("fatrate").getValue().toString();
                        tv_fatrate.setText(faterate);
                    }
                    if (snapshot.child("weight").exists()){
                        String weight=snapshot.child("weight").getValue().toString();
                        tv_weighttext.setText(weight+" kg");
                    }
                    if (snapshot.child("bodysize").exists()){
                        String bodysize=snapshot.child("bodysize").getValue().toString();
                        tv_bodysize.setText(bodysize+" in");
                    }
                    if (snapshot.child("chest").exists()){
                        String chest=snapshot.child("chest").getValue().toString();
                        tv_chestsize.setText(chest+" in");
                    }
                    if (snapshot.child("height").exists()){
                        String height=snapshot.child("height").getValue().toString();
                        tv_heightsize.setText(height+" feet");
                    }
                    if (snapshot.child("biseps").exists()){
                        String biseps=snapshot.child("biseps").getValue().toString();
                        tv_bisepsize.setText(biseps+" in");
                    }






                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
}