package com.example.manjil.sabinchat.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.manjil.sabinchat.Activity.Activity_GroupChat;
import com.example.manjil.sabinchat.Activity.SingleChat;
import com.example.manjil.sabinchat.Activity.Stories;
import com.example.manjil.sabinchat.Adapters.Custom_chathome_Adpater;
import com.example.manjil.sabinchat.Adapters.ListOfPeople_Adpater;
import com.example.manjil.sabinchat.Adapters.Recyclerview_storiesadapter;
import com.example.manjil.sabinchat.Adapters.Stories_Adapter;
import com.example.manjil.sabinchat.Constants.SharedPreference;
import com.example.manjil.sabinchat.Model.Model_HomeChat;
import com.example.manjil.sabinchat.Model.UserSignup;
import com.example.manjil.sabinchat.Model.stories.Model_stories;
import com.example.manjil.sabinchat.Model.stories.Results_stories;
import com.example.manjil.sabinchat.Model.user_list.Resultss;
import com.example.manjil.sabinchat.Model.user_list.Userlists;
import com.example.manjil.sabinchat.R;
import com.example.manjil.sabinchat.Interfaces.Title_Text_Listeners;
import com.example.manjil.sabinchat.RestApi.ApiClient;
import com.example.manjil.sabinchat.RestApi.RetroInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
    //for stories
    List<Results_stories> mstorieslist = new ArrayList<>();
    LinearLayout mstorieslayout;

    //stories adapter
    List<Resultss> mgetpeoplelist = new ArrayList<>();
    Recyclerview_storiesadapter mstoriesadapter;
    RecyclerView.LayoutManager mlayoutmanager;
    RecyclerView mrecyclerview;
    //Recyclerview
    RelativeLayout mrelativewlayout ;
    private static final int GALLERY_REQUEST_CODE = 100;
    Uri selectedImage;
    boolean selectimage = false;
    ImageView addstory;


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
        loadall_online_user();
        onclicklisteners();
       // getting_stories();
        return mview;
    }
    public void initnview(View mviews){
        //addstory image view
        addstory = (ImageView) mviews.findViewById(R.id.story_photo);
        //relativew
        mrelativewlayout = (RelativeLayout) mviews.findViewById(R.id.mrelativewlayout_addstory);
        //listview stoires
        mrecyclerview =(RecyclerView) mviews.findViewById(R.id.mrecyclerviewstores);
        //search icon imag view
        searchimageview =(ImageView) mviews.findViewById(R.id.mimageview_searchs);
       msearacheditext =(EditText) mviews.findViewById(R.id.medittext_searches);
        mlistviews =(ListView) mviews.findViewById(R.id.mlistview_homechat);
        //floating action btn
        madd_group =(FloatingActionButton) mviews.findViewById(R.id.mfloating_action);

        //for stories
        //mstorieslayout = (LinearLayout) mviews.findViewById(R.id.mlinearlayout_storiesuser);


    }
    public void laodadapterdata(){
        //recyclerview load datas
        mlayoutmanager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        mrecyclerview.setLayoutManager(mlayoutmanager);




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
        mrelativewlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromGallery();
            }
        });

        //stories linearlayout setonclicklisteners
