package com.jsalvagno.friend_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class LocalActivitiesActivity extends BaseActivity {

    public static final String CURRENT_USER_ID_EXTRA = "com.jsalvagno.friend_list.";
    public static final String ACTIVITY_ID_EXTRA = "com.jsalvagno.friend_list.activity";
    public static final String ACTIVITY_TITLE_EXTRA = "com.jsalvagno.friend_list.Title";
    public static final String ACTIVITY_MESSAGE_EXTRA = "com.jsalvagno.friend_list.Message";
    public static final String ACTIVITY_CITY_EXTRA = "com.jsalvagno.friend_list.city";
    public static final String ACTIVITY_STATE_EXTRA = "com.jsalvagno.friend_list.state";
    public static final String ACTIVITY_ZIP_EXTRA = "com.jsalvagno.friend_list.zip";
    public static final String ACTIVITY_USER_ID_EXTRA = "com.jsalvagno.friend_list.User";
    public static final String ACTIVITY_USER_IMAGE_EXTRA = "com.jsalvagno.friend_list.activity userImage";
    public static final String FRAGMENT_TO_LOAD_EXTRA = "com.jsalvagno.friend_list.Fragment_to_Load";
    public static Context mContext;

    public enum FragmentToLaunch{VIEW, EDIT, CREATE}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View contentView = inflater.inflate(R.layout.activity_local_activities, null, false);
        drawer.addView(contentView, 0);
        mContext = getApplicationContext();

    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_local_activities, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }else if(id == R.id.action_add_activity){
             KeyValues keyValues = new KeyValues();
            String curUser = keyValues.getCurrentUser(LocalActivitiesActivity.mContext);
            Intent intent = this.getIntent();
            Log.d("current user", curUser);
             intent = new Intent(this, ActivityDetailActivity.class);
            intent.putExtra(LocalActivitiesActivity.FRAGMENT_TO_LOAD_EXTRA, LocalActivitiesActivity.FragmentToLaunch.CREATE);
            intent.putExtra(LocalActivitiesActivity.CURRENT_USER_ID_EXTRA, curUser);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/

}

