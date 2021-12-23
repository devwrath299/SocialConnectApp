package com.example.instagramclone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.instagramclone.Models.Friends;
import com.example.instagramclone.R;

import java.util.ArrayList;

public class friendsadapter extends RecyclerView.Adapter<friendsadapter.viewholder> {

    ArrayList<Friends>list;

    public friendsadapter(ArrayList<Friends> list, Context context) {
        this.list = list;
        this.context = context;
    }

    Context context;


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.friend,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        Friends fr=list.get(position);
        holder.friend.setImageResource(fr.getFriend());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
       ImageView friend;
       public viewholder(@NonNull View itemView) {
           super(itemView);
           friend=itemView.findViewById(R.id.friend_image);
       }
   }
}
