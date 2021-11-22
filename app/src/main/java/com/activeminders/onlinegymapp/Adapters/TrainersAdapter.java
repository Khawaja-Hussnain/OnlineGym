package com.activeminders.onlinegymapp.Adapters;

import android.content.Context;
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

import com.activeminders.onlinegymapp.Models.Gyms;
import com.activeminders.onlinegymapp.Models.Trainers;
import com.activeminders.onlinegymapp.R;

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
