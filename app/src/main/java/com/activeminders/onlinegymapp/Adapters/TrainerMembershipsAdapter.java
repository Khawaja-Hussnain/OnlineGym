package com.activeminders.onlinegymapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.activeminders.onlinegymapp.Models.Gyms;
import com.activeminders.onlinegymapp.Models.TrainerMemberships;
import com.activeminders.onlinegymapp.R;
import com.activeminders.onlinegymapp.User.TimingandMembershipActivity;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TrainerMembershipsAdapter extends RecyclerView.Adapter<TrainerMembershipsAdapter.ViewHolers> {

    private List<TrainerMemberships> trainerMembershipsList;
    Context context;

    public TrainerMembershipsAdapter(List<TrainerMemberships> trainerMembershipsList, Context context) {
        this.trainerMembershipsList = trainerMembershipsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolers onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.trainer_memberships_layout,parent,false);
        return new ViewHolers(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolers holder, int position) {

        TrainerMemberships trainerMemberships=trainerMembershipsList.get(position);
        holder.tv_gymname.setText(trainerMemberships.getGymname());
        holder.tv_membername.setText(trainerMemberships.getMembername());
        holder.tv_membership.setText(trainerMemberships.getMembership());
        holder.tv_membershipdate.setText(trainerMemberships.getMembershipdate());


    }

    @Override
    public int getItemCount() {
        return trainerMembershipsList.size();
    }

    public class ViewHolers extends RecyclerView.ViewHolder {

        TextView tv_gymname,tv_membername,tv_membership,tv_membershipdate;

        public ViewHolers(@NonNull View itemView) {
            super(itemView);

            tv_gymname=itemView.findViewById(R.id.trainergymname);
            tv_membername=itemView.findViewById(R.id.trainermembername);
            tv_membership=itemView.findViewById(R.id.trainermembership);
            tv_membershipdate=itemView.findViewById(R.id.trainermembershipdate);
        }

    }
}
