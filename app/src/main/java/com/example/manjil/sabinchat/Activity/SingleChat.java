package com.example.manjil.sabinchat.Activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.manjil.sabinchat.Adapters.Custom_SingleMessage_Adapter;
import com.example.manjil.sabinchat.Constants.SharedPreference;
import com.example.manjil.sabinchat.Model.Model_Message_Singleitems;
import com.example.manjil.sabinchat.Model.Model_messagelist;
import com.example.manjil.sabinchat.Model.Model_sendingmessage;
import com.example.manjil.sabinchat.Model.UserSignup;
import com.example.manjil.sabinchat.R;
import com.example.manjil.sabinchat.RestApi.ApiClient;
import com.example.manjil.sabinchat.RestApi.RetroInterface;

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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_home);
        //initn views
        initviews();
        //setting onclick listeners on image view
        setonclicklisteners();
        //getting intent values
        //catching null values
        try {
            names = getIntent().getExtras().getString("name");
            userids = getIntent().getExtras().getInt("userid");
            mtextview_name.setText(names);

        }catch (NullPointerException ex){
            Log.d(TAG, "onCreate: catch null exception"+ex.toString());
        }
        getmessage_fromserver();
        }

        public void initviews(){
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
                minterface =ApiClient.getAPICLIENT().create(RetroInterface.class);
            Call<Model_messagelist> mgetlist = minterface.mgetmessagelist(SharedPreference.user_ids,userids);
            mgetlist.enqueue(new Callback<Model_messagelist>() {
                @Override
                public void onResponse(Call<Model_messagelist> call, Response<Model_messagelist> response) {
                    if(response.isSuccessful()){
                        for(int i = response.body().getResults().size()-1;i>=0;i--){
                                String message = response.body().getResults().get(i).getMessage();
                                int from_id = response.body().getResults().get(i).getFrom_id();
                                int to_id = response.body().getResults().get(i).getTo_id();
                                String time = response.body().getResults().get(i).getTime();
                                String pictures = response.body().getResults().get(i).getPicture();
                            Log.d(TAG, "onResponse: getting pictures message from server"+pictures);
                                Model_sendingmessage mmessage_single = new Model_sendingmessage(message,from_id,to_id,time,selectedImage,pictures);
                                mlistmodel.add(mmessage_single);
                        }
                        mlisview_messagse.setAdapter(new Custom_SingleMessage_Adapter(getApplicationContext(),mlistmodel,SharedPreference.user_ids));
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
        //mimage button set on click listnersn

            mbtn_sendimagebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pickFromGallery();
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
                        sendmessage_toserver_withimages(messages, SharedPreference.user_ids, userids);
                        sendimage = false;

                    }else{
                        messages = meditext_typemessage.getText().toString();
//                        Model_sendingmessage msend = new Model_sendingmessage(messages, SharedPreference.user_ids, userids, "3 hrs");
//                        mlistmodel.add(msend);
//                        madapter = new Custom_SingleMessage_Adapter(getApplicationContext(), mlistmodel, SharedPreference.user_ids);
//                        mlisview_messagse.setAdapter(madapter);
//                        madapter.notifyDataSetChanged();
                        sendmessage_toserver_withoutimages(messages, SharedPreference.user_ids, userids);
                    }
                    meditext_typemessage.setText("");

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


}