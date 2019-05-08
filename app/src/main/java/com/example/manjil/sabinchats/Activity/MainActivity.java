package com.example.manjil.sabinchats.Activity;

import android.Manifest;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manjil.sabinchats.Constants.SharedPreference;
import com.example.manjil.sabinchats.Fragment.Chat_Profile;
import com.example.manjil.sabinchats.Fragment.ListOfPeople;
import com.example.manjil.sabinchats.Fragment.Settingfragment;
import com.example.manjil.sabinchats.Model.stories.Model_stories;
import com.example.manjil.sabinchats.Model.stories.Results_stories;
import com.example.manjil.sabinchats.Model.user_list.Userlists;
import com.example.manjil.sabinchats.R;
import com.example.manjil.sabinchats.Constants.Route;
import com.example.manjil.sabinchats.Interfaces.Title_Text_Listeners;
import com.example.manjil.sabinchats.RestApi.ApiClient;
import com.example.manjil.sabinchats.RestApi.RetroInterface;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Title_Text_Listeners {
    private static final String TAG = "MainActivity";
    Context mContext;
    private TextView mtextviewtitle;
    int userids;
    SharedPreference msharedpreferene;
    RetroInterface minterface;
    ImageView tolbarimageview,refreshimageview;
    List<Results_stories> mstorieslist = new ArrayList<>();
    LinearLayout mstorieslayout;
    int count = 0;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.nav_chat:
                    Route.attachFragment(MainActivity.this, R.id.fragment_container, new Chat_Profile(), "a", true);
                    return true;

                case R.id.navfriend:
                    Route.attachFragment(MainActivity.this, R.id.fragment_container, new ListOfPeople(), "a", true);
                    return true;

                case R.id.nav_setting:
                    Route.attachFragment(MainActivity.this, R.id.fragment_container, new Settingfragment(), "a", true);
                    return true;


            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        msharedpreferene = new SharedPreference(this);


        requestStoragePermission();
        tolbarimageview =(ImageView) findViewById(R.id.mtolbarimageview);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        BottomNavigationViewHelper.disableShiftMode(bottom_navigation);
        try{
            userids = msharedpreferene.getuserids();
            Log.d(TAG, "onCreate: user ids is"+userids);
        }catch (ClassCastException ex){
            Log.d(TAG, "onCreate: getting user ids exception"+ex.toString());
        }
        initview();
        mContext = this;
        if( savedInstanceState == null )
            changeFragment(new Chat_Profile());
        get_userslist();
         Picasso.get().load(ApiClient.BASE_URL+msharedpreferene.getpitures()).into(tolbarimageview);

    }
    public void initview(){
        mtextviewtitle =(TextView) findViewById(R.id.mtextview_activitytitle);
        refreshimageview =(ImageView) findViewById(R.id.refreshs);

        //refreshbutton click listners
        refreshimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_userslist();
            }
        });

    }
    @Override
    public void onBackPressed() {
        count++;
        if(count==4){
            finish();
        }

        if(getSupportFragmentManager().getBackStackEntryCount()>1){
            getSupportFragmentManager().popBackStack();
        }else{
            // alerdialog_exit("Are You Sure You Want to Exit ? ",0);
           // showDialog(MainActivity.this, "Are You Sure You Want to Exit ? ",0);
            //Toast.makeText(mContext, "Press Again To Exit", Toast.LENGTH_SHORT).show();
            finish();
        }

    }
    private void changeFragment(Fragment targetFragment){

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, targetFragment, "fragment")
                .addToBackStack("fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @Override
    public void settitle(String title) {
        mtextviewtitle.setText(title);

    }
    //getting all users data
    public void get_userslist(){
        minterface = ApiClient.getAPICLIENT().create(RetroInterface.class);
        Call<Userlists> mgetlist_user = minterface.mgetuser_list_single(userids);
        mgetlist_user.enqueue(new Callback<Userlists>() {
            @Override
            public void onResponse(Call<Userlists> call, Response<Userlists> response) {
                if(response.isSuccessful()){
                    for(int i = 0 ;i<response.body().getResults().size();i++){
                        Log.d(TAG, "onResponse: "+response.body().getResults().get(i).getUsername());
                        msharedpreferene.setusername(response.body().getResults().get(0).getUsername(),response.body().getResults().get(0).getPicture());
                            Picasso.get().load(ApiClient.BASE_URL+msharedpreferene.getpitures()).into(tolbarimageview);

                    }

//                   St                                                                                                ring pic = response.body().getResults().get(0).getPicture();
//                   String name  = response.body().getResults().get(0).getUsername();
//                    Log.d(TAG, "onResponse: pictures i s"+ pic);
//                    Log.d(TAG, "onResponse: usernames is"+name);
                   //if(response.code()==200){
                       // for(int i =0;i<response.body().getResults().size();i++){
//                            Resultss mresult = new Resultss();
//                           // if(response.body().getResults().get(i).getId()==userids) {
//                                msharedpreferene.setusername(response.body().getResults().get(i).getUsername(),response.body().getResults().get(i).getPicture());
//                                Log.d(TAG, "onResponse: usernames set"+response.body().getResults().get(i).getPicture()+"userids"+userids);
//                                Picasso.get().load(ApiClient.BASE_URL+response.body().getResults().get(i).getPicture()).into(tolbarimageview);
//                           // }

                     //   }


                    //}
                }
            }

            @Override
            public void onFailure(Call<Userlists> call, Throwable t) {
                Log.d(TAG, "onFailure: failed"+t.toString());

            }
        });
    }
    public void getting_stories(){
        minterface = ApiClient.getAPICLIENT().create(RetroInterface.class);
        Call<Model_stories> mstories = minterface.mgetstories(msharedpreferene.getuserids());
        mstories.enqueue(new Callback<Model_stories>() {
            @Override
            public void onResponse(Call<Model_stories> call, Response<Model_stories> response) {
                if(response.isSuccessful()){
                    for(int i = 0;i<response.body().getResult().size();i++){
                        Results_stories mstores= new Results_stories();
                        mstores.setId(response.body().getResult().get(i).getId());
                        mstores.setImage(response.body().getResult().get(i).getImage());
                        mstores.setText(response.body().getResult().get(i).getText());
                        mstores.setUser_id(response.body().getResult().get(i).getUser_id());
                        mstores.setCreated_at(response.body().getResult().get(i).getCreated_at());
                        mstorieslist.add(mstores);
                    }
                }
            }

            @Override
            public void onFailure(Call<Model_stories> call, Throwable t) {
                Log.d(TAG, "onFailure: error"+t.toString());
            }
        });
    }
    private void requestStoragePermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

}


