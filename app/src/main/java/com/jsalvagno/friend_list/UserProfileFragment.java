package com.jsalvagno.friend_list;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import static com.jsalvagno.friend_list.UserProfileActivity.activitiesURL;
import static com.jsalvagno.friend_list.UserProfileActivity.bio;
import static com.jsalvagno.friend_list.UserProfileActivity.curUser;
import static com.jsalvagno.friend_list.UserProfileActivity.imageURL;
import static com.jsalvagno.friend_list.UserProfileActivity.ratingsURL;
import static com.jsalvagno.friend_list.UserProfileActivity.uname;
import static com.jsalvagno.friend_list.UserProfileActivity.userId;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends Fragment {


    public UserProfileFragment() {
        // Required empty public constructor
    }

    TextView userNameView;
    TextView bioView;
    NetworkImageView icon;
    ImageLoader imageLoader;
    RatingBar rating;

    User user;
/*    String name, uname, city, bio, email, activitiesURL, ratingsURL;
    String userId, curUser;
    int zip;*/
    boolean finished = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View fragmentLayout = inflater.inflate(R.layout.fragment_user_profile, container, false);

        Intent intent = getActivity().getIntent();

        userNameView = (TextView) fragmentLayout.findViewById(R.id.userProfileUserName);
        bioView = (TextView) fragmentLayout.findViewById(R.id.userProfileBio);
        icon = (NetworkImageView) fragmentLayout.findViewById(R.id.userProfileUserIcon);
        rating = (RatingBar) fragmentLayout.findViewById(R.id.userRatingBar);

        /*userId = intent.getExtras().getString(ActivityDetailActivity.USER_ID_EXTRA);
        curUser = intent.getExtras().getString(ActivityDetailActivity.CURRENT_USER_ID_EXTRA);
        activitiesURL = intent.getExtras().getString(ActivityDetailActivity.USER_ACTIVITIES_EXTRA);
        ratingsURL = intent.getExtras().getString(ActivityDetailActivity.USER_RATINGS_EXTRA);*/
        userNameView.setText(uname);
        bioView.setText(bio);
        imageLoader = MySingleton.getInstance(getActivity().getApplicationContext()).getImageLoader();
        icon.setImageUrl(imageURL, imageLoader);
        rating.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    launchUserRatingsActivity();
                }
                return true;
            }
        });
        return fragmentLayout;

    }

    private void launchUserRatingsActivity() {
        //create a new intent that launches ActivityDetailActivity
        Intent intent = new Intent(getActivity(), UserRatingsActivity.class);

        //pass along data from activity clicked

        Log.d("user Activities", ratingsURL);
        intent.putExtra(UserProfileActivity.CURRENT_USER_ID_EXTRA, curUser);
        intent.putExtra(UserProfileActivity.User_Ratings_URL_EXTRA, ratingsURL);
        startActivity(intent);
    }

}
