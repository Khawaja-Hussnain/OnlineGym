package com.activeminders.onlinegymapp.User.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeminders.onlinegymapp.R;
import com.activeminders.onlinegymapp.ResetPasswordActivity;
import com.activeminders.onlinegymapp.UpdateProfilePhotoActivity;
import com.activeminders.onlinegymapp.User.MyMembershipsActivity;
import com.activeminders.onlinegymapp.User.UserMainActivity;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageTask;

import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {

    TextView tv_changepassword,tv_mymemberships,tv_signout;
    ImageView iv_editimage;
    CircleImageView profileimage;

    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view=inflater.inflate(R.layout.fragment_profile, container, false);
       tv_changepassword=view.findViewById(R.id.chnagepassword);
       tv_mymemberships=view.findViewById(R.id.mymemberships);
       tv_signout=view.findViewById(R.id.signout);
       profileimage=view.findViewById(R.id.profileimageview);
       iv_editimage=view.findViewById(R.id.editimg);

        iv_editimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Edit Image", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getContext(), UpdateProfilePhotoActivity.class).putExtra("profiletype","UserInfo"));
                /*Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 1000);*/
            }
        });

       tv_changepassword.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getContext(), ResetPasswordActivity.class));
           }
       });

       tv_mymemberships.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getContext(), MyMembershipsActivity.class));
           }
       });

        tv_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                UserMainActivity activity= (UserMainActivity) getActivity();
                activity.Signout();
            }
        });
        getUserInfo();
       return view;
    }

    private void getUserInfo() {

        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("UserInfo").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("profileimage").getValue().equals("default")){
                    profileimage.setImageResource(R.drawable.userbig);
                }else {
                    Glide.with(getContext()).load(snapshot.child("profileimage").getValue().toString()).into(profileimage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}