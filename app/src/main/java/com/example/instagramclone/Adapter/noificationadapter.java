package com.example.instagramclone.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.Models.User;
import com.example.instagramclone.Models.notifiaction;
import com.example.instagramclone.R;
import com.example.instagramclone.comment;
import com.example.instagramclone.databinding.Noti3Binding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

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
        notifiaction nf=list.get(position);
        String type=nf.getType();
        FirebaseDatabase.getInstance()
                .getReference()
                .child("Users")
                .child(nf.getNotifiactionby())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user=snapshot.getValue(User.class);
                        Picasso.get()
                                .load(user.getProfile())
                                .placeholder(R.drawable.place)
                                .into(holder.binding.profileImage);

                        if(type.equals("like"))
                        {
                            holder.binding.textView15.setText(Html.fromHtml( "<b>"+ user.getName()+"</b>"+" Liked Your Post"));
                        }
                        else if(type.equals("follow"))
                        {
                            holder.binding.textView15.setText(Html.fromHtml("<b>"+ user.getName()+"</b>"+" Start Following You"));
                        }
                        else{
                            holder.binding.textView15.setText(Html.fromHtml("<b>"+ user.getName()+"</b>"+" Commented On Your Post"));
                        }



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        holder.binding.open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!type.equals("follow")){
                    Intent intent =new Intent(context, comment.class);
                    intent.putExtra("postid",nf.getPostid());
                    intent.putExtra("postedby",nf.getPostedby());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);}
            }
        });






    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        Noti3Binding binding;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            binding=Noti3Binding.bind(itemView);
        }
    }

}