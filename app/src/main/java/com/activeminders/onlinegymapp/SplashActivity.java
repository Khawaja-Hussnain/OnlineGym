package com.activeminders.onlinegymapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        },2000);


    }
}