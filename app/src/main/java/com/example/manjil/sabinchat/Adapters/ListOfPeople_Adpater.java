package com.example.manjil.sabinchat.Adapters;

import android.content.Context;
import android.provider.Contacts;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.manjil.sabinchat.Fragment.ListOfPeople;
import com.example.manjil.sabinchat.Model.Model_HomeChat;
import com.example.manjil.sabinchat.Model.PeopleListModel;
import com.example.manjil.sabinchat.Model.user_list.Resultss;
import com.example.manjil.sabinchat.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 3/31/2019.
 */

public class ListOfPeople_Adpater extends BaseAdapter implements Filterable {
    private static final String TAG = "ListOfPeople_Adpater";
    private List<Resultss> mpeople_list = new ArrayList<>();
    private Context mcontext;
    private ValueFilter mfilters = new ValueFilter();
    //constructor

    public ListOfPeople_Adpater(List<Resultss> mpeople_list, Context mcontext) {
        this.mpeople_list = mpeople_list;
        this.mcontext = mcontext;
    }

    @Override
    public int getCount() {
        return mpeople_list.size();//return total list size
    }

    @Override
    public Object getItem(int position) {
        return mpeople_list.get(position);//return list items at specific position
    }

    @Override
    public long getItemId(int position) {
        return position; //retun only positoin
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_people,parent,false);
        }
        //getting current item to be displayes
        Resultss mgetsingleitems = mpeople_list.get(position);
        //initilizing textview and image views
        View mview = (View) convertView.findViewById(R.id.online_indicators);
        TextView mlistpeope = (TextView) convertView.findViewById(R.id.tv_user_name);
        ImageView mimageiewpeople =(ImageView) convertView.findViewById(R.id.iv_user_photo);
         if(mgetsingleitems.getStatus()==1){
            mview.setVisibility(View.VISIBLE);

             //setting data to respective view
             mlistpeope.setText(mgetsingleitems.getUsername());

         }else{
            mview.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if(mfilters == null){
            mfilters = new ValueFilter();
        }
        return mfilters;
    }

    //for filtering listivew data we use Filter class
    private class ValueFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults mresults = new FilterResults();

            //we implement here the filter logic
            if(constraint == null || constraint.length() == 0){
                //No filter implemented wee return all the list
                mresults.values = mpeople_list;
                mresults.count = mpeople_list.size();
            }else{
                //we perform filttering operations
                List<Resultss> mpeoplechatlists = new ArrayList<>();
                for(Resultss m: mpeople_list){
                    if(m.getName().toUpperCase().startsWith(constraint.toString().toUpperCase())){
                        mpeoplechatlists.add(m);
                    }

                }
                mresults.values = mpeoplechatlists;
                mresults.count = mpeoplechatlists.size();
            }
            return mresults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //Now we have to inform the adapter abou the new list filter
            if(results.count == 0 ){
                notifyDataSetInvalidated();
            }else{
                mpeople_list = (List)results.values;
                Log.d(TAG, "publishResults:mlistomechat "+mpeople_list);
                notifyDataSetChanged();

            }

        }


    }
}
