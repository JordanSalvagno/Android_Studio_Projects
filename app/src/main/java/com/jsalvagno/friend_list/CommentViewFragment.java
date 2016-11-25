package com.jsalvagno.friend_list;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CommentViewFragment extends Fragment {

    TextView userName;
    TextView comment;
    RatingBar rating;
    NetworkImageView icon;
    ImageLoader imageLoader;

    public CommentViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentLayout = inflater.inflate(R.layout.fragment_comment_view, container, false);

        userName = (TextView) fragmentLayout.findViewById(R.id.commentViewUserName);
        comment = (TextView) fragmentLayout.findViewById(R.id.commentViewComment);
        rating = (RatingBar) fragmentLayout.findViewById(R.id.commentViewRating);
        icon = (NetworkImageView) fragmentLayout.findViewById(R.id.commentViewUserIcon);

        Intent intent = getActivity().getIntent();

        userName.setText(intent.getExtras().getString(UserRatingsActivity.RATING_UNAME_EXTRA));
        comment.setText(intent.getExtras().getString(UserRatingsActivity.RATING_COMMENT_EXTRA));
        rating.setRating(intent.getExtras().getFloat(UserRatingsActivity.RATING_EXTRA));
        imageLoader = MySingleton.getInstance(getActivity().getApplicationContext()).getImageLoader();
        String imageURL = intent.getExtras().getString(UserRatingsActivity.RATING_USER_IMAGE_EXTRA);
        icon.setImageUrl(imageURL, imageLoader);

        return fragmentLayout;
    }

    private void launchUserProfileActivity(){

    }

}
