package com.abhigyan.user.movieviewer1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
//DESIGNED BY ABHIGYAN RAHA

/**
 * Created by Abhigyan on 13-08-2018.
 */

public class CustomReviewAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> reviewernameArraylist;
    ArrayList<String> reviewArraylist;
    ArrayList<String> urlArraylist;
    LayoutInflater layoutInflater;

    public CustomReviewAdapter(Context applicationContext, ArrayList<String> reviewernameArraylist, ArrayList<String> reviewArraylist, ArrayList<String> urlArraylist) {
        this.context = applicationContext;
        this.reviewernameArraylist = reviewernameArraylist;
        this.reviewArraylist = reviewArraylist;
        this.urlArraylist = urlArraylist;
        layoutInflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return reviewernameArraylist.size();
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

        convertView = layoutInflater.inflate(R.layout.reviewlist_ui, null);
        TextView nameTextReviewList = convertView.findViewById(R.id.nameTextreviewlist);
        TextView contentReviewList = convertView.findViewById(R.id.contentTextreviewlist);
        TextView urlReviewList = convertView.findViewById(R.id.urlTextreviewlist);
        nameTextReviewList.setText(reviewernameArraylist.get(position));
        contentReviewList.setText(reviewArraylist.get(position));
        urlReviewList.setText(urlArraylist.get(position));
        return convertView;
    }
}
