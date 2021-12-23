package com.example.instagramclone.Adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.Models.Friends;
import com.example.instagramclone.Models.notifiaction;
import com.example.instagramclone.R;

import java.util.ArrayList;

public class noificationadapter extends RecyclerView.Adapter<noificationadapter.viewholder> {

    ArrayList<notifiaction>list;
    Context context;

    public noificationadapter(ArrayList<notifiaction> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.noti3,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        notifiaction fr=list.get(position);
        holder.profile.setImageResource(fr.getProfile());
        holder.post.setText(Html.fromHtml( fr.getPost()));
        holder.time.setText(fr.getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
         ImageView profile;
         TextView post,time;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            profile=itemView.findViewById(R.id.profile_image);
            post=itemView.findViewById(R.id.textView15);
            time=itemView.findViewById(R.id.textView16);
        }
    }

}
