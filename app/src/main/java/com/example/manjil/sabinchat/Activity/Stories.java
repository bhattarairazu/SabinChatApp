package com.example.manjil.sabinchat.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.manjil.sabinchat.Constants.ConstantValue;
import com.example.manjil.sabinchat.Constants.SharedPreference;
import com.example.manjil.sabinchat.R;
import com.example.manjil.sabinchat.RestApi.ApiClient;
import com.squareup.picasso.Picasso;

import jp.shts.android.storiesprogressview.StoriesProgressView;

public class Stories extends AppCompatActivity implements StoriesProgressView.StoriesListener {
    private static final String TAG = "Stories";
    private StoriesProgressView storiesProgressView;
    private int PROGRESS_COUNT = 1;
    ImageView mimageveiw;
    SharedPreference msharedp;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);
        msharedp = new SharedPreference(this);
        storiesProgressView = (StoriesProgressView) findViewById(R.id.stories);
        mimageveiw =(ImageView) findViewById(R.id.image_stories);
        Picasso.get().load(ApiClient.BASE_URL+ConstantValue.stories_pictures).into(mimageveiw);
        storiesProgressView.setStoriesCount(PROGRESS_COUNT); // <- set stories
        storiesProgressView.setStoryDuration(1200L); // <- set a story duration
        storiesProgressView.setStoriesListener(this); // <- set listener
        storiesProgressView.startStories(); // <- start progress
    }

    @Override
    public void onNext() {
        Toast.makeText(this, "onNext", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPrev() {
//Call when finished revserse animation.
                Toast.makeText(this, "onPrev", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onComplete() {
        //Toast.makeText(this, "onComplete", Toast.LENGTH_SHORT).show();
        finish();
    }
    @Override
    protected void onDestroy() {
        // Very important !
        storiesProgressView.destroy();
        super.onDestroy();
    }
}
