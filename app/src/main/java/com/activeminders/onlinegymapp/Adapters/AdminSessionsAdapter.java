package com.activeminders.onlinegymapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.activeminders.onlinegymapp.Models.Session;
import com.activeminders.onlinegymapp.R;

import java.util.List;

public class AdminSessionsAdapter extends RecyclerView.Adapter<AdminSessionsAdapter.ViewHolder> {

    List<Session> sessionsList;
    Context context;

    public AdminSessionsAdapter(List<Session> sessionsList, Context context) {
        this.sessionsList = sessionsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_session_items_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Session session=sessionsList.get(position);
        holder.tv_sessionTitle.setText(session.getSessiontitle());
        holder.tv_sessiondays.setText(session.getSessiondays());
        holder.tv_sessionfee.setText(session.getSessionfee());

    }

    @Override
    public int getItemCount() {
        return sessionsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_sessionTitle,tv_sessiondays,tv_sessionfee;
        AppCompatButton sendNotification;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_sessionTitle=itemView.findViewById(R.id.sessiontitletext);
            tv_sessiondays=itemView.findViewById(R.id.sessionnumdays);
            tv_sessionfee=itemView.findViewById(R.id.sessionfee);
            sendNotification=itemView.findViewById(R.id.sessionnotificationbtn);
        }
    }

}
