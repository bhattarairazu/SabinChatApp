package com.example.manjil.sabinchats.Fragment;

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
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manjil.sabinchats.Activity.LoginSignupActivity;
import com.example.manjil.sabinchats.Constants.SharedPreference;
import com.example.manjil.sabinchats.Model.UserSignup;
import com.example.manjil.sabinchats.Model.user_list.Userlists;
import com.example.manjil.sabinchats.R;
import com.example.manjil.sabinchats.Interfaces.Title_Text_Listeners;
import com.example.manjil.sabinchats.RestApi.ApiClient;
import com.example.manjil.sabinchats.RestApi.RetroInterface;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 3/31/2019.
 */

public class Settingfragment extends Fragment {
    private static final String TAG = "Settingfragment";
    //initilization interface
    private static final int GALLERY_REQUEST_CODE = 100;
    Uri selectedImage;
    ImageView mimageview,profilepictures;
    AlertDialog malertidalogeus;
    private Title_Text_Listeners mlisteners;
    private TextView changepasswords,logouts,updatproifle,profilename;
    private RetroInterface minterface;
    boolean selectimage = false;
    SharedPreference mshared;
    EditText meditext_newpassword;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mlisteners =(Title_Text_Listeners) context;
        mlisteners.settitle("Setting");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mview = inflater.inflate(R.layout.fragment_setting,container,false);
        mshared = new SharedPreference(getContext());
        //initviews

        initviews(mview);
        //onclicklistners
        onclicklisteners();
        get_userslist();
        return mview;
    }
    public void initviews(View mviews){
        //profile pictures
        profilepictures = (ImageView) mviews.findViewById(R.id.profile_pictures);
       // Picasso.get().load(ApiClient.BASE_URL+mshared.getpitures()).into(profilepictures);
        //updating profile
        updatproifle =(TextView) mviews.findViewById(R.id.mtextview_updateprofile);
       // changepasswords =(TextView) mviews.findViewById(R.id.mtextview_changepassword);
        logouts =(TextView) mviews.findViewById(R.id.logout);

        profilename =(TextView) mviews.findViewById(R.id.mtextview_proilename);
        profilename.setText(mshared.getusername());


    }
    public void onclicklisteners(){
       // logout on click listeners
        logouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        updatproifle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogueinterface_changepassword();
            }
        });
    }
    //change password dialogue
    public void Dialogueinterface_changepassword(){
        malertidalogeus = new AlertDialog.Builder(getContext()).create();
        //malertidalogeus.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View mview =getLayoutInflater().inflate(R.layout.update_profile,null);
        //final EditText meditext_current = (EditText) mview.findViewById(R.id.current_password);
        final EditText meditextname = (EditText) mview.findViewById(R.id.names);
        meditextname.setText(mshared.getusername());
        meditext_newpassword = (EditText) mview.findViewById(R.id.new_password);

        final EditText meditext_confirm = (EditText) mview.findViewById(R.id.new_passwordconfirm);

        TextView meditext_setpassword = (TextView) mview.findViewById(R.id.setpassword);
       mimageview = (ImageView) mview.findViewById(R.id.photo_profile);
        ImageView mimagvew_add = (ImageView) mview.findViewById(R.id.story_plusss);

        malertidalogeus.setView(mview);
        mimagvew_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pickFromGallery();
            }
        });
        meditext_setpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String names = meditextname.getText().toString();
                String newpassword = meditext_newpassword.getText().toString();
                String conformpasswords = meditext_confirm.getText().toString();
                if(selectimage && !TextUtils.isEmpty(names)){

                    updatename_toserver(names);
                    updatePicture_toserver();

                }
                if(selectimage && TextUtils.isEmpty(names)){
                    updatePicture_toserver();
                }
                if(!selectimage && !TextUtils.isEmpty(names)){
                    updatename_toserver(names);
                }
                if(!selectimage && TextUtils.isEmpty(names)){
                    meditextname.setError("Name Required");
                    return;
                }
                if(TextUtils.isEmpty(newpassword)){
                    meditext_newpassword.setError("Required");
                    return;
                }
                if(TextUtils.isEmpty(conformpasswords)){
                    meditext_confirm.setError("Required");
                    return;
                }
                if(newpassword.matches(conformpasswords)){
                    update_oldpassword(newpassword);
                }else{
                    meditext_confirm.setError("Password Doesn't Match with password you type! Please Type the Corect Password");
                    return;
                }



            }
        });

        malertidalogeus.show();

    }
    //logoout dialgoue
    public void logout(){
        new AlertDialog.Builder(getContext())
                .setTitle("Logout")
                .setMessage("Are You Sure You Want To Logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mshared.logoutUser();
                        logotu();
                        getActivity().finish();
                        startActivity(new Intent(getView().getContext(),LoginSignupActivity.class));

                    }
                })
                .setNegativeButton("No",null)
                .show();
    }
    //logout status update
