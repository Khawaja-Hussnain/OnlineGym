package com.activeminders.onlinegymapp.Gym.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeminders.onlinegymapp.Gym.DrawerMainActivity;
import com.activeminders.onlinegymapp.Gym.GymMainActivity;
import com.activeminders.onlinegymapp.R;
import com.activeminders.onlinegymapp.ResetPasswordActivity;
import com.activeminders.onlinegymapp.UpdateProfilePhotoActivity;
import com.activeminders.onlinegymapp.User.UserMainActivity;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class GymProfileFragment extends Fragment {

    TextView tv_changepassword,tv_signout;
    CircleImageView profileimage;
    ImageView iv_editimage;

    public GymProfileFragment() {
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
        View view=inflater.inflate(R.layout.fragment_profile_gym, container, false);

        tv_changepassword=view.findViewById(R.id.chnagepassword);
        tv_signout=view.findViewById(R.id.signout);
        profileimage=view.findViewById(R.id.profileimagegym);
        iv_editimage=view.findViewById(R.id.editimg);

        iv_editimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Edit Image", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), UpdateProfilePhotoActivity.class).putExtra("profiletype","GymInfo"));
            }
        });

        tv_changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ResetPasswordActivity.class));
            }
        });



        tv_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finishAffinity();
                DrawerMainActivity activity= (DrawerMainActivity) getActivity();
                activity.Signout();
            }
        });

        getGymInfo();
        return view;
    }

    private void getGymInfo() {


            FirebaseDatabase.getInstance().getReference().child("Gyms").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("GymInfo").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.child("profileimage").getValue().equals("default")){
                        profileimage.setImageResource(R.drawable.user);
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