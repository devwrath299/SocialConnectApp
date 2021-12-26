package com.example.instagramclone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.Fragment.SearchFragment;
import com.example.instagramclone.Models.User;
import com.example.instagramclone.R;
import com.example.instagramclone.databinding.UsersearchBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class searchoption extends RecyclerView.Adapter<searchoption.viewholder>{
  ArrayList<User> list;
  Context context;

    public searchoption(ArrayList<User> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.usersearch,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        User user=list.get(position);
        Picasso.get()
                .load(user.getProfile())
                .placeholder(R.drawable.place)
                .into(holder.binding.circleImageView);

        holder.binding.textView22.setText(user.getName());
        holder.binding.textView23.setText(user.getProfession());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        UsersearchBinding binding;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            binding=UsersearchBinding.bind(itemView);
        }
    }

}
