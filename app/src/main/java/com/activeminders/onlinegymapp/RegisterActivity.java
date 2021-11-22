package com.activeminders.onlinegymapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class RegisterActivity extends AppCompatActivity {

    String usertype;
    EditText ed_name,ed_email,ed_password,ed_re_enter_password;
    Button register;

    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private FirebaseUser currentUser;
    String uid;

    public static final String PREFS_NAME = "LoginPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usertype=getIntent().getStringExtra("UserType");


        ed_name=findViewById(R.id.name);
        ed_email=findViewById(R.id.email);
        ed_password=findViewById(R.id.password);
        ed_password=findViewById(R.id.password);
        ed_re_enter_password=findViewById(R.id.re_password);
        register=findViewById(R.id.registerbtn);

        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_name.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                }else if (ed_email.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                }else if (ed_password.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                }else if(ed_re_enter_password.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this, "Please re_enter your password", Toast.LENGTH_SHORT).show();
                }else if (!ed_password.getText().toString().equals(ed_re_enter_password.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "Password Does't Match", Toast.LENGTH_SHORT).show();
                }else
                {
                    if (usertype.equals("User")){
                        signupUser(ed_name.getText().toString(),ed_email.getText().toString(),ed_password.getText().toString());
                    }
                }
            }
        });

    }

    private void signupUser(String name, String email, String password) {
        mRef=FirebaseDatabase.getInstance().getReference().child("Users");

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    uid=mAuth.getCurrentUser().getUid();
                    Map<String,Object> userMap=new HashMap<>();
                    userMap.put("name",name);
                    userMap.put("email",email);
                    userMap.put("uid",uid);
                    userMap.put("profileimage","default");

                    mRef.child(uid).child("UserInfo").setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "Register Successfully", Toast.LENGTH_SHORT).show();

                                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.remove("logged");
                                editor.commit();
                                finish();

                                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                finishAffinity();
                            }

                        }
                    });

                }else{

                    Toast.makeText(RegisterActivity.this, "User Not Signup successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}