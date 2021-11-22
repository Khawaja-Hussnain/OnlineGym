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

import com.activeminders.onlinegymapp.Gym.AssignTrainerToMemberDetailsActivity;
import com.activeminders.onlinegymapp.Gym.Fragments.SelectTrainerActivity;
import com.activeminders.onlinegymapp.Models.Trainers;
import com.activeminders.onlinegymapp.R;

import java.util.List;

public class AssignTrainerAdapter extends RecyclerView.Adapter<AssignTrainerAdapter.ViewHolder> {

    private List<Trainers> trainersList;
    Context context;
    public String membername,memberid,membership,membershipkey,membershipdate;

    public AssignTrainerAdapter(List<Trainers> trainersList, Context context, String membername, String memberid,
                                String membership, String membershipkey, String membershipdate) {
        this.trainersList = trainersList;
        this.context = context;
        this.membername = membername;
        this.memberid = memberid;
        this.membership = membership;
        this.membershipkey = membershipkey;
        this.membershipdate = membershipdate;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.assigntrainer_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Trainers trainers=trainersList.get(position);
        holder.tv_trainernam.setText(trainers.getName());
        holder.tv_trainerexperienc.setText(trainers.getExperience()+" Years");
        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, AssignTrainerToMemberDetailsActivity.class);
                intent.putExtra("membername",membername);
                intent.putExtra("memberid",memberid);
                intent.putExtra("membership",membership);
                intent.putExtra("membershipkey",membershipkey);
                intent.putExtra("membershipdate",membershipdate);
                intent.putExtra("trainername",trainers.getName());
                intent.putExtra("trainerid",trainers.getTid());
                intent.putExtra("trainercontact",trainers.getContact());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return trainersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_trainernam,tv_trainercontact,tv_traineremail,tv_trainerexperienc,tv_trainerage,tv_trainerdescription;
        AppCompatButton select;
        ImageView trainerimage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_trainernam=itemView.findViewById(R.id.trainernametxt);
            tv_trainerexperienc=itemView.findViewById(R.id.trinerexperiencetxt);
            trainerimage=itemView.findViewById(R.id.trainerimagetxt);
            select=itemView.findViewById(R.id.select);

        }
    }
}
