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

import com.activeminders.onlinegymapp.Models.Session;
import com.activeminders.onlinegymapp.R;
import com.activeminders.onlinegymapp.User.PaymentActivity;
import com.activeminders.onlinegymapp.User.SessionPaymentActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class UserSessionAdapater extends RecyclerView.Adapter<UserSessionAdapater.ViewHolder> {

    List<Session> sessionsList;
    Context context;

    public UserSessionAdapater(List<Session> sessionsList, Context context) {
        this.sessionsList = sessionsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_session_items_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Session session = sessionsList.get(position);
        holder.tv_gymname.setText(session.getGymname());
        holder.tv_sessionTitle.setText(session.getSessiontitle());
        holder.tv_sessiondays.setText(session.getSessiondays());
        holder.tv_sessionfee.setText(session.getSessionfee());
        if (session.getGymlogo().equals("default")){
            holder.gymlogo.setImageResource(R.drawable.session);
        }else
        {
            Glide.with(context).load(session.getGymlogo()).into(holder.gymlogo);
        }
        holder.enrollNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, SessionPaymentActivity.class);
                intent.putExtra("gymid",session.getGymid());
                intent.putExtra("gymname",session.getGymname());
                intent.putExtra("gymlogo",session.getGymlogo());
                intent.putExtra("sessionDays",session.getSessiondays());
                intent.putExtra("sessionTitle",session.getSessiontitle());
                intent.putExtra("sessionId",session.getKey());
                intent.putExtra("sessionFee",session.getSessionfee());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return sessionsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_sessionTitle, tv_sessiondays, tv_sessionfee,tv_gymname;
        ImageView gymlogo;
        AppCompatButton enrollNow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_sessionTitle = itemView.findViewById(R.id.sessiontitletext);
            tv_sessiondays = itemView.findViewById(R.id.sessionnumdays);
            tv_sessionfee = itemView.findViewById(R.id.sessionfee);
            tv_gymname = itemView.findViewById(R.id.gymnametext);
            gymlogo = itemView.findViewById(R.id.gymlogoimg);
            enrollNow = itemView.findViewById(R.id.enrollnow);
        }
    }

    public void SetSearchItems(List<Session> list) {
        sessionsList=new ArrayList<>();
        sessionsList.addAll(list);
        notifyDataSetChanged();
    }
}