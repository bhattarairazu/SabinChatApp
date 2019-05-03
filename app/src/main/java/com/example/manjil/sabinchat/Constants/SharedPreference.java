package com.example.manjil.sabinchat.Constants;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.manjil.sabinchat.Activity.LoginSignupActivity;
import com.example.manjil.sabinchat.Activity.MainActivity;
import com.example.manjil.sabinchat.Interfaces.Interfacess;

import java.util.HashMap;

public class SharedPreference {
    public static int user_ids = 0;
    //Shared Preferences
    SharedPreferences pref;

    //Editor for Shared Preferences
    SharedPreferences.Editor  editor;

    //context
    Context _context;

    int PRIVATE_MODE = 0;

    //SharedPref file name
    private static final String PREF_NAME = "UserDetails";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_USERNAME = "username";

    public static final String KEY_USERID = null;

    public static final String KEY_PICTURE = "picture";

    public static final String KEY_STATUS = "status";

    public static final String KEY_GROUP = null;
    public static final String KEY_VERIYUSERNAME = null;
    public static final String KEY_STORIES = null;

    // Constructor
    public SharedPreference(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
 /* Create login session
     * */
    public void createLoginSession(String username, int user_id,int status,String picture){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_USERNAME, username);

        // Storing email in pref
        editor.putString(KEY_PICTURE, picture);

        editor.putInt(KEY_USERID,user_id);

        editor.putInt(KEY_STATUS,status);

        // commit changes
        editor.commit();
    }
    //setting stories images
    public void set_stories_picture(String pic){
        editor.putString(KEY_STORIES,pic);
        editor.commit();
    }
    //getting stories
    public String get_Stories(){
        return pref.getString(KEY_STORIES,null);
    }

    public void setusername(String uname,String pictures){
        editor.putString(KEY_USERNAME,uname);
        editor.putString(KEY_PICTURE,pictures);
        editor.commit();
    }

    public void set_groupid(int groupid){
        editor.putInt(KEY_GROUP,groupid);
        editor.commit();
    }
    public void set_verifyuname(String unames){
        editor.putString(KEY_VERIYUSERNAME,unames);
        editor.commit();
    }
    public String get_usnames_verify(){
        return pref.getString(KEY_VERIYUSERNAME,null);
    }

    public int getGroupId(){
        return pref.getInt(KEY_GROUP,0);
    }
    /**
     * Get stored session data
     * */
//    public HashMap<String, String> getUserDetails(){
//        HashMap<String, String> user = new HashMap<String, String>();
//        // user name
//        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));
//
//        // user email id
//        user.put(KEY_PICTURE, pref.getString(KEY_PICTURE, null));
//
//        user.put(KEY_USERID,pref.getInt(KEY_USERID,0));
//
//        user.put(KEY_STATUS,pref.getInt(KEY_STATUS,0));
//
//        // return user
//        return user;
//    }
    public int getuserids(){
        return pref.getInt(KEY_USERID,0);
    }

    public String getpitures(){
        return pref.getString(KEY_PICTURE,null);
    }
    public String getusername(){
        return pref.getString(KEY_USERNAME,null);
    }
    public int getstatus(){
        return pref.getInt(KEY_STATUS,0);
    }

    //setting values



    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginSignupActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginSignupActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }
}
