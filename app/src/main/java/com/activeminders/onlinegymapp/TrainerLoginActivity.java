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
import com.activeminders.onlinegymapp.Trainer.TrainerMainActivity;
import com.activeminders.onlinegymapp.User.UserMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class TrainerLoginActivity extends AppCompatActivity {

    EditText ed_email,ed_password;
    Button Login;
    private FirebaseAuth mAuth;
    public static final String PREFS_NAME = "LoginPrefs";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_login);

        ed_email=findViewById(R.id.traineremail);
        ed_password=findViewById(R.id.trainerpassword);
        Login=findViewById(R.id.trainerloginbtn);

        mAuth = FirebaseAuth.getInstance();

       /* SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        if (settings.getString("trainerlogged", "").toString().equals("trainerlogged")) {
            Intent intent = new Intent(TrainerLoginActivity.this, TrainerMainActivity.class);
            startActivity(intent);
            finish();
        }
*/
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
                    Toast.makeText(TrainerLoginActivity.this, "Login Sucsessfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(TrainerLoginActivity.this, TrainerMainActivity.class));

                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("trainerlogged", "trainerlogged");
                    editor.commit();
                    finish();

                }
                else{
                    Toast.makeText(TrainerLoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void forgotpassword(View view) {
        startActivity(new Intent(this,ResetPasswordActivity.class));
    }
}