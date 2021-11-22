package com.activeminders.onlinegymapp.Gym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.activeminders.onlinegymapp.LoginActivity;
import com.activeminders.onlinegymapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class AddTrainerActivity extends AppCompatActivity {


    EditText ed_name,ed_contact,ed_email,ed_password,ed_experience,ed_age,ed_description;
    AppCompatButton addtrainer;
    ImageView backpress;
    DatabaseReference trainerRef;
    FirebaseAuth mAuth;
    String gymid;
    public static final String PREFS_NAME = "LoginPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trainer);

        ed_name=findViewById(R.id.trainername);
        ed_contact=findViewById(R.id.trainercontact);
        ed_email=findViewById(R.id.traineremail);
        ed_password=findViewById(R.id.trainerpassword);
        ed_experience=findViewById(R.id.trainerexperience);
        ed_age=findViewById(R.id.trainerage);
        ed_description=findViewById(R.id.trainerdescription);
        addtrainer=findViewById(R.id.addtrainerbtn);;

        backpress=findViewById(R.id.backarrow);
        backpress.setOnClickListener(v -> finish());

        addtrainer.setOnClickListener(v -> {
            String name=ed_name.getText().toString();
            String contact=ed_contact.getText().toString();
            String email=ed_email.getText().toString();
            String password=ed_password.getText().toString();
            String experience=ed_experience.getText().toString();
            String age=ed_age.getText().toString();
            String description=ed_description.getText().toString();

            mAuth=FirebaseAuth.getInstance();
            gymid=mAuth.getCurrentUser().getUid();
            trainerRef= FirebaseDatabase.getInstance().getReference().child("Gyms")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

            if (name.equals("")){
                Toast.makeText(AddTrainerActivity.this, "Please Enter Trainer Name", Toast.LENGTH_SHORT).show();
            }else if (contact.equals("")){
                Toast.makeText(AddTrainerActivity.this, "Please Enter Trainer Contact", Toast.LENGTH_SHORT).show();
            }else if (email.equals("")){
                Toast.makeText(AddTrainerActivity.this, "Please Enter Trainer Email", Toast.LENGTH_SHORT).show();
            }else if (password.equals("")){
                Toast.makeText(AddTrainerActivity.this, "Please Enter Trainer Password", Toast.LENGTH_SHORT).show();
            } else if (experience.equals("")) {
                Toast.makeText(AddTrainerActivity.this, "Please Enter Trainer Experience", Toast.LENGTH_SHORT).show();
            }else if (age.equals("")){
                Toast.makeText(AddTrainerActivity.this, "Please Enter Trainer Age", Toast.LENGTH_SHORT).show();
            }else if (description.equals("")){
                Toast.makeText(AddTrainerActivity.this, "Please Enter Description About Trainer", Toast.LENGTH_SHORT).show();
            }else {
                int exp=Integer.parseInt(experience);
                int agee=Integer.parseInt(age);
                if (exp > 50) {
                    Toast.makeText(this, "Experience Not Valid", Toast.LENGTH_SHORT).show();
                } else if (agee > 80) {
                    Toast.makeText(this, "Above 80 Not Allowed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Check", Toast.LENGTH_SHORT).show();
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                String trainerid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                Map<String, Object> trainerMap = new HashMap<>();
                                trainerMap.put("name", name);
                                trainerMap.put("contact", contact);
                                trainerMap.put("email", email);
                                trainerMap.put("experience", experience);
                                trainerMap.put("age", age);
                                trainerMap.put("description", description);
                                trainerMap.put("tid", trainerid);
                                trainerMap.put("gid", gymid);
                                trainerRef.child("Trainers").child(trainerid).child("Info").setValue(trainerMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            startActivity(new Intent(AddTrainerActivity.this, LoginActivity.class));
                                            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                                            SharedPreferences.Editor editor = settings.edit();
                                            editor.remove("gymlogged");
                                            editor.commit();
                                            Toast.makeText(AddTrainerActivity.this, "Trainer Added", Toast.LENGTH_SHORT).show();

                                            finishAffinity();
                                        } else {
                                            Toast.makeText(AddTrainerActivity.this, "Error....", Toast.LENGTH_SHORT).show();
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
}