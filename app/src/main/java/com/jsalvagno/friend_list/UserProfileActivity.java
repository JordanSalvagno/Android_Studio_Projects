package com.jsalvagno.friend_list;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class UserProfileActivity extends BaseActivity {

    public static final String CURRENT_USER_ID_EXTRA = "com.jsalvagno.friend_list.Current_User";
    public static final String User_Ratings_URL_EXTRA = "com.jsalvagno.friend_list.Ratings";
    public static final String USER_ID_EXTRA = "com.jsalvagno.friend_list.user_id";
    public static final String FRAGMENT_TO_LOAD_EXTRA = "com.jsalvagno.friend_list.Fragment_to_Load";
    public static String name, uname, city, state, bio, email, activitiesURL, ratingsURL, imageURL;
    public static String userId, curUser;
    public static String zip;
    public enum UserFragmentToLaunch{VIEW, EDIT}
    UserProfileActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_user_profile);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_user_profile, null, false);
        drawer.addView(contentView, 0);
        activity = this;
        KeyValues keyValues = new KeyValues();
        curUser = keyValues.getCurrentUser(getApplicationContext());
        userId = getIntent().getStringExtra(USER_ID_EXTRA);
        String url = "http://192.241.232.97:3000/users/" + userId;
    retrieveUser(url);
    }

    private void createAndAddFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Intent intent = getIntent();
        UserProfileActivity.UserFragmentToLaunch fragmentToLaunch =
                (UserProfileActivity.UserFragmentToLaunch) intent.getSerializableExtra(UserProfileActivity.FRAGMENT_TO_LOAD_EXTRA);

        switch(fragmentToLaunch){
            case EDIT:
                //create an edit note fragment
                UserProfileEditFragment userProfileEditFragment= new UserProfileEditFragment();
                setTitle("Edit Profile");
                fragmentTransaction.add(R.id.user_container, userProfileEditFragment, "USER_PROFILE_EDIT_FRAGMENT");
                break;

            case VIEW:
                //create a view note fragment
                UserProfileFragment userProfileFragment= new UserProfileFragment();
                setTitle(R.string.profile_fragment_title);
                fragmentTransaction.add(R.id.user_container, userProfileFragment, "USER_PROFILE_FRAGMENT");
                break;

        }
/*
        UserProfileFragment userProfileFragment = new UserProfileFragment();
        fragmentTransaction.add(R.id.user_container, userProfileFragment, "USER_PROFILE_FRAGMENT");
        */
        fragmentTransaction.commit();
    }

    public void retrieveUser(String url) {
        Intent intent = getIntent();
        Log.d("user url", url);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                   //     String name, uname, city, bio, email, activitiesURL, ratingsURL;
//                        int zip;
                        try {
                            JSONObject userObject = response.optJSONObject("data");
                            JSONObject attributeObject = userObject.optJSONObject("attributes");
                            JSONObject userActivitiesObject = userObject.optJSONObject("relationships")
                                    .optJSONObject("activities").optJSONObject("links");
                            JSONObject userRatingsObject = userObject.optJSONObject("relationships")
                                    .optJSONObject("ratings").optJSONObject("links");
                            name = attributeObject.getString("name");
                            uname = attributeObject.getString("username");
                            city = attributeObject.getString("city");
                            state = attributeObject.getString("state");
                            bio = attributeObject.getString("bio");
                            email = attributeObject.getString("email");
                            zip = attributeObject.getString("zip");
                            imageURL = attributeObject.getString("image");
                            activitiesURL = userActivitiesObject.getString("related");
                            ratingsURL = userRatingsObject.getString("related");

                            createAndAddFragment();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        queue.add(jsonRequest);
    }
}
