package com.jsalvagno.friend_list;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.jsalvagno.friend_list.ActivityDetailActivity.mContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class ActivityViewFragment extends Fragment {


    public ActivityViewFragment() {
        // Required empty public constructor
    }

    TextView messageView;
    TextView titleView;
    String activityId, userId, curUser, URL;
    ImageLoader imageLoader;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentLayout = inflater.inflate(R.layout.fragment_activity_view, container, false);

        titleView = (TextView) fragmentLayout.findViewById(R.id.viewActivityTitle);
        messageView = (TextView) fragmentLayout.findViewById(R.id.viewActivityMessage);
        NetworkImageView icon = (NetworkImageView) fragmentLayout.findViewById(R.id.viewActivityIcon);
        FloatingActionButton button = (FloatingActionButton) fragmentLayout.findViewById(R.id.fab);

        Intent intent = getActivity().getIntent();

        titleView.setText(intent.getExtras().getString(LocalActivitiesActivity.ACTIVITY_TITLE_EXTRA));
        messageView.setText(intent.getExtras().getString(LocalActivitiesActivity.ACTIVITY_MESSAGE_EXTRA));
        KeyValues keyValues = new KeyValues();
        curUser = keyValues.getCurrentUser(mContext);
        activityId = intent.getExtras().getString(LocalActivitiesActivity.ACTIVITY_ID_EXTRA);
        userId = intent.getExtras().getString(LocalActivitiesActivity.ACTIVITY_USER_ID_EXTRA);
        URL = intent.getExtras().getString(LocalActivitiesActivity.ACTIVITY_USER_IMAGE_EXTRA);
        imageLoader = MySingleton.getInstance(this.getContext()).getImageLoader();
        icon.setImageUrl(URL, imageLoader);

        //new RetrieveActivity().execute();

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), UserProfileActivity.class);

                //pass along data from activity clicked
                intent.putExtra(UserProfileActivity.FRAGMENT_TO_LOAD_EXTRA, UserProfileActivity.UserFragmentToLaunch.VIEW);
                intent.putExtra(UserProfileActivity.USER_ID_EXTRA, userId);
                startActivity(intent);
                //retrieveUser();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userId != curUser) {
                    setInterest();
                }
            }
        });
        return fragmentLayout;
    }

    public void setInterest(){
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "http://192.241.232.97:3000/activity-users/";
        Log.d("url", url);
        Map<String, JSONObject> jsonParams = new HashMap<String, JSONObject>();
        JSONObject jsonObject = new JSONObject();
        JSONObject attributeObject = new JSONObject();
        JSONObject relationshipObject = new JSONObject();
        JSONObject userObject = new JSONObject();
        JSONObject userDataObject = new JSONObject();
        JSONObject activityObject = new JSONObject();
        JSONObject activityDataObject = new JSONObject();
        try {
            userDataObject.put("type", "users");
            userDataObject.put("id", curUser);
            userObject.put("data", userDataObject);
            activityDataObject.put("type", "activities");
            activityDataObject.put("id", activityId);
            activityObject.put("data", activityDataObject);
            relationshipObject.put("user", userObject);
            relationshipObject.put("activity", activityObject);
            jsonObject.put("type", "activity-users" );
            jsonObject.put("attributes", attributeObject);
            jsonObject.put("relationships", relationshipObject);
            jsonParams.put("data", jsonObject);
        }catch(JSONException e){
            Log.e("JSONexception", e.toString());
        }
        Log.d("jsonParams", jsonParams.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("data", response.toString());
                        Intent intent = new Intent(getActivity(), LocalActivitiesActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/vnd.api+json" );
                headers.put("Content-Type", "application/vnd.api+json");
                Log.d("headers", headers.toString());
                return headers;
            }
        };
        queue.add(jsonObjectRequest);

    }
    public void retrieveUser() {
        Intent intent = getActivity().getIntent();
        String url = "http://192.241.232.97:3000/users/" + userId;
        Log.d("user url", url);
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

                            Intent intent = new Intent(getActivity(), UserProfileActivity.class);

                            //pass along data from activity clicked
                            intent.putExtra(ActivityDetailActivity.USER_ID_EXTRA, userId);
                            intent.putExtra(ActivityDetailActivity.USER_NAME_EXTRA, name);
                            intent.putExtra(ActivityDetailActivity.USER_UNAME_EXTRA, uname);
                            intent.putExtra(ActivityDetailActivity.USER_CITY_EXTRA, city);
                            intent.putExtra(ActivityDetailActivity.USER_BIO_EXTRA, bio);
                            intent.putExtra(ActivityDetailActivity.USER_EMAIL_EXTRA, email);
                            intent.putExtra(ActivityDetailActivity.USER_ZIP_EXTRA, zip);
                            intent.putExtra(ActivityDetailActivity.USER_ACTIVITIES_EXTRA, activitiesURL);
                            intent.putExtra(ActivityDetailActivity.USER_RATINGS_EXTRA, ratingsURL);
                            intent.putExtra(ActivityDetailActivity.CURRENT_USER_ID_EXTRA, curUser);

                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        launchUserProfileActivity();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        queue.add(jsonRequest);
    }

    private void launchUserProfileActivity() {

        //create a new intent that launches ActivityDetailActivity

    }

}
