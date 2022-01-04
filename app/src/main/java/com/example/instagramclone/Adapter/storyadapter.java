package com.example.instagramclone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.Models.User;
import com.example.instagramclone.Models.storyModal;
import com.example.instagramclone.Models.userstories;
import com.example.instagramclone.R;
import com.example.instagramclone.databinding.StoryrvBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import omari.hamza.storyview.StoryView;
import omari.hamza.storyview.callback.StoryClickListeners;
import omari.hamza.storyview.model.MyStory;

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
        storyModal story= list.get(position);
        if(story.getUserstories().size()>0) {
            userstories last = story.getUserstories().get(story.getUserstories().size() - 1);
            Picasso.get()
                    .load(last.getImage())
                    .placeholder(R.drawable.place)
                    .into(holder.binding.story);

            holder.binding.circularStatusView.setPortionsCount(story.getUserstories().size());
            FirebaseDatabase.getInstance().getReference()
                    .child("Users")
                    .child(story.getStoryby())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User user = snapshot.getValue(User.class);
                            Picasso.get()
                                    .load(user.getProfile())
                                    .placeholder(R.drawable.place)
                                    .into(holder.binding.profileImage);

                            holder.binding.name.setText(user.getName());
                            holder.binding.story.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ArrayList<MyStory> myStories = new ArrayList<>();

                                    for (userstories story : story.getUserstories()) {
                                        myStories.add(new MyStory(
                                                story.getImage()
                                        ));
                                    }

                                    new StoryView.Builder(((AppCompatActivity) context).getSupportFragmentManager())
                                            .setStoriesList(myStories) // Required
                                            .setStoryDuration(5000) // Default is 2000 Millis (2 Seconds)
                                            .setTitleText(user.getName()) // Default is Hidden// Default is Hidden
                                            .setTitleLogoUrl(user.getProfile()) // Default is Hidden
                                            .setStoryClickListeners(new StoryClickListeners() {
                                                @Override
                                                public void onDescriptionClickListener(int position) {
                                                    //your action
                                                }

                                                @Override
                                                public void onTitleIconClickListener(int position) {
                                                    //your action
                                                }
                                            }) // Optional Listeners
                                            .build() // Must be called before calling show method
                                            .show();


                                }
                            });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
       StoryrvBinding binding;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            binding=StoryrvBinding.bind(itemView);
        }
    }
}
