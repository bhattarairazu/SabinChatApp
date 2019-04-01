package com.example.manjil.sabinchat.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.manjil.sabinchat.Adapters.Custom_chathome_Adpater;
import com.example.manjil.sabinchat.Model.Model_HomeChat;
import com.example.manjil.sabinchat.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 3/31/2019.
 */

public class Chat_Profile extends Fragment {
    private String [] mlistnames = {"Manjil","Razu","Diwas","Hari","Shyam","Geeta","Sita","Lakshman"};
    private String [] lastunseen_messages = {"Hell ohow are ou","go to school","I am fine","cool","ok bye","good morning","waht are you doing","I am plaing"};
    private boolean [] onlinesatus = {true,false,false,true,true,false,true,false};
    private String [] dates ={"6:45 AM","5:41 am","6:45 AM","7:56 PM","6:45 AM","5:41 am","6:45 AM","7:56 PM"};
    private int [] totlamessages = {5,8,12,4,3,6,1,2};

    private ListView mlistviews;
    private List<Model_HomeChat> mhomechatlisst = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mview = inflater.inflate(R.layout.chat_profile,container,false);
        //initviews
        initnview(mview);
        //adapter data initilization
        laodadapterdata();
        return mview;
    }
    public void initnview(View mviews){
        mlistviews =(ListView) mviews.findViewById(R.id.mlistview_homechat);


    }
    public void laodadapterdata(){
        for(int j = 0;j<mlistnames.length;j++){
            Model_HomeChat mhome = new Model_HomeChat();
            mhome.setDate(dates[j]);
            mhome.setName(mlistnames[j]);
            mhome.setLast_message(lastunseen_messages[j]);
            mhome.setOnline_status(onlinesatus[j]);
            mhome.setNo_of_unseenmessages(totlamessages[j]);
            mhomechatlisst.add(mhome);

        }
        mlistviews.setAdapter(new Custom_chathome_Adpater(mhomechatlisst,getContext()));
    }
}
