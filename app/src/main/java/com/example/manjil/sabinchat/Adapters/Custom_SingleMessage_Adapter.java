package com.example.manjil.sabinchat.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.manjil.sabinchat.Model.Model_Message_Singleitems;
import com.example.manjil.sabinchat.R;

import org.w3c.dom.Text;

import java.util.List;

public class Custom_SingleMessage_Adapter extends BaseAdapter {
    private static final String TAG = "Custom_SingleMessage_Ad";
    private Context mcontext;
    private List<Model_Message_Singleitems> mmessage_list;

    public Custom_SingleMessage_Adapter(Context mcontext, List<Model_Message_Singleitems> mmessage_list) {
        this.mcontext = mcontext;
        this.mmessage_list = mmessage_list;
    }

    @Override
    public int getCount() {
        return mmessage_list.size();
    }

    @Override
    public Object getItem(int position) {
        return mmessage_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            Model_Message_Singleitems msingle_items = mmessage_list.get(position);
            if(msingle_items.isFromHostUser()){
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlelist_tomessage_items,parent,false);
                //initilization views for to message
                TextView mtextview_to =(TextView) convertView.findViewById(R.id.mtextview_tomessages);
                mtextview_to.setText(msingle_items.getSend_messages());

            }else{
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlelist_frommessage_items,parent,false);
                //initilization views for from message items
                ImageView mimageview_from = (ImageView) convertView.findViewById(R.id.mimageview_frommessage);
                TextView mtextview_from = (TextView) convertView.findViewById(R.id.mtextview_frommessage);
                mtextview_from.setText(msingle_items.getSend_messages());
            }
            //try {


           // }catch (NullPointerException ex){
              //  Log.d(TAG, "getView: adapter null pointer view exception"+ex.toString());
            //}



        }
        return convertView;
    }
}
