package com.example.manjil.sabinchat.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.manjil.sabinchat.Fragment.Login;
import com.example.manjil.sabinchat.Fragment.VerifyPin;
import com.example.manjil.sabinchat.R;

/**
 * Created by User on 3/31/2019.
 */

public class LoginSignupActivity extends AppCompatActivity {
    Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initviews();
        //loading default fragment
        Login mlogin_fragme = new Login();
        changeFragment(mlogin_fragme);


    }
    public void initviews(){

    }

    private void changeFragment(Fragment targetFragment){

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_logins, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
