package com.activeminders.onlinegymapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.activeminders.onlinegymapp.Models.Gyms;
import com.activeminders.onlinegymapp.Models.Machines;
import com.activeminders.onlinegymapp.R;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MachinesAdapter extends RecyclerView.Adapter<MachinesAdapter.ViewHolder> {

    private List<Machines> machinesList;
    Context context;

    public MachinesAdapter(List<Machines> gymsList, Context context) {
        this.machinesList = gymsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.machines_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Machines machines=machinesList.get(position);
        holder.tv_machinename.setText(machines.getMachinename());
        holder.tv_quantity.setText("("+machines.getQuantity()+")");
        Glide.with(context).load(machines.getMachineimage()).into(holder.machineimage);

    }

    @Override
    public int getItemCount() {
        return machinesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_machinename,tv_quantity;
        ImageView machineimage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_machinename=itemView.findViewById(R.id.machine_name);
            tv_quantity=itemView.findViewById(R.id.machine_quantity);
            machineimage=itemView.findViewById(R.id.machine_image);
        }
    }
}
