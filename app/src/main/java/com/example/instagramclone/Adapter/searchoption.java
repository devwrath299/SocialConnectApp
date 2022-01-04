package com.example.instagramclone.Adapter;

import android.content.Context;
import android.graphics.DashPathEffect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.Fragment.Notification;
import com.example.instagramclone.Fragment.SearchFragment;
import com.example.instagramclone.Models.Follow;
import com.example.instagramclone.Models.User;
import com.example.instagramclone.Models.notifiaction;
import com.example.instagramclone.R;
import com.example.instagramclone.databinding.UsersearchBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        //Toast.makeText(context, user.getFollowerCnt()+"", Toast.LENGTH_SHORT).show();
        Picasso.get()
                .load(user.getProfile())
                .placeholder(R.drawable.place)
                .into(holder.binding.circleImageView);

        holder.binding.textView22.setText(user.getName());
        holder.binding.textView23.setText(user.getProfession());
        FirebaseDatabase.getInstance().getReference().child("Users")
                .child(user.getID()).child("Followers").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            holder.binding.FollowButton.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.follow_button));
                            holder.binding.FollowButton.setText("Following");
                            holder.binding.FollowButton.setTextColor(context.getResources().getColor(R.color.black));
                            holder.binding.FollowButton.setEnabled(false);

                        }
                        else
                        {
                            holder.binding.FollowButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    //Toast.makeText(context, user.getName(), Toast.LENGTH_SHORT).show();
                                    Follow follow=new Follow();
                                    follow.setFollowedBy(FirebaseAuth.getInstance().getUid());
                                    follow.setFollowedAt(new Date().getTime());

                                    FirebaseDatabase.getInstance().getReference()
                                            .child("Users")
                                            .child(user.getID())
                                            .child("Followers")
                                            .child(FirebaseAuth.getInstance().getUid())
                                            .setValue(follow).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {

                                            FirebaseDatabase.getInstance().getReference()
                                                    .child("Users")
                                                    .child(user.getID())
                                                    .child("FollowerCount")
                                                    .setValue(user.getFollowerCnt()+1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {


                                                    Toast.makeText(context, "You Followed "+ user.getName(), Toast.LENGTH_SHORT).show();
                                                    notifiaction nf=new notifiaction();
                                                    nf.setNotifiactionby(FirebaseAuth.getInstance().getUid());
                                                    nf.setNotificationat(new Date().getTime());
                                                    nf.setType("follow");

                                                    FirebaseDatabase.getInstance().getReference()
                                                            .child("Notification")
                                                            .child(user.getID())
                                                            .push()
                                                            .setValue(nf);


                                                }
                                            });
                                        }
                                    });


                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


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
