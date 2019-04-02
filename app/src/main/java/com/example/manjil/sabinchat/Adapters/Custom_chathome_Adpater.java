package com.example.manjil.sabinchat.Adapters;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.manjil.sabinchat.Model.Model_HomeChat;
import com.example.manjil.sabinchat.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 3/31/2019.
 */

public class Custom_chathome_Adpater extends BaseAdapter implements Filterable{
    private static final String TAG = "Custom_chathome_Adpater";
    private List<Model_HomeChat> mlisthomechat = new ArrayList<>();
    private Context mgetcontext;
    private ValueFilter mvaluefilter = new ValueFilter();

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

    @Override
    public Filter getFilter() {
        if(mvaluefilter == null){
            mvaluefilter = new ValueFilter();

        }
        return mvaluefilter;
    }

    //for filtering listivew data we use Filter class
    private class ValueFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults mresults = new FilterResults();

            //we implement here the filter logic
            if(constraint == null || constraint.length() == 0){
                //No filter implemented wee return all the list
                mresults.values = mlisthomechat;
                mresults.count = mlisthomechat.size();
            }else{
                //we perform filttering operations
                List<Model_HomeChat> mhomechatlist = new ArrayList<>();
                for(Model_HomeChat m: mlisthomechat){
                    if(m.getName().toUpperCase().startsWith(constraint.toString().toUpperCase())){
                        mhomechatlist.add(m);
                    }

                }
                mresults.values = mhomechatlist;
                mresults.count = mhomechatlist.size();
            }
            return mresults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //Now we have to inform the adapter abou the new list filter
            if(results.count == 0 ){
                notifyDataSetInvalidated();
            }else{
                mlisthomechat = (List)results.values;
                Log.d(TAG, "publishResults:mlistomechat "+mlisthomechat);
                notifyDataSetChanged();

            }

        }


    }

}
