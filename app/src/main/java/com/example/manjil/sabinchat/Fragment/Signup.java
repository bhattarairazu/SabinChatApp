package com.example.manjil.sabinchat.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manjil.sabinchat.Model.UserSignup;
import com.example.manjil.sabinchat.R;
import com.example.manjil.sabinchat.RestApi.ApiClient;
import com.example.manjil.sabinchat.RestApi.RetroInterface;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 3/31/2019.
 */

public class Signup extends Fragment {
    private static final String TAG = "Signup";
    //retrofit interface initilizatin
    RetroInterface minterface;
    //initlization views
    private EditText meditext_email,medittext_name,meditext_username,meditext_password,medittext_confirmpassword;
    //textview
    private TextView mtextview_signup;
    //initilizing string
    private String username,password,email,name,confirmpassword;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mview = inflater.inflate(R.layout.fragment_register,container,false);
        //calling inint view function
        initviews(mview);

        //setting click listenrs
        setonclicklisteners();
        return mview;
    }
    public void setonclicklisteners(){
        mtextview_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    post_registerinformation(name,username,password);
                }
            }
        });


    }
    public void initviews(View mview){
        //for signup button
        mtextview_signup =(TextView) mview.findViewById(R.id.getstarted_signup);
        //for name
        medittext_name =(EditText) mview.findViewById(R.id.name);
        //for username
        meditext_username =(EditText) mview.findViewById(R.id.username);
        //for password
        meditext_password=(EditText) mview.findViewById(R.id.password);
        //for confirm password
        medittext_confirmpassword=(EditText) mview.findViewById(R.id.confirm_password);
        //for email address
        meditext_email =(EditText) mview.findViewById(R.id.email);
    }
    //initilization api request
    public void post_registerinformation(String namess,String usenames,String passwords) {
        //UserSignup msignup = new UserSignup(namess, usenames, passwords);

        minterface = ApiClient.getAPICLIENT().create(RetroInterface.class);
       Call<UserSignup> msignups = minterface.mregister_information(namess,usenames,passwords);
       msignups.enqueue(new Callback<UserSignup>() {
           @Override
           public void onResponse(Call<UserSignup> call, Response<UserSignup> response) {
//               Log.d(TAG, "onResponse: faileddddd"+response);
              // Log.d(TAG, "onResponse: jsonobject is "+response.body());
                if(response.isSuccessful()) {
                 if(response.code()==200 && response.message().equals("OK")) {
                    // Toast.makeText(getContext(), "Register Successfull", Toast.LENGTH_SHORT).show();
                     String message = response.body().getResults().getMsg();
                     Log.d(TAG, "onResponse: main response"+message);
                     Toast.makeText(getContext(), ""+message, Toast.LENGTH_SHORT).show();
                     getFragmentManager().popBackStack();
                 }
               }
           }

           @Override
           public void onFailure(Call<UserSignup> call, Throwable t) {
               Log.d(TAG, "onFailure: error"+t.toString());
           }
       });
    }
    private boolean validate() {
        boolean validate = false;
        name = medittext_name.getText().toString();
        email =meditext_email.getText().toString();
        confirmpassword =medittext_confirmpassword.getText().toString();
        username = meditext_username.getText().toString();
        password = meditext_password.getText().toString();
        //checking either the input words are empty or not
        if (TextUtils.isEmpty(username)) {
            meditext_username.setError("Required");
        }
        else if (TextUtils.isEmpty(password)) {
                meditext_password.setError("Required");
            }
        else if(TextUtils.isEmpty(name)){
            medittext_name.setError("Required");
        }else if(TextUtils.isEmpty(email)){
        meditext_email.setError("Required");
        }else if(TextUtils.isEmpty(confirmpassword)){
            medittext_confirmpassword.setError("Required");
        }else{

            validate = true;
        }
        return validate;
    }
}
