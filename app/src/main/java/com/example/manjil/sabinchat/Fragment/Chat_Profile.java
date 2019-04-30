package com.example.manjil.sabinchat.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.manjil.sabinchat.Activity.Activity_GroupChat;
import com.example.manjil.sabinchat.Activity.SingleChat;
import com.example.manjil.sabinchat.Adapters.Custom_chathome_Adpater;
import com.example.manjil.sabinchat.Constants.SharedPreference;
import com.example.manjil.sabinchat.Model.Model_HomeChat;
import com.example.manjil.sabinchat.Model.UserSignup;
import com.example.manjil.sabinchat.R;
import com.example.manjil.sabinchat.Interfaces.Title_Text_Listeners;
import com.example.manjil.sabinchat.RestApi.ApiClient;
import com.example.manjil.sabinchat.RestApi.RetroInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private int [] user_id = {1,2,3,4,5,6,7,8};

    //setarch edit text
    private EditText msearacheditext;
    private ListView mlistviews;
    private List<Model_HomeChat> mhomechatlisst = new ArrayList<>();
    private Custom_chathome_Adpater madpters;
    private ImageView searchimageview;
    //initilizatin interface
    private Title_Text_Listeners mlisteners;

    private FloatingActionButton madd_group;
    //retrofit interface for retrofit request
    RetroInterface minterface;
    //shared preferences for getting user infos
    SharedPreference msharedpreferences;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mlisteners =(Title_Text_Listeners) context;
        mlisteners.settitle("Chat");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mview = inflater.inflate(R.layout.chat_profile,container,false);
        //initilization shared preferences
        msharedpreferences = new SharedPreference(getContext());
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
        //floating action btn
        madd_group =(FloatingActionButton) mviews.findViewById(R.id.mfloating_action);


    }
    public void laodadapterdata(){
        for(int j = 0;j<mlistnames.length;j++){
            Model_HomeChat mhome = new Model_HomeChat();
            mhome.setDate(dates[j]);
            mhome.setName(mlistnames[j]);
            mhome.setLast_message(lastunseen_messages[j]);
            mhome.setOnline_status(onlinesatus[j]);
            mhome.setNo_of_unseenmessages(totlamessages[j]);
            mhome.setUserids(user_id[j]);
            mhomechatlisst.add(mhome);

        }
        madpters = new Custom_chathome_Adpater(mhomechatlisst,getContext());
        mlistviews.setAdapter(madpters);
        //listview setonclick listeners
        mlistviews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String names = parent.getItemAtPosition(position).toString();
                String names = mhomechatlisst.get(position).getName();
                int userids = mhomechatlisst.get(position).getUserids();
                Log.d(TAG, "onItemClick: names of respected list items"+names);
                startActivity(new Intent(view.getContext(), SingleChat.class).putExtra("name",names).putExtra("userid",userids));
            }
        });

        //delete on long clicklisteners on listview
        mlistviews.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int positions = position;
                long ids = id;
                onconfirm_dialogue(position,ids);
                return true;
            }
        });

    }
    public void onclicklisteners(){
        //floating point button on click listners
        madd_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertdialogue_creategroup();
            }
        });
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
    //confirmation dialog for deleting or not
    public void onconfirm_dialogue(final int pos, long id){
        new AlertDialog.Builder(getContext())
                .setTitle("Delete")
                .setMessage("Are You Sure You Want to Delete This Conversation ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mhomechatlisst.remove(pos);
                        madpters.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("No",null)
                .show();
    }

    //alertdialogue create group
    public void alertdialogue_creategroup(){
        final AlertDialog malert = new AlertDialog.Builder(getContext()).create();
        View mview = getLayoutInflater().inflate(R.layout.alertdialog_creategroup,null);
        final EditText mediitext_groupname = (EditText) mview.findViewById(R.id.medittext_groupname);
        Button mbtn_create = (Button) mview.findViewById(R.id.mbutton_creategroup);
        malert.setView(mview);
        mbtn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String names = mediitext_groupname.getText().toString();
                create_group_withname(names);
                malert.dismiss();

            }
        });
        malert.show();
    }
    public void create_group_withname(final String groupname){
        minterface = ApiClient.getAPICLIENT().create(RetroInterface.class);
        Call<UserSignup> mcreategroup = minterface.getgroup_info(msharedpreferences.getuserids());

        mcreategroup.enqueue(new Callback<UserSignup>() {
            @Override
            public void onResponse(Call<UserSignup> call, Response<UserSignup> response) {
                if(response.isSuccessful()){
                    if(response.body().getResults().getStatus()){
                        Toast.makeText(getContext(), "Group Created", Toast.LENGTH_SHORT).show();
                        Model_HomeChat mhomechat = new Model_HomeChat();
                        mhomechat.setName(groupname);
                        mhomechatlisst.add(mhomechat);
                        madpters = new Custom_chathome_Adpater(mhomechatlisst,getContext());
                        mlistviews.setAdapter(madpters);

                        int group_ids = response.body().getResults().getGroupId();
                        msharedpreferences.set_groupid(group_ids);
                        Log.d(TAG, "onResponse: group ids"+group_ids);
                        //instantly updating group name also
                        update_group_name(group_ids,groupname);

                    }
                }
            }

            @Override
            public void onFailure(Call<UserSignup> call, Throwable t) {
                Log.d(TAG, "onFailure: group create failed"+t.toString());
                Toast.makeText(getContext(), "Group Create Failed!Please Try Again!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //updating group name
    public void update_group_name(int gid, final String name_group){
        minterface = ApiClient.getAPICLIENT().create(RetroInterface.class);
        Call<UserSignup> mupdatename = minterface.mupdate_groupname(gid,name_group);
        mupdatename.enqueue(new Callback<UserSignup>() {
            @Override
            public void onResponse(Call<UserSignup> call, Response<UserSignup> response) {
                if(response.isSuccessful()){
                    if(response.body().getResults().getStatus()){
                        getContext().startActivity(new Intent(getActivity(),Activity_GroupChat.class));

                    }
                }
            }

            @Override
            public void onFailure(Call<UserSignup> call, Throwable t) {
                Log.d(TAG, "onFailure:update group name "+t.toString());
            }
        });

    }

}
