package com.example.instagramclone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.Models.postmodel;
import com.example.instagramclone.R;

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
        holder.story.setImageResource(model.getStory());
        holder.profileimage.setImageResource(model.getProfileimage());
        holder.like.setImageResource(model.getLike());
        holder.save.setImageResource(model.getSave());
        holder.share.setImageResource(model.getShare());
        holder.chat.setImageResource(model.getChat());
        holder.threedots.setImageResource(model.getThreedots());
        holder.name.setText(model.getName());
        holder.about.setText(model.getAbout());
        holder.sharetext.setText(model.getSharetext());
        holder.liketext.setText(model.getLiketext());
        holder.chattext.setText(model.getChattext());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        ImageView story,profileimage,like,save,share,chat,threedots;
        TextView name,about,liketext,sharetext,chattext;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            story=itemView.findViewById(R.id.story);
            profileimage=itemView.findViewById(R.id.profile_image);
            like=itemView.findViewById(R.id.heart);
            share=itemView.findViewById(R.id.share);
            chat=itemView.findViewById(R.id.chats);
            save=itemView.findViewById(R.id.imageView7);
            threedots=itemView.findViewById(R.id.imageView2);
            name=itemView.findViewById(R.id.textView4);
            about=itemView.findViewById(R.id.textView5);
            liketext=itemView.findViewById(R.id.liketext);
            sharetext=itemView.findViewById(R.id.sharetext);
            chattext=itemView.findViewById(R.id.chattext);



        }
    }
}
