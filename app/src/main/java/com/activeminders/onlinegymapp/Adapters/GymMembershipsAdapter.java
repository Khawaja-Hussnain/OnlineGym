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
import androidx.recyclerview.widget.RecyclerView;

import com.activeminders.onlinegymapp.Gym.Fragments.SelectTrainerActivity;
import com.activeminders.onlinegymapp.Models.Memberships;
import com.activeminders.onlinegymapp.R;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GymMembershipsAdapter extends RecyclerView.Adapter<GymMembershipsAdapter.ViewHolder> {

    private List<Memberships> membershipsList;
    Context context;

    public GymMembershipsAdapter(List<Memberships> membershipslist, Context context) {
        this.membershipsList = membershipslist;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.membership_gym_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Memberships memberships=membershipsList.get(position);
        holder.tv_membername.setText(memberships.getMembername());
        holder.tv_membership.setText(memberships.getMembership());
        holder.tv_membershipdate.setText(memberships.getMembershipdate());
        holder.tv_trainer.setText(memberships.getTrainer());
        if (memberships.getMemberimage().equals("default")){
            holder.memberimage.setImageResource(R.drawable.user);
        }else {
            Glide.with(context).load(memberships.getMemberimage()).into(holder.memberimage);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, SelectTrainerActivity.class);
                intent.putExtra("membername",memberships.getMembername());
                intent.putExtra("memberid",memberships.getMemberid());
                intent.putExtra("membership",memberships.getMembership());
                intent.putExtra("membershipkey",memberships.getMembershipkey());
                intent.putExtra("membershipdate",memberships.getMembershipdate());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return membershipsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_membername,tv_membership,tv_membershipdate,tv_trainer;
        ImageView memberimage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_membername=itemView.findViewById(R.id.membername);
            tv_membership=itemView.findViewById(R.id.membership);
            tv_membershipdate=itemView.findViewById(R.id.membersipdate);
            tv_trainer=itemView.findViewById(R.id.memberstrainer);
            memberimage=itemView.findViewById(R.id.memberimage);
        }
    }
}
