package com.example.manjil.sabinchat.Adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.manjil.sabinchat.Model.Model_Message_Singleitems;
import com.example.manjil.sabinchat.Model.Model_messagelist;
import com.example.manjil.sabinchat.Model.Model_sendingmessage;
import com.example.manjil.sabinchat.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class Custom_SingleMessage_Adapter extends BaseAdapter {
    private static final String TAG = "Custom_SingleMessage_Ad";
    private Context mcontext;
    private List<Model_sendingmessage> mmessage_list;
    private int hostuser;
    private String pictureurl = "http://appzza.com:3000/";

    public Custom_SingleMessage_Adapter(Context mcontext, List<Model_sendingmessage> mmessage_list,int hostuser) {
        this.mcontext = mcontext;
        this.mmessage_list = mmessage_list;
        this.hostuser = hostuser;
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
    public int getItemViewType(int position) throws IndexOutOfBoundsException {
        int i = (mmessage_list).get(position).getFrom_id()==hostuser?1:0;

        return i;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        if(convertView == null){
             if(type==1){
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlelist_tomessage_items,parent,false);
                //initilization views for to message


            }else{
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlelist_frommessage_items,parent,false);
                //initilization views for from message items

            }
        }
        Model_sendingmessage msingle_items = mmessage_list.get(position);
        Log.d(TAG, "getView: postionsitons"+position);
        if(type == 1){
                TextView mtextview_to =(TextView) convertView.findViewById(R.id.mtextview_tomessages);
            ImageView mimageview = (ImageView) convertView.findViewById(R.id.miageview_imageto);
            mimageview.setVisibility(View.GONE);

             if(!msingle_items.getPicture().matches("")){
                 Log.d(TAG, "getView: msing pictures detials"+msingle_items.getPicture());

                 mimageview.setVisibility(View.VISIBLE);
               // Log.d(TAG, "getView: getting image uris"+msingle_items.getMuri());
               // mimageview.setImageURI(msingle_items.getMuri());
                Picasso.get().load(pictureurl+msingle_items.getPicture()).into(mimageview);
            }

                mtextview_to.setText(msingle_items.getMessage());
            }

            if (type==0){
                ImageView mimageview_from = (ImageView) convertView.findViewById(R.id.mimageview_frommessage);
                TextView mtextview_from = (TextView) convertView.findViewById(R.id.mtextview_frommessage);
                ImageView mimavew_image_froms = (ImageView) convertView.findViewById(R.id.miageview_imagefrom);
                mimavew_image_froms.setVisibility(View.GONE);

                if(!msingle_items.getPicture().matches("")){
                    mimavew_image_froms.setVisibility(View.VISIBLE);

                    Picasso.get().load(pictureurl+msingle_items.getPicture()).into(mimavew_image_froms);

                }
                mtextview_from.setText(msingle_items.getMessage());
            }
            //try {


           // }catch (NullPointerException ex){
              //  Log.d(TAG, "getView: adapter null pointer view exception"+ex.toString());
            //}




        return convertView;
    }
}
