package com.example.instagramclone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.Models.storyModal;
import com.example.instagramclone.R;

import java.util.ArrayList;
import java.util.List;

public class storyadapter extends RecyclerView.Adapter<storyadapter.viewholder>{
   ArrayList<storyModal> list;
   Context context;

    public storyadapter(ArrayList<storyModal> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.storyrv,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        storyModal model= list.get(position);
        holder.story.setImageResource(model.getStory());
        holder.profile.setImageResource(model.getProfile());
        holder.status.setImageResource(model.getStatus());
        holder.name.setText(model.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        ImageView story,profile,status;
        TextView name;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            story=itemView.findViewById(R.id.story);
            profile=itemView.findViewById(R.id.profile_image);
            status=itemView.findViewById(R.id.status);
            name=itemView.findViewById(R.id.name);

        }
    }
}
