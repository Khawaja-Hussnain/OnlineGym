package com.activeminders.onlinegymapp.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.activeminders.onlinegymapp.Models.Session;
import com.activeminders.onlinegymapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class SessionPaymentActivity extends AppCompatActivity {

    EditText ed_accountname,ed_cardnumber,ed_cvv,ed_dateofbirth;
    AppCompatButton paynow;
    ImageView backarrow;
    DatabaseReference MembersRef;

    String username,useremail,userid,userimage;

    String gymid,gymname,gymlogo,sessiondays,sessiontitle,sessionfee,sessionid;//this is for session membership

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_payment);


        //Get data thorugh intent for session memberships and this data is coming from UserSessionAdapter
        gymid=getIntent().getStringExtra("gymid");
        gymname=getIntent().getStringExtra("gymname");
        gymlogo=getIntent().getStringExtra("gymlogo");
        sessiondays=getIntent().getStringExtra("sessionDays");
        sessiontitle=getIntent().getStringExtra("sessionTitle");
        sessionid=getIntent().getStringExtra("sessionId");
        sessionfee=getIntent().getStringExtra("sessionFee");


        Date date = Calendar.getInstance().getTime();
        System.out.println("Current time => " + date);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(date);

        ed_accountname=findViewById(R.id.accountname);
        ed_cardnumber=findViewById(R.id.cardnum);
        ed_cvv=findViewById(R.id.cvv);
        ed_dateofbirth=findViewById(R.id.dateofbirth);
        paynow=findViewById(R.id.paybtn);
        backarrow=findViewById(R.id.backbtn);

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String accountName = ed_accountname.getText().toString();
                String cardNo = ed_cardnumber.getText().toString();
                String cvv = ed_cvv.getText().toString();
                String dateofbirth = ed_dateofbirth.getText().toString();
                String membershipKey = "",sessionKey = "";

                MembersRef=FirebaseDatabase.getInstance().getReference().child("Gyms").child(gymid).child("Memberships").child("SessionMemberships");

                if (accountName.equals("")) {
                    Toast.makeText(SessionPaymentActivity.this, "Please Enter Account Name", Toast.LENGTH_SHORT).show();
                } else if (cardNo.equals("")) {
                    Toast.makeText(SessionPaymentActivity.this, "Please Enter Card No", Toast.LENGTH_SHORT).show();
                } else if (cvv.equals("")) {
                    Toast.makeText(SessionPaymentActivity.this, "Please Enter CVV No", Toast.LENGTH_SHORT).show();
                } else if (dateofbirth.equals("")) {
                    Toast.makeText(SessionPaymentActivity.this, "Please Enter Your Date of Birth", Toast.LENGTH_SHORT).show();
                } else {

                    sessionKey = MembersRef.push().getKey();
                    HashMap<String, Object> gymMembershipMap = new HashMap();
                    gymMembershipMap.put("accountname", accountName);
                    gymMembershipMap.put("cardno", cardNo);
                    gymMembershipMap.put("cvv", cvv);
                    gymMembershipMap.put("dateofbirth", dateofbirth);
                    gymMembershipMap.put("membername", username);
                    gymMembershipMap.put("memberemail", useremail);
                    gymMembershipMap.put("memberimage", userimage);
                    gymMembershipMap.put("memberid", userid);
                    gymMembershipMap.put("session", sessiondays);
                    gymMembershipMap.put("sessionid", sessionid);
                    gymMembershipMap.put("sessionfee", sessionfee);
                    gymMembershipMap.put("sessiontitle", sessiontitle);
                    gymMembershipMap.put("sessionkey", sessionKey);
                    String finalsessionKey = sessionKey;
                    MembersRef.child(sessionKey).setValue(gymMembershipMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                DatabaseReference MyGymRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userid).child("Memberships").child("SessionMemberships");
                                HashMap<String, Object> MyMembershipMap = new HashMap();
                                MyMembershipMap.put("accountname", accountName);
                                MyMembershipMap.put("cardno", cardNo);
                                MyMembershipMap.put("cvv", cvv);
                                MyMembershipMap.put("dateofbirth", dateofbirth);
                                MyMembershipMap.put("gymname", gymname);
                                MyMembershipMap.put("gymid", gymid);
                                MyMembershipMap.put("gymlogo", gymlogo);
                                MyMembershipMap.put("session", sessiondays);
                                MyMembershipMap.put("sessionid", sessionid);
                                MyMembershipMap.put("sessionfee", sessionfee);
                                MyMembershipMap.put("sessiontitle", sessiontitle);
                                MyMembershipMap.put("sessionkey", finalsessionKey);
                                MyGymRef.child(finalsessionKey).setValue(MyMembershipMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(SessionPaymentActivity.this, "Membership added successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(SessionPaymentActivity.this, UserMainActivity.class));
                                            finish();
                                        }
                                    }
                                });
                            }
                        }
                    });
                    }
                }
        });
        getUserInfo();

    }

    private void getUserInfo() {

        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("UserInfo")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        username=snapshot.child("name").getValue().toString();
                        useremail=snapshot.child("email").getValue().toString();
                        userid=snapshot.child("uid").getValue().toString();
                        userimage=snapshot.child("profileimage").getValue().toString();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}