package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.instagramclone.Fragment.AddFragment;
import com.example.instagramclone.Fragment.Home;
import com.example.instagramclone.Fragment.Noti2;
import com.example.instagramclone.Fragment.Notification;
import com.example.instagramclone.Fragment.ProfileFragment;
import com.example.instagramclone.Fragment.SearchFragment;
import com.example.instagramclone.databinding.ActivityMainBinding;
import com.iammert.library.readablebottombar.ReadableBottomBar;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FragmentTransaction  transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,new Home());
        transaction.commit();
        binding.Readablebottombar.setOnItemSelectListener(new ReadableBottomBar.ItemSelectListener() {
            @Override
            public void onItemSelected(int i) {
                FragmentTransaction  transaction=getSupportFragmentManager().beginTransaction();
                switch(i){
                    case 0:
                        transaction.replace(R.id.container,new Home());
                        break;
                    case 1:
                        transaction.replace(R.id.container,new Noti2());
                        break;
                    case 2:
                        transaction.replace(R.id.container,new AddFragment());
                        break;
                    case 3:
                        transaction.replace(R.id.container,new SearchFragment());
                        break;
                    case 4:
                        transaction.replace(R.id.container,new ProfileFragment());
                        break;
                }
                transaction.commit();
            }
        });


    }
}