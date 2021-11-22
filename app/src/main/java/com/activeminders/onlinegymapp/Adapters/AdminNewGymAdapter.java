package com.activeminders.onlinegymapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.activeminders.onlinegymapp.Admin.AdminMainActivity;
import com.activeminders.onlinegymapp.Models.Gyms;
import com.activeminders.onlinegymapp.R;
import com.activeminders.onlinegymapp.User.TimingandMembershipActivity;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminNewGymAdapter extends RecyclerView.Adapter<AdminNewGymAdapter.ViewHolder> {

    private List<Gyms> gymsList;
    Context context;

    public AdminNewGymAdapter(List<Gyms> gymsList, Context context) {
        this.gymsList = gymsList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.new_gym_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Gyms gyms=gymsList.get(position);
        holder.gymname.setText(gyms.getGymname());
        holder.gymcontact.setText(gyms.getPhone());
        holder.gymaddress.setText(gyms.getAddress());
        if (!gyms.getProfileimage().equals("default")){
            Glide.with(context).load(gyms.getProfileimage()).into(holder.profileimage);
        }else {
            holder.profileimage.setImageResource(R.drawable.userbig);
        }
        holder.approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Gyms").child(gyms.getUid()).child("GymInfo")
                        .child("status").setValue("approved").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(context, "Gym Approved", Toast.LENGTH_SHORT).show();
                            /*context.startActivity(new Intent(context, AdminMainActivity.class));
                            ((AdminMainActivity)context).finish();*/
                        }
                    }
                });
            }
        });
       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, TimingandMembershipActivity.class).putExtra("gymId",gyms.getUid())
                        .putExtra("gymName",gyms.getGymname()).putExtra("gymLogo",gyms.getProfileimage()));
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return gymsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView gymname,gymcontact,gymaddress;
        ImageView profileimage;
        AppCompatButton approved;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            gymname=itemView.findViewById(R.id.nametext);
            gymcontact=itemView.findViewById(R.id.contacttext);
            gymaddress=itemView.findViewById(R.id.addresstext);
            profileimage=itemView.findViewById(R.id.gymprofileimage);
            approved=itemView.findViewById(R.id.approved);
        }
    }
}
