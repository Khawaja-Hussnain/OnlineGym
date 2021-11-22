package com.activeminders.onlinegymapp.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.activeminders.onlinegymapp.R;

public class TimingandMembershipActivity extends AppCompatActivity {

    LinearLayout membership30days,membership60days,membership90days;
    String days="",payment="";
    String gymId,gymName,gymLogo;//These values are coming from GymsAdapter
    AppCompatButton pay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timingand_membership);

        gymId=getIntent().getStringExtra("gymId");
        gymName=getIntent().getStringExtra("gymName");
        gymLogo=getIntent().getStringExtra("gymLogo");

        membership30days=findViewById(R.id.membershiplayout1);
        membership60days=findViewById(R.id.membershiplayout2);
        membership90days=findViewById(R.id.membershiplayout3);
        pay=findViewById(R.id.pay);

        membership30days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days="30";
                payment="2000";
                membership30days.setBackground(getResources().getDrawable(R.drawable.package_background_layout));
                membership60days.setBackground(getResources().getDrawable(R.drawable.layout_views_background));
                membership90days.setBackground(getResources().getDrawable(R.drawable.layout_views_background));
            }
        });
        membership60days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days="60";
                payment="5000";
                membership30days.setBackground(getResources().getDrawable(R.drawable.layout_views_background));
                membership60days.setBackground(getResources().getDrawable(R.drawable.package_background_layout));
                membership90days.setBackground(getResources().getDrawable(R.drawable.layout_views_background));
            }
        });
        membership90days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days="90";
                payment="10000";
                membership30days.setBackground(getResources().getDrawable(R.drawable.layout_views_background));
                membership60days.setBackground(getResources().getDrawable(R.drawable.layout_views_background));
                membership90days.setBackground(getResources().getDrawable(R.drawable.package_background_layout  ));
            }
        });
    pay.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!days.equals("") && !payment.equals("")){
                startActivity(new Intent(TimingandMembershipActivity.this,PaymentActivity.class).putExtra("days",days)
                        .putExtra("payment",payment).putExtra("gymId",gymId).putExtra("gymName",gymName).putExtra("gymLogo",gymLogo));
                days="";
                payment="";
                membership30days.setBackground(getResources().getDrawable(R.drawable.layout_views_background));
                membership60days.setBackground(getResources().getDrawable(R.drawable.layout_views_background));
                membership90days.setBackground(getResources().getDrawable(R.drawable.layout_views_background  ));
            }else{
                Toast.makeText(TimingandMembershipActivity.this, "Please A Membership Package", Toast.LENGTH_SHORT).show();
            }
        }
    });
    }
}