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
import java.util.Random;

/** * Created by jordansalvagno on 11/8/16. */
public class InterestedUserAdapter extends ArrayAdapter<User> {
    public static class ViewHolder{
        ImageLoader imageLoader;
        NetworkImageView userImage;
        TextView userName;
        RatingBar userRating;
    }
    public InterestedUserAdapter(Context context, ArrayList<User> interestedUsers){
        super(context, 0, interestedUsers);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // Get the data item for this position
        User interestedUser = getItem(position);

        //create a new ViewHolder
        ViewHolder viewHolder;

        // Check if an existing view is being reused, otherwise inflate a new view from custom row layout
        if (convertView == null) {

            viewHolder = new ViewHolder();

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_list_row, parent, false);

            //set our views to our view holder
            viewHolder.userImage = (NetworkImageView) convertView.findViewById(R.id.InterestedUserListItemUserImg);
            viewHolder.userName  = (TextView) convertView.findViewById(R.id.InterestedUserListItemUserName);
            viewHolder.userRating = (RatingBar) convertView.findViewById(R.id.InterestedUserListItemRating);



            convertView.setTag(viewHolder);

        }
        else{
            //already have a view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String URL = interestedUser.getImageUrl();
        //Fill each referenced view with data associated with activity it's referencing
        viewHolder.imageLoader = MySingleton.getInstance(this.getContext()).getImageLoader();
        viewHolder.userImage.setImageUrl(URL, viewHolder.imageLoader);
        viewHolder.userName.setText(interestedUser.getUname());
        Random random = new Random();
        viewHolder.userRating.setRating(random.nextInt(5) + 1);

        //return modified view to be displayed
        return convertView;
    }

}
