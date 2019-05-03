package com.example.manjil.sabinchat.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manjil.sabinchat.Activity.Stories;
import com.example.manjil.sabinchat.Constants.ConstantValue;
import com.example.manjil.sabinchat.Model.stories.Model_stories;
import com.example.manjil.sabinchat.Model.stories.Results_stories;
import com.example.manjil.sabinchat.Model.user_list.Resultss;
import com.example.manjil.sabinchat.R;
import com.example.manjil.sabinchat.RestApi.ApiClient;
import com.example.manjil.sabinchat.RestApi.RetroInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Recyclerview_storiesadapter extends RecyclerView.Adapter<Recyclerview_storiesadapter.MyViewHolder> {
    private static final String TAG = "Recyclerview_storiesada";
    private List<Resultss> mpeople_list = new ArrayList<>();
    private Context mcontext;
    private RetroInterface minterface;

    public Recyclerview_storiesadapter(List<Resultss> mpeople_list, Context mcontext) {
        this.mpeople_list = mpeople_list;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.singlelistitem_stores,viewGroup,false);

        return new MyViewHolder(mview);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Resultss mgetstores = mpeople_list.get(i);
        myViewHolder.mtextview_storeistextview.setText(mgetstores.getUsername());
        if(mgetstores.getPicture()==null){
        Picasso.get().load(R.drawable.p_file).into(myViewHolder.mimaeview_storiesprofile);
        }else{
        Picasso.get().load(ApiClient.BASE_URL+mgetstores.getPicture()).into(myViewHolder.mimaeview_storiesprofile);
        }
    }

    @Override
    public int getItemCount() {
        return mpeople_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mimaeview_storiesprofile;
        TextView mtextview_storeistextview;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mimaeview_storiesprofile = (ImageView) itemView.findViewById(R.id.mimageview_storeis_image);
            mtextview_storeistextview = (TextView) itemView.findViewById(R.id.mtextview_storeis_name);
            mimaeview_storiesprofile.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterid = getAdapterPosition();
            getting_stories(mpeople_list.get(adapterid).getId(),v);

        }
    }
    public void getting_stories(int ids,final View v){

        minterface = ApiClient.getAPICLIENT().create(RetroInterface.class);
        Call<Model_stories> mstories = minterface.mgetstories(ids);
        mstories.enqueue(new Callback<Model_stories>() {
            @Override
            public void onResponse(Call<Model_stories> call, Response<Model_stories> response) {
                if(response.isSuccessful()){
                    if(response.body().getResult().size()==0){
                        Toast.makeText(mcontext, "No Stores To Show", Toast.LENGTH_SHORT).show();
                    }else{

                    for(int i = 0;i<response.body().getResult().size();i++){
                        Results_stories mstores= new Results_stories();
                        mstores.setId(response.body().getResult().get(i).getId());
                        mstores.setImage(response.body().getResult().get(i).getImage());
                        ConstantValue.stories_pictures = response.body().getResult().get(i).getImage();
                        mstores.setText(response.body().getResult().get(i).getText());
                        mstores.setUser_id(response.body().getResult().get(i).getUser_id());
                        mstores.setCreated_at(response.body().getResult().get(i).getCreated_at());
                        //mstorieslist.add(mstores);
                    }
                        v.getContext().startActivity(new Intent(v.getContext(),Stories.class));
                }
                }
            }

            @Override
            public void onFailure(Call<Model_stories> call, Throwable t) {
                Log.d(TAG, "onFailure: error"+t.toString());
            }
        });
    }

}
