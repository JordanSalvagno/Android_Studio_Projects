package com.jsalvagno.friend_list;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class ActivityDetailActivity extends BaseActivity {

    public static final String CURRENT_USER_ID_EXTRA = "com.jsalvagno.friend_list.Current User";
    public static final String USER_ID_EXTRA = "com.jsalvagno.friend_list.UserId";
    public static final String USER_NAME_EXTRA = "com.jsalvagno.friend_list.name";
    public static final String USER_UNAME_EXTRA = "com.jsalvagno.friend_list.uname";
    public static final String USER_CITY_EXTRA = "com.jsalvagno.friend_list.city";
    public static final String USER_ZIP_EXTRA = "com.jsalvagno.friend_list.zip";
    public static final String USER_BIO_EXTRA = "com.jsalvagno.friend_list.bio";
    public static final String USER_EMAIL_EXTRA = "com.jsalvagno.friend_list.email";
    public static final String USER_ACTIVITIES_EXTRA = "com.jsalvagno.friend_list.activities";
    public static final String USER_RATINGS_EXTRA = "com.jsalvagno.friend_list.ratings";
    public static final String NEW_ACTIVITY_EXTRA = "com.jsalvagno.friend_list.NewActivity";
    public static Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getApplicationContext();
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_activity_detail, null, false);
        drawer.addView(contentView, 0);
        //setContentView(R.layout.activity_activity_detail);
        createAndAddFragment();
    }

    private void createAndAddFragment(){

        Intent intent = getIntent();
        String curUser = intent.getExtras().getString(ActivityDetailActivity.CURRENT_USER_ID_EXTRA);
        LocalActivitiesActivity.FragmentToLaunch fragmentToLaunch =
                (LocalActivitiesActivity.FragmentToLaunch) intent.getSerializableExtra(LocalActivitiesActivity.FRAGMENT_TO_LOAD_EXTRA);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch(fragmentToLaunch){
            case EDIT:
                //create an edit note fragment
                ActivityEditFragment activityEditFragment = new ActivityEditFragment();
                setTitle(R.string.edit_fragment_title);
                fragmentTransaction.add(R.id.activity_container, activityEditFragment, "ACTIVITY_EDIT_FRAGMENT");
                break;

            case CREATE:
                //create a view note fragment
                ActivityEditFragment activityCreateFragment = new ActivityEditFragment();
                setTitle(R.string.create_activity_title);
                Bundle bundle = new Bundle();
                bundle.putBoolean(NEW_ACTIVITY_EXTRA, true);
                activityCreateFragment.setArguments(bundle);
                fragmentTransaction.add(R.id.activity_container, activityCreateFragment, "ACTIVITY_CREATE_FRAGMENT");
                break;

            case VIEW:
                //create a view note fragment
                ActivityViewFragment activityViewFragment = new ActivityViewFragment();
                setTitle(R.string.view_fragment_title);
                fragmentTransaction.add(R.id.activity_container, activityViewFragment, "ACTIVITY_VIEW_FRAGMENT");
                break;

        }


        fragmentTransaction.commit();

    }

}
