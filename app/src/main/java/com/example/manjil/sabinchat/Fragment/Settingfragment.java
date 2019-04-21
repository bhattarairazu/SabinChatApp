package com.example.manjil.sabinchat.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.manjil.sabinchat.Activity.LoginSignupActivity;
import com.example.manjil.sabinchat.R;
import com.example.manjil.sabinchat.Interfaces.Title_Text_Listeners;

import org.w3c.dom.Text;

/**
 * Created by User on 3/31/2019.
 */

public class Settingfragment extends Fragment {
    //initilization interface
    private Title_Text_Listeners mlisteners;
    private TextView changepasswords,logouts,updatproifle;

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
        //initviews
        initviews(mview);
        //onclicklistners
        onclicklisteners();
        return mview;
    }
    public void initviews(View mviews){
        //updating profile
        updatproifle =(TextView) mviews.findViewById(R.id.mtextview_updateprofile);
       // changepasswords =(TextView) mviews.findViewById(R.id.mtextview_changepassword);
        logouts =(TextView) mviews.findViewById(R.id.logout);


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
        AlertDialog malertidalogeus = new AlertDialog.Builder(getContext()).create();
        //malertidalogeus.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View mview =getLayoutInflater().inflate(R.layout.update_profile,null);
        EditText meditext = (EditText) mview.findViewById(R.id.current_password);

        EditText meditext_newpassword = (EditText) mview.findViewById(R.id.new_password);

        EditText meditext_confirm = (EditText) mview.findViewById(R.id.new_passwordconfirm);

        EditText meditext_setpassword = (EditText) mview.findViewById(R.id.setpassword);
        malertidalogeus.setView(mview);
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
                        startActivity(new Intent(getView().getContext(),LoginSignupActivity.class));

                    }
                })
                .setNegativeButton("No",null)
                .show();
    }
}
