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
import com.activeminders.onlinegymapp.R;
import com.activeminders.onlinegymapp.User.TimingandMembershipActivity;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.activeminders.onlinegymapp.R.*;


public class GymsAdapter extends RecyclerView.Adapter<GymsAdapter.ViewHolers> {

    private List<Gyms> gymsList;
    Context context;

    public GymsAdapter(List<Gyms> gymsList, Context context) {
        this.gymsList = gymsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolers onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.gym_items,parent,false);
        return new ViewHolers(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolers holder, int position) {
        Gyms gyms=gymsList.get(position);
        holder.gymname.setText(gyms.getGymname());
        holder.gymaddress.setText(gyms.getAddress());
        if (gyms.getProfileimage().equals("default")){
            holder.logo.setImageResource(drawable.userbig);
        }else {
            Glide.with(context).load(gyms.getProfileimage()).into(holder.logo);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, TimingandMembershipActivity.class).putExtra("gymId",gyms.getUid())
                .putExtra("gymName",gyms.getGymname()).putExtra("gymLogo",gyms.getProfileimage()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return gymsList.size();
    }

    public class ViewHolers extends RecyclerView.ViewHolder {

        TextView gymname,gymaddress;
        CircleImageView logo;

        public ViewHolers(@NonNull View itemView) {
            super(itemView);

            gymname=itemView.findViewById(id.gymname);
            gymaddress=itemView.findViewById(id.gymaddress);
            logo=itemView.findViewById(id.gymlogo);
        }

    }
}
