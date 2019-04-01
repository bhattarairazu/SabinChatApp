package com.example.manjil.sabinchat.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.manjil.sabinchat.Activity.MainActivity;
import com.example.manjil.sabinchat.R;

/**
 * Created by User on 3/31/2019.
 */

public class Login extends Fragment {
    private TextView mtextview_createaccounts,mtextview_forgotpasswords;
    private TextView mgetstarted;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mview = inflater.inflate(R.layout.fragment_login,container,false);
        initviews(mview);
        setuponclicklisteners();
        return mview;
    }
    public void setuponclicklisteners(){
        //get started button seton click listners
        mgetstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), MainActivity.class));
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
        mtextview_createaccounts =(TextView) views.findViewById(R.id.create_account);
        mtextview_forgotpasswords =(TextView) views.findViewById(R.id.forgot_passwords);
        //getstarted button
        mgetstarted =(TextView) views.findViewById(R.id.login_getstarted);
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
}
