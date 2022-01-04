package com.example.instagramclone.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.Models.User;
import com.example.instagramclone.Models.postmodel;
import com.example.instagramclone.R;
import com.example.instagramclone.comment;
import com.example.instagramclone.databinding.PostsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class postadapter extends RecyclerView.Adapter<postadapter.viewholder> {

    ArrayList<postmodel>list;
    Context context;

    public postadapter(ArrayList<postmodel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.posts,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        postmodel model=list.get(position);
        Picasso.get()
                .load(model.getPostedimage())
                .placeholder(R.drawable.place)
                .into(holder.binding.story);
        String desc=model.getPostdescription();
        if(desc.isEmpty())
        {
            holder.binding.desc.setVisibility(View.GONE);
        }else
        {
            holder.binding.desc.setText(desc);
        }
        FirebaseDatabase.getInstance().getReference().child("Users")
                .child(model.getPostedby())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            User user=snapshot.getValue(User.class);
                            Picasso.get()
                                    .load(user.getProfile())
                                    .placeholder(R.drawable.place)
                                    .into(holder.binding.profileImage);
                            holder.binding.textView4.setText(user.getName());
                            holder.binding.textView5.setText(user.getProfession());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        FirebaseDatabase.getInstance().getReference()
                .child("Posts")
                .child(model.getPostid())
                .child("likes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.binding.liketext.setText(snapshot.getChildrenCount()+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        FirebaseDatabase.getInstance().getReference().child("Posts")
                .child(model.getPostid())
                .child("likes")
                .child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                   holder.binding.heart.setImageResource(R.drawable.redheart);
                   holder.binding.heart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FirebaseDatabase.getInstance().getReference()
                                    .child("Posts")
                                    .child(model.getPostid())
                                    .child("likes")
                                    .child(FirebaseAuth.getInstance().getUid())
                                    .setValue(null);
                            holder.binding.heart.setImageResource(R.drawable.heart);
                        }
                    });
                }
                else
                {
                    holder.binding.heart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FirebaseDatabase.getInstance().getReference()
                                    .child("Posts")
                                    .child(model.getPostid())
                                    .child("likes")
                                    .child(FirebaseAuth.getInstance().getUid())
                                    .setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    holder.binding.heart.setImageResource(R.drawable.redheart);
                                    Toast.makeText(context, "You liked this Post", Toast.LENGTH_SHORT).show();
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
        FirebaseDatabase.getInstance().getReference().child("Posts")
                .child(model.getPostid()).child("comments")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                       holder.binding.chattext.setText(snapshot.getChildrenCount()+"");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        holder.binding.chats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, comment.class);
                intent.putExtra("postid",model.getPostid());
                intent.putExtra("postedby",model.getPostedby());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

       PostsBinding binding;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            binding=PostsBinding.bind(itemView);
        }
    }
}
