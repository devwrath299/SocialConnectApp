package com.example.instagramclone.Adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.Models.Commentmodel;
import com.example.instagramclone.Models.User;
import com.example.instagramclone.R;
import com.example.instagramclone.databinding.CommentSampleBinding;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class commentadapter extends RecyclerView.Adapter<commentadapter.viewholder> {
    Context context;
    ArrayList<Commentmodel>list;

    public commentadapter(Context context, ArrayList<Commentmodel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.comment_sample,parent,false);
        return new viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
         Commentmodel model=list.get(position);
        String text = TimeAgo.using(model.getTime());
         holder.binding.textView23.setText(text);

        FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(model.getBy())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user=snapshot.getValue(User.class);
                        Picasso.get()
                                .load(user.getProfile())
                                .placeholder(R.drawable.place)
                                .into(holder.binding.circleImageView);
                        holder.binding.textView22.setText(Html.fromHtml("<b>"+user.getName() +"</b>"+" "+model.getMsg()));
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

    public class viewholder extends RecyclerView.ViewHolder{
        CommentSampleBinding binding;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            binding=CommentSampleBinding.bind(itemView);
        }
    }
}
