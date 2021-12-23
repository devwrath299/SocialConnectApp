package com.example.instagramclone.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instagramclone.Adapter.friendsadapter;
import com.example.instagramclone.Adapter.storyadapter;
import com.example.instagramclone.MainActivity;
import com.example.instagramclone.Models.Friends;
import com.example.instagramclone.Models.storyModal;
import com.example.instagramclone.R;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);

        setHasOptionsMenu(true);

        rv=view.findViewById(R.id.rvs);
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

        return view;

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menuitem,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}