package com.example.instagramclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.instagramclone.Adapter.commentadapter;
import com.example.instagramclone.Models.Commentmodel;
import com.example.instagramclone.Models.User;
import com.example.instagramclone.Models.notifiaction;
import com.example.instagramclone.Models.postmodel;
import com.example.instagramclone.databinding.ActivityCommentBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class comment extends AppCompatActivity {


    Intent intent;
    String postid,postedby;
    ActivityCommentBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ArrayList<Commentmodel>list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCommentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intent=getIntent();
        postid=intent.getStringExtra("postid");
        postedby=intent.getStringExtra("postedby");

        setSupportActionBar(binding.toolbar);
        comment.this.setTitle("Comments");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        database.getReference().child("Posts")
                .child(postid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        postmodel post=snapshot.getValue(postmodel.class);
                        if(post!=null && post.getPostedimage()!=null){
                        Picasso.get()
                                .load(post.getPostedimage())
                                .placeholder(R.drawable.place)
                                .into(binding.imageView11);

                        binding.desc.setText(post.getPostdescription());}


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



        database.getReference().child("Users")
                .child(postedby)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user=snapshot.getValue(User.class);
                        Picasso.get()
                                .load(user.getProfile())
                                .placeholder(R.drawable.place)
                                .into(binding.circleImageView);

                        binding.textView22.setText(user.getName());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        binding.imageView14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Commentmodel comments=new Commentmodel();
                comments.setMsg(binding.editTextTextPersonName3.getText().toString());
                comments.setTime(new Date().getTime());
                comments.setBy(auth.getUid());

                database.getReference()
                        .child("Posts")
                        .child(postid)
                        .child("comments")
                        .push()
                        .setValue(comments)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                binding.editTextTextPersonName3.setText("");
                                Toast.makeText(getApplicationContext(), "Commented", Toast.LENGTH_SHORT).show();

                                notifiaction nf=new notifiaction();
                                nf.setNotifiactionby(auth.getUid());
                                nf.setNotificationat(new Date().getTime());
                                nf.setPostid(postid);
                                nf.setPostedby(postedby);
                                nf.setType("comment");

                                FirebaseDatabase.getInstance().getReference()
                                        .child("Notification")
                                        .child(postedby)
                                        .push()
                                        .setValue(nf);

                            }
                        });
            }
        });

        database.getReference().child("Posts")
                .child(postid).child("comments")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        binding.chattext.setText(snapshot.getChildrenCount()+"");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        database.getReference().child("Posts")
                .child(postid).child("likes")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        binding.liketext.setText(snapshot.getChildrenCount()+"");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        list=new ArrayList<>();
        commentadapter adapter=new commentadapter(this,list);
        LinearLayoutManager lm=new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(lm);
        binding.recyclerView.setAdapter(adapter);

        database.getReference().child("Posts")
                .child(postid)
                .child("comments")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for(DataSnapshot ds:snapshot.getChildren())
                        {
                            list.add(ds.getValue(Commentmodel.class));
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}