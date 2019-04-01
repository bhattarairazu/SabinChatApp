package com.example.manjil.sabinchat.Adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.manjil.sabinchat.Model.Model_HomeChat;
import com.example.manjil.sabinchat.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 3/31/2019.
 */

public class Custom_chathome_Adpater extends BaseAdapter{
    private List<Model_HomeChat> mlisthomechat = new ArrayList<>();
    private Context mgetcontext;

    public Custom_chathome_Adpater(List<Model_HomeChat> mlisthomechat, Context mgetcontext) {
        this.mlisthomechat = mlisthomechat;
        this.mgetcontext = mgetcontext;
    }

    @Override
    public int getCount() {
        return mlisthomechat.size();
    }

    @Override
    public Object getItem(int position) {
        return mlisthomechat.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_chat,parent,false);
        }
        Model_HomeChat mmodelhome = mlisthomechat.get(position);
        TextView mtextviewname = (TextView) convertView.findViewById(R.id.tv_user_names);
        TextView mtextview_lastmessages = (TextView) convertView.findViewById(R.id.tv_last_chats);
        TextView mtextivwe_dates = (TextView) convertView.findViewById(R.id.tv_times);
        ImageView mimaagview_profile = (ImageView) convertView.findViewById(R.id.iv_user_photos);
        TextView mtextview_totalmessages = (TextView) convertView.findViewById(R.id.mtextview_totlamesaes);
        View mview = (View) convertView.findViewById(R.id.online_indicator);
        if(mmodelhome.isOnline_status()){
            mview.setVisibility(View.VISIBLE);
        }else{
            mview.setVisibility(View.GONE);
        }
        mtextivwe_dates.setText(mmodelhome.getDate());
        mtextviewname.setText(mmodelhome.getName());
        mtextview_lastmessages.setText(mmodelhome.getLast_message());
        mtextview_totalmessages.setText(String.valueOf(mmodelhome.getNo_of_unseenmessages()));

        return convertView;
    }
}
