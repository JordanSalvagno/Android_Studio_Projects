package com.jsalvagno.friend_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

/**
 * Created by jordansalvagno on 9/11/16.
 */
public class ActivityAdapter extends ArrayAdapter<Activity> {

    public static class ViewHolder{
        TextView title;
        TextView activity;
        ImageLoader imageLoader;
        NetworkImageView activityIcon;
    }
    public ActivityAdapter(Context context, ArrayList<Activity> activities){
        super(context, 0, activities);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // Get the data item for this position
        Activity activity = getItem(position);

        //create a new ViewHolder
        ViewHolder viewHolder;

        // Check if an existing view is being reused, otherwise inflate a new view from custom row layout
        if (convertView == null) {

            viewHolder = new ViewHolder();

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row, parent, false);

            //set our views to our view holder
            viewHolder.title = (TextView) convertView.findViewById(R.id.listItemActivityTitle);
            viewHolder.activity = (TextView) convertView.findViewById(R.id.listItemActivityBody);
            viewHolder.activityIcon = (NetworkImageView) convertView.findViewById(R.id.listItemUserImg);

            convertView.setTag(viewHolder);

        }
        else{
            //already have a view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //Fill each referenced view with data associated with activity it's referencing
       viewHolder.title.setText(activity.getTitle());
        viewHolder.activity.setText(activity.getMessage());
        String URL = activity.getUserImageURL();
        viewHolder.imageLoader = MySingleton.getInstance(this.getContext()).getImageLoader();
        viewHolder.activityIcon.setImageUrl(URL, viewHolder.imageLoader);
        //return modified view to be displayed
        return convertView;
    }
}
