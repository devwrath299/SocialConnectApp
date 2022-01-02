package com.example.instagramclone.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.RecoverySystem;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instagramclone.Adapter.postadapter;
import com.example.instagramclone.Adapter.storyadapter;
import com.example.instagramclone.Models.postmodel;
import com.example.instagramclone.Models.storyModal;
import com.example.instagramclone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

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
    RecyclerView rv,kv;
    ArrayList<storyModal>list;
    ArrayList<postmodel>klist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        rv=view.findViewById(R.id.storyrv);
        kv=view.findViewById(R.id.kv);
        list=new ArrayList<>();
        klist=new ArrayList<>();

        list.add(new storyModal(R.drawable.boy,R.drawable.live2,R.drawable.boy,"Devwrath Vats"));
        list.add(new storyModal(R.drawable.boy,R.drawable.live2,R.drawable.boy,"Devwrath Vats"));
        list.add(new storyModal(R.drawable.boy,R.drawable.live2,R.drawable.boy,"Devwrath Vats"));
        list.add(new storyModal(R.drawable.boy,R.drawable.live2,R.drawable.boy,"Devwrath Vats"));
        list.add(new storyModal(R.drawable.boy,R.drawable.live2,R.drawable.boy,"Devwrath Vats"));


        storyadapter  adapter=new storyadapter(list,getContext());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        rv.setLayoutManager(linearLayoutManager);
        rv.setNestedScrollingEnabled(false);
        rv.setAdapter(adapter);

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
                pstadapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        kv.setAdapter(pstadapter);

        return view;
    }
}


