package com.example.manjil.sabinchat.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.manjil.sabinchat.Activity.SingleChat;
import com.example.manjil.sabinchat.Adapters.Custom_chathome_Adpater;
import com.example.manjil.sabinchat.Model.Model_HomeChat;
import com.example.manjil.sabinchat.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 3/31/2019.
 */

public class Chat_Profile extends Fragment {
    private static final String TAG = "Chat_Profile";
    private String [] mlistnames = {"Manjil","Razu","Diwas","Hari","Shyam","Geeta","Sita","Lakshman"};
    private String [] lastunseen_messages = {"Hell ohow are ou","go to school","I am fine","cool","ok bye","good morning","waht are you doing","I am plaing"};
    private boolean [] onlinesatus = {true,false,false,true,true,false,true,false};
    private String [] dates ={"6:45 AM","5:41 am","6:45 AM","7:56 PM","6:45 AM","5:41 am","6:45 AM","7:56 PM"};
    private int [] totlamessages = {5,8,12,4,3,6,1,2};
    //setarch edit text
    private EditText msearacheditext;
    private ListView mlistviews;
    private List<Model_HomeChat> mhomechatlisst = new ArrayList<>();
    private Custom_chathome_Adpater madpters;
    private ImageView searchimageview;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mview = inflater.inflate(R.layout.chat_profile,container,false);
        //initviews
        initnview(mview);
        //adapter data initilization
        laodadapterdata();
        onclicklisteners();
        return mview;
    }
    public void initnview(View mviews){
        //search icon imag view
        searchimageview =(ImageView) mviews.findViewById(R.id.mimageview_searchs);
       msearacheditext =(EditText) mviews.findViewById(R.id.medittext_searches);
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
        madpters = new Custom_chathome_Adpater(mhomechatlisst,getContext());
        mlistviews.setAdapter(madpters);
        //listview setonclick listeners
        mlistviews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String names = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "onItemClick: names of respected list items"+names);
                startActivity(new Intent(view.getContext(), SingleChat.class).putExtra("name",names));
            }
        });

    }
    public void onclicklisteners(){
        //ediitext filter onlcick listenrs
        msearacheditext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged: texts"+s);
                searchimageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_cancel_black_24dp));
                madpters.getFilter().filter(s.toString());
                searchimageview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        msearacheditext.setText("");
                        searchimageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_search));
                        madpters = new Custom_chathome_Adpater(mhomechatlisst,getContext());
                        mlistviews.setAdapter(madpters);
                        madpters.notifyDataSetChanged();
                    }
                });


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
