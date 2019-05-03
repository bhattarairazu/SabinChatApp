package com.example.manjil.sabinchat.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manjil.sabinchat.Constants.SharedPreference;
import com.example.manjil.sabinchat.Model.UserSignup;
import com.example.manjil.sabinchat.R;
import com.example.manjil.sabinchat.RestApi.ApiClient;
import com.example.manjil.sabinchat.RestApi.RetroInterface;

import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 3/31/2019.
 */

public class ForgotPassword extends Fragment {
    private static final String TAG = "ForgotPassword";
    //sending data to server
    RetroInterface minterface;
    SharedPreference mshared;
    TextView mtextviewrequest;
    EditText meditext_usernames;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mview = inflater.inflate(R.layout.fragment_forgotpassword,container,false);
        mshared = new SharedPreference(getContext());
        //view initlization
        initview(mview);
        onclicklisteners();
        return mview;
    }
    public void initview(View mview){
        mtextviewrequest =(TextView) mview.findViewById(R.id.request_passwowrd);

        //editext
        meditext_usernames =(EditText) mview.findViewById(R.id.medittext_requests);
    }
    public void onclicklisteners(){
        mtextviewrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String unames = meditext_usernames.getText().toString();
                mshared.set_verifyuname(unames);
                Log.d(TAG, "onClick: usernames"+unames);
                sending_password_reset(unames);
            }
        });
    }
    public void sending_password_reset(String uname){
        minterface = ApiClient.getAPICLIENT().create(RetroInterface.class);
        Call<UserSignup> msendemail = minterface.resetpin(uname);
        msendemail.enqueue(new Callback<UserSignup>() {
            @Override
            public void onResponse(Call<UserSignup> call, Response<UserSignup> response) {
                if(response.isSuccessful()){
                    if(response.body().getResults().getStatus()){
                        Toast.makeText(getContext(), ""+response.body().getResults().getMsg(), Toast.LENGTH_SHORT).show();
                        changeFragment(new VerifyPin());
                    }else{
                        Toast.makeText(getContext(), "Failed to send reset pin! Please Try Again", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserSignup> call, Throwable t) {
                Log.d(TAG, "onFailure: failed"+t.toString());
            }
        });
    }
    private void changeFragment(Fragment targetFragment){

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_logins, targetFragment, "fragment")
                .addToBackStack("fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

}
