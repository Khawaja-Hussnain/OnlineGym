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

import com.activeminders.onlinegymapp.Admin.AdminMainActivity;
import com.activeminders.onlinegymapp.Gym.DrawerMainActivity;
import com.activeminders.onlinegymapp.Gym.GymMainActivity;
import com.activeminders.onlinegymapp.Trainer.TrainerMainActivity;
import com.activeminders.onlinegymapp.User.UserMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText ed_email,ed_password;
    Button Login;

    private FirebaseAuth mAuth;

    public static final String PREFS_NAME = "LoginPrefs";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ed_email=findViewById(R.id.email);
        ed_password=findViewById(R.id.password);
        Login=findViewById(R.id.loginbtn);

        mAuth = FirebaseAuth.getInstance();

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        if (settings.getString("logged", "").toString().equals("logged")) {
            Intent intent = new Intent(LoginActivity.this, UserMainActivity.class);
            startActivity(intent);
            finish();
        }
        else if (settings.getString("gymlogged", "").toString().equals("gymlogged")) {
            Intent intent = new Intent(LoginActivity.this, DrawerMainActivity.class);
            startActivity(intent);
            finish();
        } else if (settings.getString("adminlogged", "").toString().equals("adminlogged")) {
            Intent intent = new Intent(LoginActivity.this, AdminMainActivity.class);
            startActivity(intent);
            finish();
        }else if (settings.getString("trainerlogged", "").toString().equals("trainerlogged")) {
            Intent intent = new Intent(LoginActivity.this, TrainerMainActivity.class);
            startActivity(intent);
            finish();
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_email.getText().toString().isEmpty()){
                    ed_email.setError("Please Enter Email Address");
                    ed_email.requestFocus();
                    //Toast.makeText(LoginActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                }else if (!ed_email.getText().toString().matches(emailPattern)){
                    ed_email.setError("Please Enter Valid Email Address");
                    ed_email.requestFocus();
                    //Toast.makeText(LoginActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                }else if (ed_password.getText().toString().isEmpty()){
                    ed_password.setError("Please Enter Password");
                    ed_password.requestFocus();
                    //Toast.makeText(LoginActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(LoginActivity.this, "Login Sucsessfully", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(LoginActivity.this, UserMainActivity.class));

                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("logged", "logged");
                    editor.commit();
                    finish();

                }
                else{
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void gymtypelogin(View view) {
        startActivity(new Intent(LoginActivity.this,GymOwnerLoginActivity.class));
    }

    public void admintypelogin(View view) {
        startActivity(new Intent(LoginActivity.this,AdminLoginActivity.class));
    }

    public void forgotpassword(View view) {
        startActivity(new Intent(LoginActivity.this,ResetPasswordActivity.class));
    }

    public void register(View view) {
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class).putExtra("UserType","User"));
    }

    public void trainertypelogin(View view) {
        startActivity(new Intent(LoginActivity.this,TrainerLoginActivity.class));
    }
}