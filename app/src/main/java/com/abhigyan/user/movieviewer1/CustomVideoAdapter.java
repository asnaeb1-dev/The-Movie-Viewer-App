package com.abhigyan.user.movieviewer1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
//DESIGNED BY ABHIGYAN RAHA

/**
 * Created by USER on 17-08-2018.
 */

public class CustomVideoAdapter extends BaseAdapter {


    Context context;
    ArrayList<String> nameList;
    ArrayList<String> typeList;
    LayoutInflater layoutInflater;

    public CustomVideoAdapter(Context context, ArrayList<String> nameList, ArrayList<String> typeList) {
        this.context = context;
        this.nameList = nameList;
        this.typeList = typeList;
        layoutInflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return nameList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = layoutInflater.inflate(R.layout.video_layout, null);
        TextView nameText = convertView.findViewById(R.id.nameText);
        TextView typeText = convertView.findViewById(R.id.typeText);
        nameText.setText(nameList.get(position));
        Log.i("name-*****************",nameList.get(position));
        typeText.setText(typeList.get(position));
        return convertView;
    }
}
