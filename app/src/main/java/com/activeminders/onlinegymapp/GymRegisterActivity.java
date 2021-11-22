package com.activeminders.onlinegymapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class GymRegisterActivity extends AppCompatActivity {

    EditText ed_name,ed_phone,ed_address,ed_email,ed_password,ed_re_enter_password;
    AppCompatButton register;

    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private FirebaseUser currentUser;
    String uid;

    public static final String PREFS_NAME = "LoginPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_register);

        ed_name=findViewById(R.id.name);
        ed_phone=findViewById(R.id.phone);
        ed_address=findViewById(R.id.address);
        ed_email=findViewById(R.id.email);
        ed_password=findViewById(R.id.password);
        ed_re_enter_password=findViewById(R.id.re_password);
        register=findViewById(R.id.gymregisterbtn);

        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_name.getText().toString().equals("")){
                    Toast.makeText(GymRegisterActivity.this, "Please enter gym name", Toast.LENGTH_SHORT).show();
                }else if (ed_phone.getText().toString().equals("")){
                    Toast.makeText(GymRegisterActivity.this, "Please enter gym phone", Toast.LENGTH_SHORT).show();
                }else if (ed_address.getText().toString().equals("")){
                    Toast.makeText(GymRegisterActivity.this, "Please enter gym addreess", Toast.LENGTH_SHORT).show();
                }else if (ed_email.getText().toString().equals("")){
                    Toast.makeText(GymRegisterActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                }
                else if (ed_password.getText().toString().equals("")){
                    Toast.makeText(GymRegisterActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                }else if(ed_re_enter_password.getText().toString().equals("")){
                    Toast.makeText(GymRegisterActivity.this, "Please re_enter your password", Toast.LENGTH_SHORT).show();
                }else if (!ed_password.getText().toString().equals(ed_re_enter_password.getText().toString())){
                    Toast.makeText(GymRegisterActivity.this, "Password Does't Match", Toast.LENGTH_SHORT).show();
                }else
                {

                        GymRegister(ed_name.getText().toString(),ed_phone.getText().toString(),ed_address.getText().toString(),
                                ed_email.getText().toString(),ed_password.getText().toString());

                }
            }
        });

    }

    private void GymRegister(String name, String phone, String address, String email, String password) {

        mRef= FirebaseDatabase.getInstance().getReference().child("Gyms");

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    uid=mAuth.getCurrentUser().getUid();
                    Map<String,Object> userMap=new HashMap<>();
                    userMap.put("gymname",name);
                    userMap.put("phone",phone);
                    userMap.put("address",address);
                    userMap.put("email",email);
                    userMap.put("uid",uid);
                    userMap.put("profileimage","default");
                    userMap.put("status","unapproved");

                    mRef.child(uid).child("GymInfo").setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(GymRegisterActivity.this, "Register Successfully", Toast.LENGTH_SHORT).show();

                                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.remove("logged");
                                editor.commit();
                                finish();

                                startActivity(new Intent(GymRegisterActivity.this,GymOwnerLoginActivity.class));
                                finishAffinity();
                            }

                        }
                    });

                }else{

                    Toast.makeText(GymRegisterActivity.this, "Gym Not Register successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}