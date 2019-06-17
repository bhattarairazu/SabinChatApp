package com.example.manjil.sabinchats.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manjil.sabinchats.Adapters.Custom_SingleMessage_Adapter;
import com.example.manjil.sabinchats.Constants.SharedPreference;
import com.example.manjil.sabinchats.Model.Model_messagelist;
import com.example.manjil.sabinchats.Model.Model_sendingmessage;
import com.example.manjil.sabinchats.Model.UserSignup;
import com.example.manjil.sabinchats.R;
import com.example.manjil.sabinchats.RestApi.ApiClient;
import com.example.manjil.sabinchats.RestApi.RetroInterface;
import com.example.manjil.sabinchats.Room.AppDatabase;
import com.example.manjil.sabinchats.Room.Latestmessage;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleChat extends AppCompatActivity{
    private static final String TAG = "SingleChat";
    private String names;
    private int userids;
    private ImageView mbtnback_imagevviews;
    private TextView mtextview_name;
    private EditText meditext_typemessage;
    private Button mbtn_sends;
    List<Latestmessage> mgetmessagelist = new ArrayList<>();
    //initilization datas
    private ListView mlisview_messagse;
    private Custom_SingleMessage_Adapter madapter;
    private String messages;
    private List<Model_sendingmessage> mlistmodel = new ArrayList<>();
    private RetroInterface minterface;
    private ImageView mbtn_sendimagebtn;
    private static final int GALLERY_REQUEST_CODE = 100;
    Uri selectedImage;
    private Bitmap mbitmap;
    boolean sendimage = false;
    SharedPreference msharedpreference;
    String names_singleu,pictureurl,messagess=null;
    SwipeRefreshLayout swipe;
    ImageView profilechat;
    int fromid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_home);
        msharedpreference = new SharedPreference(this);
        //initn views
        initviews();
        //setting onclick listeners on image view
        setonclicklisteners();
        //getting intent values
        //catching null values
        try {
            names = getIntent().getExtras().getString("name");
            userids = getIntent().getExtras().getInt("userid");
            pictureurl = getIntent().getExtras().getString("picture");
            if(pictureurl!=null){
                Picasso.get().load(ApiClient.BASE_URL+pictureurl).into(profilechat);
            }else{
                Picasso.get().load(R.drawable.p_file).into(profilechat);
            }
            mtextview_name.setText(names);

        }catch (NullPointerException ex){
            Log.d(TAG, "onCreate: catch null exception"+ex.toString());
        }
        getting_singleusername(userids);

        getmessage_fromserver();
        }

        public void initviews(){
        //mprochat
            profilechat =(ImageView) findViewById(R.id.mprofile_chat);
        //swipe refresh layout
            swipe = (SwipeRefreshLayout) findViewById(R.id.mswipe_refresh);
        //image button
             mbtn_sendimagebtn =(ImageView) findViewById(R.id.bt_attachment);
        //listview
            mlisview_messagse =(ListView) findViewById(R.id.mlsitview_listmessages_single);
        //typing message edittext
            meditext_typemessage =(EditText) findViewById(R.id.send_message_edittext);
            //button sending
            mbtn_sends =(Button) findViewById(R.id.bt_send);

        mbtnback_imagevviews =(ImageView) findViewById(R.id.mimageview_btnback);
        //toolbartextview name
            mtextview_name =(TextView) findViewById(R.id.muser_name);
            try{


            }catch (NullPointerException ex){
                Log.d(TAG, "initviews: name null pinter"+ex.toString());
            }

        }
        public void getmessage_fromserver(){

            final String[] message_latest = {null};
                minterface =ApiClient.getAPICLIENT().create(RetroInterface.class);
            Call<Model_messagelist> mgetlist = minterface.mgetmessagelist(msharedpreference.getuserids(),userids);
            Log.d(TAG, "getmessage_fromserver: userids_home"+msharedpreference.getuserids()+"useridsother"+userids);
            mgetlist.enqueue(new Callback<Model_messagelist>() {
                @Override
                public void onResponse(Call<Model_messagelist> call, Response<Model_messagelist> response) {
                    if(response.isSuccessful()){
                        for(int i = response.body().getResults().size()-1;i>=0;i--){
                                messagess = response.body().getResults().get(i).getMessage();
                                message_latest[0] = messagess;
                                int id = response.body().getResults().get(i).getId();
                                fromid = response.body().getResults().get(i).getFrom_id();
                                int to_id = response.body().getResults().get(i).getTo_id();
                                String time = response.body().getResults().get(i).getTime();
                                String pictures = response.body().getResults().get(i).getPicture();
                            Log.d(TAG, "onResponse: getting pictures message from server"+pictures);
                                Model_sendingmessage mmessage_single = new Model_sendingmessage(id,messagess,fromid,to_id,time,selectedImage,pictures);
                                mlistmodel.add(mmessage_single);

                        }
                        if(names_singleu==null && messagess!=null){
                            Log.d(TAG, "onResponse: inside conditions");
                            savelatestmessage(messages,userids);
                        }else{
                            updatelatest_message(userids,messages);
                        }
                        if(swipe.isRefreshing()){
                        swipe.setRefreshing(false);
                        }


//                        try{
//                        if(!names.matches(names_singleu)){
//                            savelatestmessage(message_latest[0],userids);
//                            Log.d(TAG, "onResponse: single names"+names_singleu);
//
//                        }
//                        }catch (NullPointerException ex){
//                            Log.d(TAG, "onResponse: exception"+ex.toString());
//                        }

                        madapter = new Custom_SingleMessage_Adapter(getApplicationContext(),mlistmodel,msharedpreference.getuserids());

                        mlisview_messagse.setAdapter(madapter);
                    }
                }

                @Override
                public void onFailure(Call<Model_messagelist> call, Throwable t) {
                    Log.d(TAG, "onFailure: failed"+t.toString());

                }
            });
        }
        //setting click listners
        public void setonclicklisteners(){
        //seron refresh listeners
            swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipe.setRefreshing(true);
                    mlistmodel.clear();
                    getmessage_fromserver();
                }
            });
        //mimage button set on click listnersn

            mbtn_sendimagebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pickFromGallery();
                   // requestStoragePermission();
                }
            });

            mbtnback_imagevviews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            //listview click listeners
            mbtn_sends.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //checking whether the user has selected image or not
                    //if user has selected image than image is send to the server else not
                    if(sendimage) {
                        Log.d(TAG, "onClick: image urls"+selectedImage);
                        messages = meditext_typemessage.getText().toString();
//                        Model_sendingmessage msend = new Model_sendingmessage();
//                        msend.setMessage(messages);
//                        msend.setFrom_id(SharedPreference.user_ids);
//                        msend.setTo_id(userids);
//                        msend.setMuri(selectedImage);
//                        msend.setPicture(compresstostring());
//                        mlistmodel.add(msend);
//                        madapter = new Custom_SingleMessage_Adapter(getApplicationContext(), mlistmodel, SharedPreference.user_ids);
//                        mlisview_messagse.setAdapter(madapter);
//                        madapter.notifyDataSetChanged();

                        sendmessage_toserver_withimages(messages, msharedpreference.getuserids(), userids);
                        sendimage = false;

                    }else{
                        messages = meditext_typemessage.getText().toString();
//                        Model_sendingmessage msend = new Model_sendingmessage(messages, SharedPreference.user_ids, userids, "3 hrs");
//                        mlistmodel.add(msend);
//                        madapter = new Custom_SingleMessage_Adapter(getApplicationContext(), mlistmodel, SharedPreference.user_ids);
//                        mlisview_messagse.setAdapter(madapter);
//                        madapter.notifyDataSetChanged();

                        sendmessage_toserver_withoutimages(messages, msharedpreference.getuserids(), userids);
                    }
                    meditext_typemessage.setText("");

                }
            });

            //listview clicklisteners
            mlisview_messagse.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    int message_id = mlistmodel.get(position).getId();
                    onconfirm_dialogue(position,message_id);
                    return false;
                }
            });


        }
        public void sendmessage_toserver_withoutimages(String messages,int from_ids,int to_ids){
        minterface = ApiClient.getAPICLIENT().create(RetroInterface.class);
        Call<UserSignup> msendmessage = minterface.msend_singlemessage(messages,from_ids,to_ids);
        msendmessage.enqueue(new Callback<UserSignup>() {
            @Override
            public void onResponse(Call<UserSignup> call, Response<UserSignup> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: image response"+response.toString());
                        if(response.body().getResults().getStatus()){

                            mlistmodel.clear();
                            getmessage_fromserver();
                        }
                }
            }

            @Override
            public void onFailure(Call<UserSignup> call, Throwable t) {
                Log.d(TAG, "onFailure: error"+t.toString());

            }
        });
        }
        //sending message to server with images contained
    public void sendmessage_toserver_withimages(String messagess,int from_ids,int to_ids){

       //This section is used for uploading multipart images with messages
        File mfile = new File(getPath(selectedImage));
        RequestBody filereqbody = RequestBody.create(MediaType.parse("image/"),mfile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image",mfile.getName(),filereqbody);

        //creating requestbody object from string
        RequestBody message = createPartFromString(messagess);
        RequestBody to_id = createPartFromString(String.valueOf(to_ids));
        RequestBody from_id = createPartFromString(String.valueOf(from_ids));
       // String images = compresstostring();

        //hashmap for sending messages with images
        HashMap<String,RequestBody> map = new HashMap<>();
        map.put("message",message);
        map.put("to_id",to_id);
        map.put("from_id",from_id);


        minterface = ApiClient.getAPICLIENT().create(RetroInterface.class);
        Call<UserSignup> msendmessage = minterface.uploadimage(map,body);
        msendmessage.enqueue(new Callback<UserSignup>() {
            @Override
            public void onResponse(Call<UserSignup> call, Response<UserSignup> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: image response"+response.toString());
                    if(response.body().getResults().getStatus()){

                        mlistmodel.clear();

                        getmessage_fromserver();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserSignup> call, Throwable t) {
                Log.d(TAG, "onFailure: error"+t.toString());

            }
        });

    }

//    //creating file objects
//    public MultipartBody.Part prepareFilePart(String partName,Uri fileuri){
//        File file = FileUtils.getFile(this, fileuri);
//
//        // create RequestBody instance from file
//        RequestBody requestFile =
//                RequestBody.create(
//                        MediaType.parse(getContentResolver().getType(fileuri)),
//                        file
//                );
//
//        // MultipartBody.Part is used to send also the actual file name
//        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
//
//    }

    //creating multipart file from string for uploading message with images
    @NonNull
    private RequestBody createPartFromString(String descriptionString){
        return RequestBody.create(MultipartBody.FORM,descriptionString);
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
                    sendimage = true;
                    //data.getData returns the content URI for the selected Image
                   selectedImage = data.getData();
                    meditext_typemessage.setHint("Image Selected");
                    try {
                        mbitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //getPath(selectedImage);
                    Log.d(TAG, "onActivityResult: image paths "+compresstostring());
                    //imageView.setImageURI(selectedImage);
                    break;
            }
    }
    //getting filepat from uri of theimage
    private String getPath(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
    //not used for now
    //converting bitmapt image to encoded string
    private String compresstostring(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        mbitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte [] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);
    }
    public void deletemessage_fromserver(int id){
         //final boolean delete_status = false;
        minterface = ApiClient.getAPICLIENT().create(RetroInterface.class);
        Call<UserSignup> mdelete = minterface.delete_message(id);
        mdelete.enqueue(new Callback<UserSignup>() {
            @Override
            public void onResponse(Call<UserSignup> call, Response<UserSignup> response) {
                if(response.isSuccessful()){
                    if(response.body().getResults().getStatus()){
                        Toast.makeText(SingleChat.this, ""+response.body().getResults().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserSignup> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.toString());
                Toast.makeText(SingleChat.this, "Message Delete Failed", Toast.LENGTH_SHORT).show();

            }
        });

    }

    //confirmation dialog for deleting or not
    public void onconfirm_dialogue(final int pos, final int id){
        new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Are You Sure You Want to Delete This Conversation ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deletemessage_fromserver(id);
                        mlistmodel.remove(pos);
                        madapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("No",null)
                .show();
    }

    private void savelatestmessage(final String message,final int userid) {

        //room database inserting username and images
        class SaveLatestmessge extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a new task
                Latestmessage mlate = new Latestmessage(names,message,userid,0,msharedpreference.getuserids());
                AppDatabase.getInstance(getApplicationContext()).lmessagedao().inserLastmessage(mlate);

                return null;
            }

        }
        SaveLatestmessge mmessages = new SaveLatestmessge();
        mmessages.execute();

    }
    //updating latest message
    private void updatelatest_message(final int uids,final String lastmessage){
        //room database inserting username and images
        class UpdateLatestMessage extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                Log.d(TAG, "doInBackground: updateing"+lastmessage+"idss"+uids);
                //creating a new task
                Latestmessage mlate = new Latestmessage(names,lastmessage,uids,0,msharedpreference.getuserids());
                AppDatabase.getInstance(getApplicationContext()).lmessagedao().updateLastmessage(mlate);

                return null;
            }

        }
        UpdateLatestMessage mmessages = new UpdateLatestMessage();
        mmessages.execute();
    }
    //getting single username from room databse to check for latest message
    private void getting_singleusername(final int uids){
        class Getsingleuname extends AsyncTask<Void,Void,List<Latestmessage>>{

            @Override
            protected List<Latestmessage> doInBackground(Void... voids) {
              // names_singleu = AppDatabase.getInstance(getApplicationContext()).lmessagedao().getting_single_username();
                names_singleu  = AppDatabase.getInstance(getApplicationContext()).lmessagedao().getsingleusername(uids,msharedpreference.getuserids());
               // mgetmessagelist = AppDatabase.getInstance(getApplicationContext()).lmessagedao().getting_single_username(uids);

                Log.d(TAG, "doInBackground: inside asynctask"+names_singleu);
                return mgetmessagelist;
            }



        }
        Getsingleuname msinl = new Getsingleuname();
        msinl.execute();
    }
    private void requestStoragePermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                           // Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                           // pickFromGallery();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }


}