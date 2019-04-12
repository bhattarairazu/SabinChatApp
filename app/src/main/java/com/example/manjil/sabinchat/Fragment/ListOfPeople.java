package com.example.manjil.sabinchat.Fragment;

import android.content.Context;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.manjil.sabinchat.Activity.SingleChat;
import com.example.manjil.sabinchat.Adapters.Custom_chathome_Adpater;
import com.example.manjil.sabinchat.Adapters.ListOfPeople_Adpater;
import com.example.manjil.sabinchat.Model.PeopleListModel;
import com.example.manjil.sabinchat.R;
import com.example.manjil.sabinchat.Title_Text_Listeners;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 3/31/2019.
 */

public class ListOfPeople extends Fragment {
    private static final String TAG = "ListOfPeople";
    private String [] mlistnames = {"Manjil","Razu","Diwas","Hari","Shyam","Geeta","Sita","Lakshman"};
    private int [] user_id = {1,2,3,4,5,6,7,8};
    private ListView mlistview_people;
    private List<PeopleListModel> mlist = new ArrayList<>();
    private EditText meditextsearch;
    private ImageView mimageviewsearch;
    //initilization adapter
    private ListOfPeople_Adpater mpoepolelisadapter;
    //initilize interface
    Title_Text_Listeners mlisteners;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mlisteners = (Title_Text_Listeners) context;
        mlisteners.settitle("Online List");
    }

    //private String [] images = ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mview = inflater.inflate(R.layout.fragment_listofpeople,container,false);
        //init views


        initview(mview);
        //initiliziting adapters
        loadadapterdatas();
        //set on click listeners
        onclickslisteners();
        return mview;
    }
    public void initview(View miews){
        //editext search
        meditextsearch =(EditText) miews.findViewById(R.id.meditext_peoplelist_search);
        //imageview search
        mimageviewsearch =(ImageView) miews.findViewById(R.id.msearchview_listpeoplesearch);

        //initilizing listview
        mlistview_people =(ListView) miews.findViewById(R.id.mlistview_activeusers);
    }
    public void loadadapterdatas(){
        for(int i = 0;i<mlistnames.length;i++){
            PeopleListModel mlistmodels = new PeopleListModel();
            mlistmodels.setName(mlistnames[i]);
            mlistmodels.setUser_id(user_id[i]);
            mlist.add(mlistmodels);
        }
        mpoepolelisadapter = new ListOfPeople_Adpater(mlist,getContext());

        mlistview_people.setAdapter(mpoepolelisadapter);
        //listview setonclick listeners
        mlistview_people.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String names = parent.getItemAtPosition(position).toString();
                String names = mlist.get(position).getName();
                int userids = mlist.get(position).getUser_id();
                Log.d(TAG, "onItemClick: names of respected list items"+names);
                startActivity(new Intent(view.getContext(), SingleChat.class).putExtra("name",names).putExtra("userid",userids));
            }
        });

    }
    public void onclickslisteners(){
        meditextsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged: texts"+s);
                mimageviewsearch.setImageDrawable(getResources().getDrawable(R.drawable.ic_cancel_black_24dp));
                mpoepolelisadapter.getFilter().filter(s.toString());
                mimageviewsearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        meditextsearch.setText("");
                        mimageviewsearch.setImageDrawable(getResources().getDrawable(R.drawable.ic_search));
                        mpoepolelisadapter = new ListOfPeople_Adpater(mlist,getContext());
                        mlistview_people.setAdapter(mpoepolelisadapter);
                        mpoepolelisadapter.notifyDataSetChanged();
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
