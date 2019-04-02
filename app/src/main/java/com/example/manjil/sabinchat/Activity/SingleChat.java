package com.example.manjil.sabinchat.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.manjil.sabinchat.R;

public class SingleChat extends AppCompatActivity{
    private static final String TAG = "SingleChat";
    private String names;
    private ImageView mbtnback_imagevviews;
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

        }catch (NullPointerException ex){
            Log.d(TAG, "onCreate: catch null exception"+ex.toString());
        }
        }
        public void initviews(){
        mbtnback_imagevviews =(ImageView) findViewById(R.id.mimageview_btnback);

        }
        //setting click listners
        public void setonclicklisteners(){
            mbtnback_imagevviews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

}