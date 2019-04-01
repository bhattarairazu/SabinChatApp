package com.example.manjil.sabinchat.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.manjil.sabinchat.Model.PeopleListModel;
import com.example.manjil.sabinchat.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 3/31/2019.
 */

public class ListOfPeople_Adpater extends BaseAdapter{
    private List<PeopleListModel> mpeople_list = new ArrayList<>();
    private Context mcontext;
    //constructor

    public ListOfPeople_Adpater(List<PeopleListModel> mpeople_list, Context mcontext) {
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
        PeopleListModel mgetsingleitems = mpeople_list.get(position);
        //initilizing textview and image views
        TextView mlistpeope = (TextView) convertView.findViewById(R.id.tv_user_name);
        ImageView mimageiewpeople =(ImageView) convertView.findViewById(R.id.iv_user_photo);

        //setting data to respective view
        mlistpeope.setText(mgetsingleitems.getName());
        return convertView;
    }
}
