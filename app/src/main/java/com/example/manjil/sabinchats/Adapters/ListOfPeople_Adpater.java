package com.example.manjil.sabinchats.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.manjil.sabinchats.Constants.SharedPreference;
import com.example.manjil.sabinchats.Model.user_list.Resultss;
import com.example.manjil.sabinchats.R;
import com.example.manjil.sabinchats.RestApi.ApiClient;
import com.squareup.picasso.Picasso;

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
    SharedPreference mshared;
    int uids;


    //constructor

    public ListOfPeople_Adpater(List<Resultss> mpeople_list, Context mcontext) {
        this.mpeople_list = mpeople_list;
        this.mcontext = mcontext;
        mshared = new SharedPreference(mcontext);
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
              try{
                 uids = mshared.getuserids();
              }catch (ClassCastException ex){
                  Log.d(TAG, "getView: no userid found which is int"+ex.toString());
              }
              if(mgetsingleitems.getId()!=uids){
                  mlistpeope.setText(mgetsingleitems.getUsername());
                  if(mgetsingleitems.getPicture()!=null) {
                      Picasso.get().load(ApiClient.BASE_URL+mgetsingleitems.getPicture()).into(mimageiewpeople);
                  }

              }


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
