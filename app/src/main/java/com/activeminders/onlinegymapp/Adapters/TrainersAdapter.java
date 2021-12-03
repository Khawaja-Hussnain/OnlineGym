package com.activeminders.onlinegymapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.activeminders.onlinegymapp.Gym.DrawerMainActivity;
import com.activeminders.onlinegymapp.Models.Gyms;
import com.activeminders.onlinegymapp.Models.Trainers;
import com.activeminders.onlinegymapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TrainersAdapter extends RecyclerView.Adapter<TrainersAdapter.ViewHolder> {

    private List<Trainers> trainersList;
    Context context;

    public TrainersAdapter(List<Trainers> trainersList, Context context) {
        this.trainersList = trainersList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.trainer_items_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Trainers trainers=trainersList.get(position);
        holder.tv_trainername.setText(trainers.getName());
        holder.tv_trainerexperience.setText(trainers.getExperience()+" Years");
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Gyms").child(trainers.getGid()).child("Trainers")
                        .child(trainers.getTid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(context, "Trainer deleted successfully", Toast.LENGTH_SHORT).show();
                            context.startActivity(new Intent(context, DrawerMainActivity.class));
                            ((Activity)context).finish();
                        }
                    }
                });
                Toast.makeText(context, ""+trainers.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return trainersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_trainername,tv_trainercontact,tv_traineremail,tv_trainerexperience,tv_trainerage,tv_trainerdescription;
        AppCompatButton delete;
        ImageView trainerimage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_trainername=itemView.findViewById(R.id.trainername);
            tv_trainerexperience=itemView.findViewById(R.id.trinerexperience);
            trainerimage=itemView.findViewById(R.id.trainerimage);
            delete=itemView.findViewById(R.id.deltrainer);

        }
    }

    public void SetSearchItems(List<Trainers> list) {
        trainersList=new ArrayList<>();
        trainersList.addAll(list);
        notifyDataSetChanged();
    }

}
