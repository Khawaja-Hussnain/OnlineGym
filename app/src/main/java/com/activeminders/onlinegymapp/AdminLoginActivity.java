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
import com.activeminders.onlinegymapp.User.UserMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLoginActivity extends AppCompatActivity {


    EditText ed_email,ed_password;
    Button Login;
    private FirebaseAuth mAuth;
    public static final String PREFS_NAME = "LoginPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        ed_email=findViewById(R.id.adminemail);
        ed_password=findViewById(R.id.adminpassword);
        Login=findViewById(R.id.adminloginbtn);

        mAuth = FirebaseAuth.getInstance();

          SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
       if (settings.getString("adminlogged", "").toString().equals("adminlogged")) {
            Intent intent = new Intent(AdminLoginActivity.this, DrawerMainActivity.class);
            startActivity(intent);
            finish();
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_email.getText().toString().equals("")){
                    Toast.makeText(AdminLoginActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                }else if (ed_password.getText().toString().equals("")){
                    Toast.makeText(AdminLoginActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
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

                    Toast.makeText(AdminLoginActivity.this, "Login Sucsessfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AdminLoginActivity.this, AdminMainActivity.class));
                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("adminlogged", "adminlogged");
                    editor.commit();
                    finishAffinity();
                }

                else{
                    Toast.makeText(AdminLoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void forgotpassword(View view) {
        startActivity(new Intent(this,ResetPasswordActivity.class));
    }

    public void RegisterAdmin(View view) {
        startActivity(new Intent(AdminLoginActivity.this,RegisterActivity.class).putExtra("UserType","Admin"));
    }
}