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

import com.activeminders.onlinegymapp.Gym.DrawerMainActivity;
import com.activeminders.onlinegymapp.Gym.GymMainActivity;
import com.activeminders.onlinegymapp.User.UserMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class GymOwnerLoginActivity extends AppCompatActivity {

    EditText ed_email,ed_password;
    Button Login;

    private FirebaseAuth mAuth;

    public static final String PREFS_NAME = "LoginPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_owner_login);

        ed_email=findViewById(R.id.email);
        ed_password=findViewById(R.id.password);
        Login=findViewById(R.id.gymloginbtn);

        mAuth = FirebaseAuth.getInstance();

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        if (settings.getString("gymlogged", "").toString().equals("gymlogged")) {
            Intent intent = new Intent(GymOwnerLoginActivity.this, UserMainActivity.class);
            startActivity(intent);
            finish();
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_email.getText().toString().equals("")){
                    Toast.makeText(GymOwnerLoginActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                }else if (ed_password.getText().toString().equals("")){
                    Toast.makeText(GymOwnerLoginActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                }else
                {

                    SignIn(ed_email.getText().toString(),ed_password.getText().toString());
                }
            }
        });
    }

    private void SignIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(GymOwnerLoginActivity.this, "Login Sucsessfully", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(GymOwnerLoginActivity.this, DrawerMainActivity.class));

                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("gymlogged", "gymlogged");
                    editor.commit();
                    finishAffinity();

                }
                else{
                    Toast.makeText(GymOwnerLoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void RegisterGym(View view) {
        startActivity(new Intent(GymOwnerLoginActivity.this,GymRegisterActivity.class));
    }

    public void forgotpassword(View view) {
        startActivity(new Intent(GymOwnerLoginActivity.this,ResetPasswordActivity.class));
    }
}