public void logotu(){
        minterface = ApiClient.getAPICLIENT().create(RetroInterface.class);
    Call<UserSignup> mcall = minterface.mupdate_user(mshared.getuserids(),0);
    mcall.enqueue(new Callback<UserSignup>() {
        @Override
        public void onResponse(Call<UserSignup> call, Response<UserSignup> response) {
            if(response.isSuccessful()){
                if(response.body().getResults().getStatus()){
                    Log.d(TAG, "onResponse: user status updated successfully");

                }
            }
        }

        @Override
        public void onFailure(Call<UserSignup> call, Throwable t) {
            Log.d(TAG, "onFailure: user status update failed"+t.toString());

        }
    });
}
    public void update_oldpassword(String password_news){
        minterface = ApiClient.getAPICLIENT().create(RetroInterface.class);
        Call<UserSignup> mupdate = minterface.update_password(mshared.getusername(),password_news);
        mupdate.enqueue(new Callback<UserSignup>() {
            @Override
            public void onResponse(Call<UserSignup> call, Response<UserSignup> response) {
                if(response.isSuccessful()){
                    if(response.body().getResults().getStatus()){
                        Toast.makeText(getContext(), "Password Sucessfully Updated", Toast.LENGTH_SHORT).show();
                        meditext_newpassword.setText("");
                    }else{
                        Toast.makeText(getContext(), "Failed To Update Password! Please Try Again!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserSignup> call, Throwable t) {
                Log.d(TAG, "onFailure: failed"+t.toString());
            }
        });


    }


    public void updatePicture_toserver(){
        //This section is used for uploading multipart images with messages
        File mfile = new File(getPath(selectedImage));
        RequestBody filereqbody = RequestBody.create(MediaType.parse("image/"),mfile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image",mfile.getName(),filereqbody);

        minterface = ApiClient.getAPICLIENT().create(RetroInterface.class);
        Log.d(TAG, "updatePicture_toserver: userpictures"+mshared.getuserids());
        Call<UserSignup> mupdate_image = minterface.update_profileimage(mshared.getuserids(),body);
        mupdate_image.enqueue(new Callback<UserSignup>() {
            @Override
            public void onResponse(Call<UserSignup> call, Response<UserSignup> response) {
                if(response.isSuccessful()){
                    if (response.body().getResults().getStatus()){
                        Toast.makeText(getContext(), "Successfully Updates Images", Toast.LENGTH_SHORT).show();
                        get_userslist();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserSignup> call, Throwable t) {
                Log.d(TAG, "onFailure: failed"+t.toString());
            }
        });

    }
    public void updatename_toserver(String newname){
        minterface = ApiClient.getAPICLIENT().create(RetroInterface.class);
        Call<UserSignup> mupdate_name = minterface.update_username(SharedPreference.user_ids,newname);
        mupdate_name.enqueue(new Callback<UserSignup>() {
            @Override
            public void onResponse(Call<UserSignup> call, Response<UserSignup> response) {
                if(response.isSuccessful()){
                    if(response.body().getResults().getStatus()){
                        Toast.makeText(getContext(), "User Updated Successfully!", Toast.LENGTH_SHORT).show();
                        malertidalogeus.dismiss();
                        get_userslist();
                    }else{
                        Toast.makeText(getContext(), "User Update Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserSignup> call, Throwable t) {
                Log.d(TAG, "onFailure: fialed"+t.toString());

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
                    mimageview.setImageURI(selectedImage);
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
    //getting all users data
    public void get_userslist(){
        minterface = ApiClient.getAPICLIENT().create(RetroInterface.class);
        Call<Userlists> mgetlist_user = minterface.mgetuser_list_single(mshared.getuserids());
        Log.d(TAG, "get_userslist: user id is  "+mshared.getuserids());
        mgetlist_user.enqueue(new Callback<Userlists>() {
            @Override
            public void onResponse(Call<Userlists> call, Response<Userlists> response) {
                if(response.isSuccessful()){
                    for(int i = 0 ;i<response.body().getResults().size();i++){
                        Log.d(TAG, "onResponse: "+response.body().getResults().get(i).getUsername());
                        mshared.setusername(response.body().getResults().get(0).getUsername(),response.body().getResults().get(0).getPicture());
                        Picasso.get().load(ApiClient.BASE_URL+mshared.getpitures()).into(profilepictures);

                    }


//                   String pic = response.body().getResults().get(0).getPicture();
//                   String name  = response.body().getResults().get(0).getUsername();
//                    Log.d(TAG, "onResponse: pictures i s"+ pic);
//                    Log.d(TAG, "onResponse: usernames is"+name);
                    //if(response.code()==200){
                    // for(int i =0;i<response.body().getResults().size();i++){
//                            Resultss mresult = new Resultss();
//                           // if(response.body().getResults().get(i).getId()==userids) {
//                                msharedpreferene.setusername(response.body().getResults().get(i).getUsername(),response.body().getResults().get(i).getPicture());
//                                Log.d(TAG, "onResponse: usernames set"+response.body().getResults().get(i).getPicture()+"userids"+userids);
//                                Picasso.get().load(ApiClient.BASE_URL+response.body().getResults().get(i).getPicture()).into(tolbarimageview);
//                           // }

                    //   }


                    //}
                }
            }

            @Override
            public void onFailure(Call<Userlists> call, Throwable t) {
                Log.d(TAG, "onFailure: failed"+t.toString());

            }
        });
    }
}
