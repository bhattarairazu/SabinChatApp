package com.example.manjil.sabinchat.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.manjil.sabinchat.Adapters.CreateGroupChat_Adapter;
import com.example.manjil.sabinchat.Adapters.Custom_chathome_Adpater;
import com.example.manjil.sabinchat.Adapters.ListOfPeople_Adpater;
import com.example.manjil.sabinchat.Constants.SharedPreference;
import com.example.manjil.sabinchat.Model.Results;
import com.example.manjil.sabinchat.Model.user_list.Resultss;
import com.example.manjil.sabinchat.Model.user_list.Userlists;
import com.example.manjil.sabinchat.R;
import com.example.manjil.sabinchat.RestApi.ApiClient;
import com.example.manjil.sabinchat.RestApi.RetroInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_GroupChat extends AppCompatActivity {
    private static final String TAG = "Activity_GroupChat";
    RetroInterface minterface;
    List<Resultss> mgetlist = new ArrayList<>();
    CreateGroupChat_Adapter madaptergroup;
    ListView mlistview;
    EditText meditext_searchs;
    ImageView mimageview_search;
    SharedPreference msharedsp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupchat);
        msharedsp = new SharedPreference(this);
        initviews();
        get_userslist();
        onclicklisteners();
    }
    public void initviews(){
        mlistview = (ListView) findViewById(R.id.mlistview_grouplist);
        meditext_searchs = (EditText) findViewById(R.id.meditext_grouppeople_search);
        mimageview_search =(ImageView) findViewById(R.id.mimageview_searchicon);
    }
    public void onclicklisteners(){
        //ediitext filter onlcick listenrs
        meditext_searchs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged: texts"+s);
                mimageview_search.setImageDrawable(getResources().getDrawable(R.drawable.ic_cancel_black_24dp));
                madaptergroup.getFilter().filter(s.toString());
                mimageview_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        meditext_searchs.setText("");
                        mimageview_search.setImageDrawable(getResources().getDrawable(R.drawable.ic_search));
                        madaptergroup = new CreateGroupChat_Adapter(mgetlist,getApplicationContext());
                        mlistview.setAdapter(madaptergroup);
                        madaptergroup.notifyDataSetChanged();
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
    //getting all users data
    public void get_userslist(){
        minterface = ApiClient.getAPICLIENT().create(RetroInterface.class);
        Call<Userlists> mgetlist_user = minterface.mgetuser_list();
        mgetlist_user.enqueue(new Callback<Userlists>() {
            @Override
            public void onResponse(Call<Userlists> call, Response<Userlists> response) {
                if(response.isSuccessful()){
                    if(response.code()==200){
                        for(int i =0;i<response.body().getResults().size();i++){
                            Resultss mresult = new Resultss();
                            if(response.body().getResults().get(i).getStatus()==1) {
                                if(response.body().getResults().get(i).getId()!=msharedsp.getuserids()) {
                                    mresult.setId(response.body().getResults().get(i).getId());
                                    mresult.setName(response.body().getResults().get(i).getName());
                                    mresult.setPicture(response.body().getResults().get(i).getPicture());
                                    Log.d(TAG, "onResponse: pictures" + response.body().getResults().get(i).getPicture());
                                    mresult.setStatus(response.body().getResults().get(i).getStatus());
                                    mresult.setUsername(response.body().getResults().get(i).getUsername());
                                    mgetlist.add(mresult);
                                }
                            }

                        }
                        madaptergroup = new CreateGroupChat_Adapter(mgetlist,getApplicationContext());
                        mlistview.setAdapter(madaptergroup);

                    }
                }
            }

            @Override
            public void onFailure(Call<Userlists> call, Throwable t) {
                Log.d(TAG, "onFailure: failed"+t.toString());

            }
        });
    }

}
