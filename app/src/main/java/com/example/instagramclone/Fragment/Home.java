package com.example.instagramclone.Fragment;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.RecoverySystem;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.example.instagramclone.Adapter.postadapter;
import com.example.instagramclone.Adapter.storyadapter;
import com.example.instagramclone.Models.postmodel;
import com.example.instagramclone.Models.storyModal;
import com.example.instagramclone.Models.userstories;
import com.example.instagramclone.R;
import com.example.instagramclone.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;

public class Home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Home() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    FirebaseDatabase database;
    FirebaseAuth auth;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

    }
    ShimmerRecyclerView kv,rv;
    ArrayList<storyModal>list;
    ArrayList<postmodel>klist;
    FragmentHomeBinding binding;
    ActivityResultLauncher<String> launcher;
    ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        binding= FragmentHomeBinding.inflate(inflater, container, false);
        rv=binding.storyrv;
        kv=binding.kv;
        rv.showShimmerAdapter();
        kv.showShimmerAdapter();
        list=new ArrayList<>();
        klist=new ArrayList<>();
        dialog=new ProgressDialog(getContext());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Story Uploading");
        dialog.setMessage("Please wait...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);



        binding.story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               launcher.launch("image/*");

            }
        });

        launcher=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {

               if(result!=null){
                   dialog.show();

                StorageReference reference=FirebaseStorage.getInstance().getReference()
                        .child("stories")
                        .child(auth.getUid())
                        .child(new Date().getTime()+"");

                    reference.putFile(result).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                storyModal modal=new storyModal();
                                modal.setStoryat(new Date().getTime());
                                database.getReference()
                                        .child("stories")
                                        .child(auth.getUid())
                                        .child("postedAt")
                                        .setValue(modal.getStoryat()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        userstories storries=new userstories(uri.toString(),modal.getStoryat());

                                        database.getReference()
                                                .child("stories")
                                                .child(auth.getUid())
                                                .child("userStories")
                                                .push()
                                                .setValue(storries);
                                        dialog.dismiss();
                                        Toast.makeText(getContext(), "Story Sucessfully Uploaded", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        });
                    }
                });


            }}
        });


        storyadapter  adapter=new storyadapter(list,getContext());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        rv.setLayoutManager(linearLayoutManager);
        rv.setNestedScrollingEnabled(false);


        database.getReference()
                .child("stories")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        if(snapshot.exists())
                        {
                            for(DataSnapshot ds:snapshot.getChildren())
                            {
                                storyModal story=new storyModal();
                                story.setStoryby(ds.getKey());
                                story.setStoryat(ds.child("postedAt").getValue(Long.class));

                                ArrayList<userstories>us=new ArrayList<>();

                                for (DataSnapshot d:ds.child("userStories").getChildren())
                                {
                                     userstories uv=d.getValue(userstories.class);
                                     us.add(uv);
                                }
                                story.setUserstories(us);
                                list.add(story);
                            }
                            rv.setAdapter(adapter);
                            rv.hideShimmerAdapter();
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



        postadapter pstadapter=new postadapter(klist,getContext());
        LinearLayoutManager lm=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        kv.setLayoutManager(lm);
        kv.setNestedScrollingEnabled(false);

        database.getReference().child("Posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                klist.clear();
                for(DataSnapshot ds:snapshot.getChildren())
                {
                    postmodel post=ds.getValue(postmodel.class);
                    post.setPostid(ds.getKey());
                    klist.add(post);

                }
                kv.setAdapter(pstadapter);
                kv.hideShimmerAdapter();
                pstadapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        return binding.getRoot();
    }
}


