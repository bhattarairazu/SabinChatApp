package com.example.manjil.sabinchat.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manjil.sabinchat.Activity.MainActivity;
import com.example.manjil.sabinchat.Constants.SharedPreference;
import com.example.manjil.sabinchat.Model.UserSignup;
import com.example.manjil.sabinchat.R;
import com.example.manjil.sabinchat.RestApi.ApiClient;
import com.example.manjil.sabinchat.RestApi.RetroInterface;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 3/31/2019.
 */

public class Login extends Fragment {
    private static final String TAG = "Login";
    private TextView mtextview_createaccounts,mtextview_forgotpasswords;
    private TextView mgetstarted;
    private EditText meditext_uname,meditext_password;
    private String unames,passwords;
    //initilization interface
    RetroInterface minterface;
    boolean status;
    private Handler mhandlers;
    private Runnable mrunnables;
    //setting progress dialog
    ProgressDialog mdialog;
    SharedPreference msharedpreferece;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mview = inflater.inflate(R.layout.fragment_login,container,false);
        msharedpreferece = new SharedPreference(getContext());
        if(msharedpreferece.isLoggedIn()){
            startActivity(new Intent(getContext(), MainActivity.class));
        }
        initviews(mview);
        setuponclicklisteners();

        return mview;
    }
    public void setuponclicklisteners(){
        //get started button seton click listners
        mgetstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logindialog();
                if(validate()){
                    chekclogin(unames,passwords);

                }

            }
        });
        //forgot passwords on click listenrs
        mtextview_forgotpasswords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new ForgotPassword());
            }
        });
        //create account on click listenesrs
        mtextview_createaccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new Signup());
            }
        });
    }
    public void initviews(View views){
        //uname
        meditext_uname=(EditText) views.findViewById(R.id.signin_username);
        meditext_password =(EditText) views.findViewById(R.id.signin_password);
        mtextview_createaccounts =(TextView) views.findViewById(R.id.create_account);
        mtextview_forgotpasswords =(TextView) views.findViewById(R.id.forgot_passwords);
        //getstarted button
        mgetstarted =(TextView) views.findViewById(R.id.login_getstarted);
    }
    public void chekclogin(final String una,String pass){
        minterface =ApiClient.getAPICLIENT().create(RetroInterface.class);
        Call<UserSignup> mcall = minterface.mlogins(una,pass);
        mcall.enqueue(new Callback<UserSignup>() {
            @Override
            public void onResponse(Call<UserSignup> call, Response<UserSignup> response) {
                if(response.isSuccessful()){
                    if(response.code()==200 && response.message().equals("OK")){
                        status = response.body().getResults().getStatus();
                        if(status){
                                msharedpreferece.createLoginSession(una,response.body().getResults().getUserId(),1,"");
                            //updating user online status
                            //setting online status to true when user is logged in
                            Call<UserSignup> mcall = minterface.mupdate_user(response.body().getResults().getUserId(),1);
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


                            mdialog.dismiss();
                            SharedPreference.user_ids = response.body().getResults().getUserId();
                            startActivity(new Intent(getContext(), MainActivity.class));
                        }else{
                            mdialog.dismiss();
                            Toast.makeText(getContext(), response.body().getResults().getMsg(), Toast.LENGTH_SHORT).show();
                            meditext_password.setText("");
                            meditext_uname.setText("");
                        }
                         }
                }            }

            @Override
            public void onFailure(Call<UserSignup> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.toString());
            }
        });


    }
    private boolean validate() {
        boolean validate = false;
        unames = meditext_uname.getText().toString();
        passwords =meditext_password.getText().toString();
        //checking either the input words are empty or not
        if (TextUtils.isEmpty(unames)) {
            meditext_uname.setError("Required");
        }
        else if (TextUtils.isEmpty(passwords)) {
            meditext_password.setError("Required");
        }else{

            validate = true;
        }
        return validate;
    }
    //changing fragments
    private void changeFragment(Fragment targetFragment){

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_logins, targetFragment, "fragment")
                .addToBackStack("fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
    public void logindialog(){
        //final boolean mstatuss=false;
        mdialog = new ProgressDialog(getContext());
        mdialog.setMessage("Loggin in...");
        mdialog.setCancelable(false);
        mdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mdialog.show();
        //new Handler().postDelayed(new Runnable() {
          //  @Override
            //public void run() {
              //  mdialog.show();
            //}
        //},2000);
//        mhandlers = new Handler();
//        mrunnables = new Runnable() {
//            @Override
//            public void run() {
//                if(status){
//                    mdialog.dismiss();
//                    startActivity(new Intent(getContext(), MainActivity.class));
//
//                }else{
//                    mdialog.dismiss();
//                }
//
//            }
//        };
//        mhandlers.postDelayed(mrunnables,1000);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//
//            }
//        },1000);
//
    }
}
