package com.example.instagramclone.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.instagramclone.Adapter.friendsadapter;
import com.example.instagramclone.Adapter.storyadapter;
import com.example.instagramclone.MainActivity;
import com.example.instagramclone.Models.Friends;
import com.example.instagramclone.Models.User;
import com.example.instagramclone.Models.storyModal;
import com.example.instagramclone.R;
import com.example.instagramclone.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    RecyclerView rv;
    ArrayList<Friends> list;
    FragmentProfileBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding=FragmentProfileBinding.inflate(inflater,container,false);
        Toolbar toolbar = binding.toolbar;
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);

        setHasOptionsMenu(true);

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();

        rv=binding.rvs;
        list=new ArrayList<>();
        list.add(new Friends(R.drawable.secondboy));
        list.add(new Friends(R.drawable.secondboy));
        list.add(new Friends(R.drawable.secondboy));
        list.add(new Friends(R.drawable.secondboy));
        list.add(new Friends(R.drawable.secondboy));
        list.add(new Friends(R.drawable.secondboy));
        list.add(new Friends(R.drawable.secondboy));
        list.add(new Friends(R.drawable.secondboy));
        list.add(new Friends(R.drawable.secondboy));
        friendsadapter adapter=new friendsadapter(list,getContext());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        rv.setLayoutManager(linearLayoutManager);
        rv.setNestedScrollingEnabled(false);
        rv.setAdapter(adapter);

        //background Profile image update code

        binding.change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,22);
            }
        });

        //background small photo of profile
        binding.right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,11);
            }
        });




        return binding.getRoot();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( data!=null && data.getData()!=null && requestCode==22) {

            try {
                Uri uri = data.getData();
                binding.profile.setImageURI(uri);
                final StorageReference reference = storage.getReference().child("cover_images").child(auth.getUid());
                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getContext(), "Image Uploaded Sucessfully", Toast.LENGTH_SHORT).show();
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                database.getReference().child("Users").child(auth.getUid()).child("cover_photo").setValue(uri.toString());
                            }
                        });
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


         if(data!=null && data.getData()!=null && requestCode==11)
        {

            try {
                Uri uri=data.getData();
                binding.profileImage.setImageURI(uri);
                final StorageReference reference=storage.getReference().child("profileimage").child(auth.getUid());
                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getContext(), "Image Uploaded Sucessfully", Toast.LENGTH_SHORT).show();
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                database.getReference().child("Users").child(auth.getUid()).child("Profile").setValue(uri.toString());
                            }
                        });
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void onStart() {
        super.onStart();
        database.getReference().child("Users").child(auth.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            User user=snapshot.getValue(User.class);
                            Picasso.get()
                                    .load(user.getCover_photo())
                                    .placeholder(R.drawable.place)
                                    .into(binding.profile);
                            Picasso.get()
                                    .load(user.getProfile())
                                    .placeholder(R.drawable.place)
                                    .into(binding.profileImage);



                            binding.namess.setText(user.getName());
                            binding.aboutss.setText(user.getProfession());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menuitem,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}