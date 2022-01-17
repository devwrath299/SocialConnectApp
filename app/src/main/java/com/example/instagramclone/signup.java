package com.example.instagramclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.instagramclone.Models.User;
import com.example.instagramclone.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {

    ActivitySignupBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase  database;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        dialog=new ProgressDialog(signup.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Info Checking");
        dialog.setMessage("Please wait...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        binding.textView21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signup.this, Login.class));
            }
        });

        binding.materialButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email,password,username,profession;
                email=binding.emails.getText().toString();
                password=binding.passwds.getText().toString();
                username=binding.names.getText().toString();
                profession=binding.professions.getText().toString();
                if(!email.isEmpty() && !password.isEmpty() && !username.isEmpty() && !profession.isEmpty())
                Signup(email,password,username,profession);
                else
                    Toast.makeText(getApplicationContext(), "Please Fill all Info", Toast.LENGTH_SHORT).show();


            }
        });


    }

    public void Signup(String email, String password, String username, String profession) {
   dialog.show();
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                dialog.cancel();
                if(task.isSuccessful())
                {
                   User user=new User(email,password,username,profession);
                   String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                   database.getReference().child("Users").child(currentuser).setValue(user);
                   startActivity(new Intent(getApplicationContext(), MainActivity.class));
                   finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }




}