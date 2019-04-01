package com.example.manjil.sabinchat.Activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.manjil.sabinchat.Fragment.ChatFragment;
import com.example.manjil.sabinchat.Fragment.Chat_Profile;
import com.example.manjil.sabinchat.Fragment.ListOfPeople;
import com.example.manjil.sabinchat.Fragment.Settingfragment;
import com.example.manjil.sabinchat.R;
import com.example.manjil.sabinchat.Constants.Route;

public class MainActivity extends AppCompatActivity {
    Context mContext;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.nav_chat:
                    Route.attachFragment(MainActivity.this, R.id.fragment_container, new Chat_Profile(), "a", false);
                    return true;

                case R.id.navfriend:
                    Route.attachFragment(MainActivity.this, R.id.fragment_container, new ListOfPeople(), "a", false);
                    return true;

                case R.id.nav_setting:
                    Route.attachFragment(MainActivity.this, R.id.fragment_container, new Settingfragment(), "a", false);
                    return true;


            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        BottomNavigationViewHelper.disableShiftMode(bottom_navigation);

        mContext = this;
        if( savedInstanceState == null )
            changeFragment(new Chat_Profile());
    }
    private void changeFragment(Fragment targetFragment){

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, targetFragment, "fragment")
                .addToBackStack("fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}


