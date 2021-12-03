package com.activeminders.onlinegymapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.activeminders.onlinegymapp.Models.Machines;
import com.activeminders.onlinegymapp.Models.Memberships;
import com.activeminders.onlinegymapp.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class UserMembershipsAdapter extends RecyclerView.Adapter<UserMembershipsAdapter.ViewHolder> {

    private List<Memberships> membershipsList;
    Context context;

    public UserMembershipsAdapter(List<Memberships> membershipsList, Context context) {
        this.membershipsList = membershipsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_membership_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Memberships memberships=membershipsList.get(position);
        holder.tv_gymname.setText(memberships.getGymname());
        holder.tv_membership.setText(memberships.getMembership());
        holder.tv_trainername.setText(memberships.getTrainer());
        holder.tv_membershipdate.setText(memberships.getMembershipdate());
        if (!memberships.getGymlogo().equals("default")) {
            Glide.with(context).load(memberships.getGymlogo()).into(holder.gymimage);
        }
    }

    @Override
    public int getItemCount() {
        return membershipsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_gymname,tv_membership,tv_trainername,tv_membershipdate;
        ImageView gymimage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_gymname=itemView.findViewById(R.id.memgymname);
            tv_membership=itemView.findViewById(R.id.memship);
            tv_trainername=itemView.findViewById(R.id.memtrainer);
            tv_membershipdate=itemView.findViewById(R.id.memsipdate);
            gymimage=itemView.findViewById(R.id.machine_image);
        }
    }
}
