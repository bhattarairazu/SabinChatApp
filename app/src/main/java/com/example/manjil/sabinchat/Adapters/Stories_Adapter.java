package com.example.manjil.sabinchat.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.manjil.sabinchat.Model.user_list.Resultss;
import com.example.manjil.sabinchat.R;
import com.example.manjil.sabinchat.RestApi.ApiClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Stories_Adapter extends BaseAdapter {
    private static final String TAG = "Stories_Adapter";
    private List<Resultss> mpeople_list = new ArrayList<>();
    private Context mcontext;

    public Stories_Adapter(List<Resultss> mpeople_list, Context mcontext) {
        this.mpeople_list = mpeople_list;
        this.mcontext = mcontext;
    }

    @Override
    public int getCount() {
        return mpeople_list.size();
    }

    @Override
    public Object getItem(int position) {
        return mpeople_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlelistitem_stores,parent,false);
        }
        //getting current item to be displayes
        Resultss mgetsingleitems = mpeople_list.get(position);
        //initilizing textview and image views
//        View mview = (View) convertView.findViewById(R.id.online_indicators);
        ImageView mimageview_pic = (ImageView) convertView.findViewById(R.id.mimageview_storeis_image);
        TextView mtextview = (TextView) convertView.findViewById(R.id.mtextview_storeis_name);

        Picasso.get().load(ApiClient.BASE_URL+mgetsingleitems.getPicture()).into(mimageview_pic);
        mtextview.setText(mgetsingleitems.getUsername());



        return convertView;
    }
}
