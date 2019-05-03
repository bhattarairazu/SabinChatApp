package com.example.manjil.sabinchat.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manjil.sabinchat.Constants.SharedPreference;
import com.example.manjil.sabinchat.Model.UserSignup;
import com.example.manjil.sabinchat.Model.user_list.Resultss;
import com.example.manjil.sabinchat.Model.user_list.Userlists;
import com.example.manjil.sabinchat.R;
import com.example.manjil.sabinchat.RestApi.ApiClient;
import com.example.manjil.sabinchat.RestApi.RetroInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateGroupChat_Adapter extends BaseAdapter implements Filterable {
    private static final String TAG = "CreateGroupChat_Adapter";
    private List<Resultss> mpeople_list = new ArrayList<>();
    private Context mcontext;
    ValueFilter mfilters = new ValueFilter();
    SharedPreference mshared;
    RetroInterface minterfaces;
    int uids;
    public CreateGroupChat_Adapter(List<Resultss> mpeople_list, Context mcontext) {
        this.mpeople_list = mpeople_list;
        this.mcontext = mcontext;
        mshared = new SharedPreference(mcontext);
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_addusergroup,parent,false);
        }
        //getting current item to be displayes
        final Resultss mgetsingleitems = mpeople_list.get(position);
        //initilizing textview and image views
        View mview = (View) convertView.findViewById(R.id.online_indicators);
        TextView mlistpeope = (TextView) convertView.findViewById(R.id.tv_user_name);
        ImageView mimageiewpeople =(ImageView) convertView.findViewById(R.id.iv_user_photo);
        ImageButton maddbutton = (ImageButton) convertView.findViewById(R.id.mbutton_addtogroup);

        if(mgetsingleitems.getStatus()==1){
            mview.setVisibility(View.VISIBLE);
            try{
                uids = mshared.getuserids();
            }catch (ClassCastException ex){
                Log.d(TAG, "getView: class cast exception"+ex.toString());
            }
            //setting data to respective view
            if(mgetsingleitems.getId()!=uids){
                mlistpeope.setText(mgetsingleitems.getUsername());
                if(mgetsingleitems.getPicture()!=null) {
                    Picasso.get().load(ApiClient.BASE_URL+mgetsingleitems.getPicture()).into(mimageiewpeople);
                }

            }


        }else{
            mview.setVisibility(View.GONE);
        }
        //on add button clicks
        //adding respective user to the group
        maddbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userids = mgetsingleitems.getId();
                adduser_to_group(userids);
            }
        });

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
    //adding user to the group
    public void adduser_to_group(int userids){
        minterfaces = ApiClient.getAPICLIENT().create(RetroInterface.class);
        Call<UserSignup> madduser = minterfaces.madduser_to_group(mshared.getGroupId(),userids);
        madduser.enqueue(new Callback<UserSignup>() {
            @Override
            public void onResponse(Call<UserSignup> call, Response<UserSignup> response) {
                if(response.isSuccessful()){
                    if(response.body().getResults().getStatus()){
                        Toast.makeText(mcontext, ""+response.body().getResults().getMsg(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(mcontext, "Failed To Add User!Please Try Again", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserSignup> call, Throwable t) {
                Log.d(TAG, "onFailure: faile"+t.toString());
                Toast.makeText(mcontext, "Failed To Add User!Please Try Again", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
