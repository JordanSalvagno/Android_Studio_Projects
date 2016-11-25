package com.jsalvagno.friend_list;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

public class UserRatingsActivity extends BaseActivity {

    public static final String CURRENT_USER_ID_EXTRA = "com.jsalvagno.friend_list.Current User";
    public static final String RATING_COMMENT_EXTRA = "com.jsalvagno.friend_list.Rating Comment";
    public static final String RATING_UNAME_EXTRA = "com.jsalvagno.friend_list.Rating Uname";
    public static final String RATING_EXTRA = "com.jsalvagno.friend_list.Rating Rating";
    public static final String RATING_USER_ID_EXTRA = "com.jsalvagno.friend_list.Rating UserId";
    public static final String RATING_USER_IMAGE_EXTRA = "com.jsalvagno.friend_list.Rating UserImage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View contentView = inflater.inflate(R.layout.activity_user_ratings, null, false);
        drawer.addView(contentView, 0);
        //setContentView(R.layout.activity_user_ratings);
    }

}
