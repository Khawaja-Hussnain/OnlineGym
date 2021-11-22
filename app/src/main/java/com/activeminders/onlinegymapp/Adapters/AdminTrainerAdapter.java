package com.activeminders.onlinegymapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.activeminders.onlinegymapp.Models.Trainers;
import com.activeminders.onlinegymapp.R;

import java.util.ArrayList;
import java.util.List;

public class AdminTrainerAdapter extends RecyclerView.Adapter<AdminTrainerAdapter.ViewHolder> {

    private List<Trainers> trainersList;
    Context context;


    public AdminTrainerAdapter(List<Trainers> trainersList, Context context) {
        this.trainersList = trainersList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_trainer_items_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Trainers trainers = trainersList.get(position);
        holder.tv_trainername.setText(trainers.getName());
        holder.tv_trainercontact.setText(trainers.getContact());
        holder.tv_trainerexperience.setText(trainers.getExperience() + " Years");
    }

    @Override
    public int getItemCount() {
        return trainersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_trainername, tv_trainercontact, tv_traineremail, tv_trainerexperience, tv_trainerage, tv_trainerdescription;
        ImageView trainerimage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_trainername = itemView.findViewById(R.id.admintrainername);
            tv_trainerexperience = itemView.findViewById(R.id.admintrainerexperience);
            tv_trainercontact=itemView.findViewById(R.id.admintrainercontact);
            trainerimage = itemView.findViewById(R.id.admintrainerimage);
        }
    }

   /* public void SetSearchItems(List<Trainers> list) {
        trainersList = new ArrayList<>();
        trainersList.addAll(list);
        notifyDataSetChanged();
    }*/
}