//        mstorieslayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getContext(),Stories.class));
//            }
//        });
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
        int uids = 0;
        try{
            uids = msharedpreferences.getuserids();
            //uids = Integer.parseInt(uids);
        }catch (ClassCastException ex){
            Log.d(TAG, "create_group_withname: class cast exception"+ex.toString());
        }
        minterface = ApiClient.getAPICLIENT().create(RetroInterface.class);
        Call<UserSignup> mcreategroup = minterface.getgroup_info(uids);

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
//    public void getting_stories(){
//        int muids = 0;
//        try{
//            muids = msharedpreferences.getuserids();
//        }catch (ClassCastException ex){
//            Log.d(TAG, "getting_stories: exception clas cast"+ex.toString());
//        }
//        minterface = ApiClient.getAPICLIENT().create(RetroInterface.class);
//        Call<Model_stories> mstories = minterface.mgetstories(muids);
//        mstories.enqueue(new Callback<Model_stories>() {
//            @Override
//            public void onResponse(Call<Model_stories> call, Response<Model_stories> response) {
//                if(response.isSuccessful()){
//                    for(int i = 0;i<response.body().getResult().size();i++){
//                        Results_stories mstores= new Results_stories();
//                        mstores.setId(response.body().getResult().get(i).getId());
//                        mstores.setImage(response.body().getResult().get(i).getImage());
//                        msharedpreferences.set_stories_picture(response.body().getResult().get(i).getImage());
//                        mstores.setText(response.body().getResult().get(i).getText());
//                        mstores.setUser_id(response.body().getResult().get(i).getUser_id());
//                        mstores.setCreated_at(response.body().getResult().get(i).getCreated_at());
//                        mstorieslist.add(mstores);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Model_stories> call, Throwable t) {
//                Log.d(TAG, "onFailure: error"+t.toString());
//            }
//        });
//    }

    public void loadall_online_user(){
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
                                mresult.setPicture(response.body().getResults().get(i).getPicture());
                                Log.d(TAG, "onResponse: pictures" + response.body().getResults().get(i).getPicture());
                                mresult.setUsername(response.body().getResults().get(i).getUsername());
                               mgetpeoplelist.add(mresult);
                            }

                        }
                        mstoriesadapter = new Recyclerview_storiesadapter(mgetpeoplelist,getContext());
                        mrecyclerview.setAdapter(mstoriesadapter);
//                        mpoepolelisadapter = new ListOfPeople_Adpater(mgetlist,getContext());
//                        mlistview_people.setAdapter(mpoepolelisadapter);

                    }
                }
            }

            @Override
            public void onFailure(Call<Userlists> call, Throwable t) {
                Log.d(TAG, "onFailure: failed"+t.toString());

            }
        });
    }

    //pick image from gallery
    private void pickFromGallery(){
        //Create an Intent with action as ACTION_PICK
        Intent intent=new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Launching the Intent
        startActivityForResult(intent,GALLERY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case GALLERY_REQUEST_CODE:
                    //data.getData returns the content URI for the selected Image
                    selectedImage = data.getData();
                    selectimage = true;
                    addstory.setImageURI(selectedImage);
                    add_image_tostory(selectedImage);
//                    try {
//                        mbitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImage);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    //getPath(selectedImage);
//                    Log.d(TAG, "onActivityResult: image paths "+compresstostring());
                    //imageView.setImageURI(selectedImage);
                    break;
            }
    }
    //getting filepat from uri of theimage
    private String getPath(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    //adding photo to the story in server
    public void add_image_tostory(Uri image){
        int uid = 0;
        try{
            uid = msharedpreferences.getuserids();
            Log.d(TAG, "add_image_tostory: userids "+uid);
        }catch (ClassCastException ex){
            Log.d(TAG, "add_image_tostory: exception"+ex.toString());
        }
        //This section is used for uploading multipart images with messages
        File mfile = new File(getPath(image));
        RequestBody filereqbody = RequestBody.create(MediaType.parse("image/"),mfile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image",mfile.getName(),filereqbody);

        minterface = ApiClient.getAPICLIENT().create(RetroInterface.class);
        Call<UserSignup> mupdate_image = minterface.maddtostory(14,body);
        mupdate_image.enqueue(new Callback<UserSignup>() {
            @Override
            public void onResponse(Call<UserSignup> call, Response<UserSignup> response) {
                if(response.isSuccessful()){
                    if (response.body().getResults().getStatus()){
                        Toast.makeText(getContext(), ""+response.body().getResults().getMsg(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(), "Failed to add story!Please Try Aggain", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserSignup> call, Throwable t) {
                Log.d(TAG, "onFailure: failed"+t.toString());
            }
        });
    }


}
