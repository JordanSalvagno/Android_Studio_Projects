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
    private ArrayList<String> userLinks;
    private InterestedUserAdapter interestedUserAdapter;
    private EditText inputZip;
    String curUser;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d("Interested users", "Looking for interested users");
        super.onActivityCreated(savedInstanceState);
        Intent intent = getActivity().getIntent();
        KeyValues keyValues = new KeyValues();
        curUser = keyValues.getCurrentUser(LocalActivitiesActivity.mContext);
        retrieveInterestedUsers();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        launchInterestedUserActivity(position);

    }

    private void launchInterestedUserActivity(int position) {

        //grab the Activity information for the row clicked
        User user = (User) getListAdapter().getItem(position);

        //create a new intent that launches ActivityDetailActivity
        Intent intent = new Intent(getActivity(), UserProfileActivity.class);

        //pass along data from rating clicked
        intent.putExtra(UserRatingsActivity.CURRENT_USER_ID_EXTRA, curUser);

        startActivity(intent);

    }

    public void retrieveInterestedUsers() {
        String url = "http://192.241.232.97:3000/users/" + curUser + "/activities";
        users = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray activityArray = response.optJSONArray("data");
                            Log.d("response", response.toString());
                            String name, uname, city, state, zip, bio, email, activitiesURL, ratingsURL, image;
                            for (int i = 0; i < activityArray.length(); i++) {
                                JSONArray userArray = activityArray.getJSONObject(i).getJSONObject("attributes").getJSONArray("interested-users");
                                for (int ii = 0; ii < userArray.length(); ii++) {
                                    JSONObject userObject = userArray.getJSONObject(ii);
                                        name = userObject.getString("name");
                                        uname = userObject.getString("username");
                                        city = userObject.getString("city");
                                        state = userObject.getString("state");
                                        bio = userObject.getString("bio");
                                        email = userObject.getString("email");
                                        zip = userObject.getString("zip");
                                        image = userObject.getString("image");
                                        activitiesURL = "";
                                        ratingsURL = "";
                                        users.add(new User(name, uname, email, bio, activitiesURL, ratingsURL, city, zip, image));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        interestedUserAdapter = new InterestedUserAdapter(getActivity(),users);
                        setListAdapter(interestedUserAdapter);
                        registerForContextMenu(getListView());
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
