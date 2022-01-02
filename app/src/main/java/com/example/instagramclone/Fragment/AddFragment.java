package com.example.instagramclone.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.instagramclone.Models.User;
import com.example.instagramclone.Models.postmodel;
import com.example.instagramclone.R;
import com.example.instagramclone.databinding.FragmentAddBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    ProgressDialog dialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();
        dialog=new ProgressDialog(getContext());

    }
    FragmentAddBinding binding;
    Uri uri;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding= FragmentAddBinding.inflate(inflater, container, false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Post Uploading");
        dialog.setMessage("Please wait...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);


        binding.imageView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,11);
            }
        });

        binding.appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                StorageReference reference=storage.getReference().child("posts")
                        .child(auth.getUid())
                        .child(new Date().getTime()+"");
                if(uri!=null)
                {
                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                               reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                   @Override
                                   public void onSuccess(Uri uri) {
                                       postmodel post=new postmodel();
                                       post.setPostedimage(uri.toString());
                                       post.setPostedby(auth.getUid());
                                       post.setPostdescription(binding.editTextTextPersonName.getText().toString());
                                       post.setPostedat(new Date().getTime());
                                       database.getReference().child("Posts")
                                               .push()
                                               .setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                                           @Override
                                           public void onSuccess(Void unused) {
                                               dialog.dismiss();
                                               Toast.makeText(getContext(), "Posted Successfully", Toast.LENGTH_SHORT).show();
                                           }
                                       });
                                   }
                               });
                    }
                });

            }}
        });



        database.getReference().child("Users").child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    User user=snapshot.getValue(User.class);
                    Picasso.get()
                            .load(user.getProfile())
                            .placeholder(R.drawable.place)
                            .into(binding.circleImageView);

                    binding.textView22.setText(user.getName());
                    binding.textView23.setText(user.getProfession());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null && data.getData()!=null)
        {
          uri=data.getData();
          binding.imageView9.setVisibility(View.VISIBLE);
          binding.imageView9.setImageURI(uri);
          binding.appCompatButton.setBackgroundDrawable(ContextCompat.getDrawable(getContext(),R.drawable.secondfollow));
          binding.appCompatButton.setTextColor(getContext().getResources().getColor(R.color.white));
          binding.appCompatButton.setEnabled(true);
        }
    }


}