package com.jsalvagno.friend_list;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class InterestedUsersListFragment extends ListFragment {

    private ArrayList<User> users;
    private InterestedUserAdapter interestedUserAdapter;
    private EditText inputZip;
    String zipCode = "";
    EditText zipInput;
    String title, message, city, state, curUser;
    int activityId, userId;
    int zip;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent intent = getActivity().getIntent();
        KeyValues keyValues = new KeyValues();
        curUser = keyValues.getCurrentUser(LocalActivitiesActivity.mContext);
        retrieveInterestedUsers();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        launchCommentViewActivity(position);

    }

    private void launchCommentViewActivity(int position) {

        //grab the Activity information for the row clicked
        User user = (User) getListAdapter().getItem(position);

        //create a new intent that launches ActivityDetailActivity
        Intent intent = new Intent(getActivity(), UserProfileActivity.class);

        //pass along data from rating clicked
        intent.putExtra(UserRatingsActivity.CURRENT_USER_ID_EXTRA, curUser);

        startActivity(intent);

    }

    public void retrieveInterestedUsers() {
        String url = "http://192.241.232.97:3000/users/" + curUser + "/activity-users";
        users = new ArrayList<User>();
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray activityArray = response.optJSONArray("data");
                            Log.d("response", response.toString());

                            for (int i = 0; i < activityArray.length(); i++) {
                                JSONObject userObject = activityArray.getJSONObject(i)
                                        .getJSONObject("relationships").getJSONObject("user")
                                        .getJSONObject("links");
                                String url = userObject.getString("related");
                                retrieveUser(url);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        interestedUserAdapter = new InterestedUserAdapter(getActivity(),users);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(jsonRequest);
    }
    public void retrieveUser(String url) {
        Log.d("Interested user url", url);
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String name, uname, city, bio, email, activitiesURL, ratingsURL;
                        int zip;
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
                            bio = attributeObject.getString("bio");
                            email = attributeObject.getString("email");
                            zip = attributeObject.getInt("zip");
                            activitiesURL = userActivitiesObject.getString("related");
                            ratingsURL = userRatingsObject.getString("related");

                            users.add(new User(name, uname, email, bio, activitiesURL, ratingsURL, city, zip));

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
