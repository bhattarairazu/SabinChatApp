package com.example.manjil.sabinchat.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.manjil.sabinchat.Adapters.ListOfPeople_Adpater;
import com.example.manjil.sabinchat.Model.PeopleListModel;
import com.example.manjil.sabinchat.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 3/31/2019.
 */

public class ListOfPeople extends Fragment {
    private String [] mlistnames = {"Manjil","Razu","Diwas","Hari","Shyam","Geeta","Sita","Lakshman"};
    private ListView mlistview_people;
    private List<PeopleListModel> mlist = new ArrayList<>();

    //private String [] images = ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mview = inflater.inflate(R.layout.fragment_listofpeople,container,false);
        //init views


        initview(mview);
        //initiliziting adapters
        loadadapterdatas();
        return mview;
    }
    public void initview(View miews){
        //initilizing listview
        mlistview_people =(ListView) miews.findViewById(R.id.mlistview_activeusers);
    }
    public void loadadapterdatas(){
        for(int i = 0;i<mlistnames.length;i++){
            PeopleListModel mlistmodels = new PeopleListModel();
            mlistmodels.setName(mlistnames[i]);
            mlist.add(mlistmodels);
        }
        mlistview_people.setAdapter(new ListOfPeople_Adpater(mlist,getContext()));


    }
}
