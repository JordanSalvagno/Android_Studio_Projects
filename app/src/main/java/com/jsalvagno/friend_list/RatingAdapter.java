package com.jsalvagno.friend_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

/**
 * Created by jordansalvagno on 9/20/16.
 */

public class RatingAdapter extends ArrayAdapter<Rating> {

    public static class ViewHolder{
        TextView comment;
        TextView userName;
        RatingBar rating;
        NetworkImageView userIcon;
        ImageLoader imageLoader;
    }
    public RatingAdapter(Context context, ArrayList<Rating> ratings){
        super(context, 0, ratings);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // Get the data item for this position
        Rating rating = getItem(position);

        //create a new ViewHolder
        ViewHolder viewHolder;

        // Check if an existing view is being reused, otherwise inflate a new view from custom row layout
        if (convertView == null) {

            viewHolder = new ViewHolder();

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rating_list_row, parent, false);

            //set our views to our view holder
            viewHolder.comment = (TextView) convertView.findViewById(R.id.ratingListItemComment);
            viewHolder.userName = (TextView) convertView.findViewById(R.id.ratingListItemUserName);
            viewHolder.rating = (RatingBar) convertView.findViewById(R.id.ratingListItemRating);
            viewHolder.userIcon = (NetworkImageView) convertView.findViewById(R.id.ratingListItemUserImg);

            convertView.setTag(viewHolder);

        }
        else{
            //already have a view
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //Fill each referenced view with data associated with activity it's referencing
        viewHolder.comment.setText(rating.getComment());
        viewHolder.userName.setText(rating.getCommenterUname());
        viewHolder.rating.setRating(rating.getRating());
        String URL = rating.getCommentersImage();
        viewHolder.imageLoader = MySingleton.getInstance(this.getContext()).getImageLoader();
        viewHolder.userIcon.setImageUrl(URL, viewHolder.imageLoader);

        //return modified view to be displayed
        return convertView;
    }
}

