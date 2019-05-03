package com.example.manjil.sabinchat.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import static android.support.constraint.Constraints.TAG;

public class VerifyPin extends Fragment {
    RetroInterface minterfaces;
    EditText meditextenterpin,meditextnewpasswords,medtextnewconformpasswords;
    TextView mtextview_verify,mtextviewresetpasswords;
    SharedPreference mshareds;
    LinearLayout mlinearlayout_pins,mlinearlayout_passwords;
    View mview;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         mview = inflater.inflate(R.layout.fragment_verifypin,container,false);
        mshareds = new SharedPreference(getContext());
        initview(mview);
        setonclicklisteners();
        return mview;
    }
    public void initview(View mviews){
        //for pin items
        meditextenterpin =(EditText) mviews.findViewById(R.id.meditext_enterpin);
        mtextview_verify =(TextView) mviews.findViewById(R.id.verify_pin);
        //linearlayout for showing pin reset
        mlinearlayout_pins =(LinearLayout) mviews.findViewById(R.id.mlinearlayout_verifypin);
        //linearlayout for showing password new
        mlinearlayout_passwords =(LinearLayout) mviews.findViewById(R.id.mlinearlayout_newpassword);


    }
    public void setonclicklisteners(){
        mtextview_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pins = Integer.parseInt(meditextenterpin.getText().toString());
                String unames = mshareds.get_usnames_verify();
                verify_pin(pins,unames);

            }
        });
    }
    public void verify_pin(int pins,String usernames){
     minterfaces = ApiClient.getAPICLIENT().create(RetroInterface.class);
        Call<UserSignup> mverifypin = minterfaces.verifypin(usernames,pins);
        mverifypin.enqueue(new Callback<UserSignup>() {
            @Override
            public void onResponse(Call<UserSignup> call, Response<UserSignup> response) {
                if(response.isSuccessful()){
                    if(response.body().getResults().getStatus()){
                        Toast.makeText(getContext(), ""+response.body().getResults().getMsg(), Toast.LENGTH_SHORT).show();
                        showing_newpassword();
                    }else{
                        Toast.makeText(getContext(), "Failed to Verify Pin !Please Try Again", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserSignup> call, Throwable t) {
                Log.d(TAG, "onFailure: failed "+t.toString());
            }
        });
    }
    public void showing_newpassword(){
        mlinearlayout_pins.setVisibility(View.GONE);
        mlinearlayout_passwords.setVisibility(View.VISIBLE);
        meditextnewpasswords = (EditText) mview.findViewById(R.id.meditext_newpasswords);
        medtextnewconformpasswords = (EditText) mview.findViewById(R.id.meditext_conformnewpasswords);

        mtextviewresetpasswords =(TextView) mview.findViewById(R.id.setpasswords_reset);
        //onclick listeners
        mtextviewresetpasswords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwords = meditextnewpasswords.getText().toString();
                String conformpasswords = medtextnewconformpasswords.getText().toString();
                if(TextUtils.isEmpty(passwords)){
                    meditextnewpasswords.setError("Required");
                    return;
                }
                if(TextUtils.isEmpty(conformpasswords)){
                    medtextnewconformpasswords.setError("Required");
                    return;
                }
                if(passwords.matches(conformpasswords)){
                    update_oldpassword(passwords);
                }else{
                    medtextnewconformpasswords.setError("Password Doesn't Match with password you type! Please Type the Corect Password");
                }

            }
        });
    }
    public void update_oldpassword(String password_news){
        minterfaces = ApiClient.getAPICLIENT().create(RetroInterface.class);
        Call<UserSignup> mupdate = minterfaces.update_password(mshareds.get_usnames_verify(),password_news);
        mupdate.enqueue(new Callback<UserSignup>() {
            @Override
            public void onResponse(Call<UserSignup> call, Response<UserSignup> response) {
                if(response.isSuccessful()){
                    if(response.body().getResults().getStatus()){
                        Toast.makeText(getContext(), "Password Sucessfully Updated", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getContext(), "Please Login To Continue", Toast.LENGTH_SHORT).show();
                        changeFragment(new Login());

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
    private void changeFragment(Fragment targetFragment){

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_logins, targetFragment, "fragment")
                .addToBackStack("fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}

