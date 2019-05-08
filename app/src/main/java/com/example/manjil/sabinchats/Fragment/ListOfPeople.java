package com.example.manjil.sabinchats.Fragment;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.manjil.sabinchats.Activity.SingleChat;
import com.example.manjil.sabinchats.Adapters.ListOfPeople_Adpater;
import com.example.manjil.sabinchats.Model.user_list.Resultss;
import com.example.manjil.sabinchats.Model.user_list.Userlists;
import com.example.manjil.sabinchats.R;
import com.example.manjil.sabinchats.Interfaces.Title_Text_Listeners;
import com.example.manjil.sabinchats.RestApi.ApiClient;
import com.example.manjil.sabinchats.RestApi.RetroInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 3/31/2019.
 */

public class ListOfPeople extends Fragment {
    private static final String TAG = "ListOfPeople";
    private ListView mlistview_people;
    private EditText meditextsearch;
    private ImageView mimageviewsearch;
    //initilization adapter
    private ListOfPeople_Adpater mpoepolelisadapter;
    //initilize interface
    Title_Text_Listeners mlisteners;
    //retrofit interface
    RetroInterface minterface;
    //getting userlist model data
    List<Resultss> mgetlist = new ArrayList<>();

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
        //getting users list
        get_userslist();
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
//        for(int i = 0;i<mlistnames.length;i++){
//            PeopleListModel mlistmodels = new PeopleListModel();
//            mlistmodels.setName(mlistnames[i]);
//            mlistmodels.setUser_id(user_id[i]);
//            mlist.add(mlistmodels);
//        }
//        mpoepolelisadapter = new ListOfPeople_Adpater(mlist,getContext());
//
//        mlistview_people.setAdapter(mpoepolelisadapter);


    }
    public void onclickslisteners(){
        //edittext search view
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
                        mpoepolelisadapter = new ListOfPeople_Adpater(mgetlist,getContext());
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
        //listview setonclick listeners
        mlistview_people.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String names = parent.getItemAtPosition(position).toString();
                String names = mgetlist.get(position).getUsername();
                int userids = mgetlist.get(position).getId();
                String picurl = mgetlist.get(position).getPicture();
                Log.d(TAG, "onItemClick: names of respected list items"+names);
                startActivity(new Intent(view.getContext(), SingleChat.class).putExtra("name",names).putExtra("userid",userids).putExtra("picture",picurl));
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
                                mresult.setId(response.body().getResults().get(i).getId());
                                mresult.setName(response.body().getResults().get(i).getName());
                                mresult.setPicture(response.body().getResults().get(i).getPicture());
                                Log.d(TAG, "onResponse: pictures" + response.body().getResults().get(i).getPicture());
                                mresult.setStatus(response.body().getResults().get(i).getStatus());
                                mresult.setUsername(response.body().getResults().get(i).getUsername());
                                mgetlist.add(mresult);
                            }

                        }
                        mpoepolelisadapter = new ListOfPeople_Adpater(mgetlist,getContext());
                        mlistview_people.setAdapter(mpoepolelisadapter);

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
