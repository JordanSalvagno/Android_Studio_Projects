package com.jsalvagno.friend_list;


import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocalActivitiesListFragment extends ListFragment {

    private ArrayList<Activity> activities;
    private ActivityAdapter activityAdapter;
    private EditText inputZip;
    String zipCode = "";
    EditText zipInput;
    String title, message, city, state, URL, curUser, imageURL;
    int activityId, userId;
    int zip;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent intent = getActivity().getIntent();
        KeyValues keyValues = new KeyValues();
        curUser = keyValues.getCurrentUser(LocalActivitiesActivity.mContext);
        zipInput = (EditText) getActivity().findViewById(R.id.inputZip);
        Button searchButton = (Button) getActivity().findViewById(R.id.searchZip);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zipCode = zipInput.getText().toString();
                URL = "http://192.241.232.97:3000/activities?filter[zip]=" + zipCode;
                retrieveActivities(URL);
            }
        });

        URL = "http://192.241.232.97:3000/users/" + curUser + "/activities";
        retrieveActivities(URL);
    }


    public void retrieveActivities(String url) {
        activities = new ArrayList<Activity>();
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray activityArray = response.optJSONArray("data");
                            Log.d("response", response.toString());

                            for (int i = 0; i < activityArray.length(); i++) {

                                JSONObject activityObject = activityArray.getJSONObject(i);
                                JSONObject attributeObject = activityObject.optJSONObject("attributes");

                                activityId = activityObject.getInt("id");
                                userId = attributeObject.getInt("user-id");
                                message = attributeObject.getString("message");
                                title = attributeObject.getString("title");
                                city = attributeObject.getString("city");
                                state = attributeObject.getString("state");
                                zip = attributeObject.getInt("zip");
                                imageURL = attributeObject.getString("user-image");
                                activities.add(new Activity(title, message, city, state, zip, userId, activityId, imageURL));
                            }
                            activityAdapter = new ActivityAdapter(getActivity(), activities);
                            setListAdapter(activityAdapter);
                            registerForContextMenu(getListView());
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

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        launchActivityDetailActivity(LocalActivitiesActivity.FragmentToLaunch.VIEW, position);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.long_press_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        //get position of item that was long pressed on
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int rowPosition = info.position;

        switch (item.getItemId()) {

            case R.id.edit:

                launchActivityDetailActivity(LocalActivitiesActivity.FragmentToLaunch.EDIT, rowPosition);
                Log.d("Menu Clicks", "We pressed edit!");

        }

        return super.onContextItemSelected(item);
    }

    private void launchActivityDetailActivity(LocalActivitiesActivity.FragmentToLaunch ftl, int position) {

        //grab the Activity information for the row clicked
        Activity activity = (Activity) getListAdapter().getItem(position);

        //create a new intent that launches ActivityDetailActivity
        Intent intent = new Intent(getActivity(), ActivityDetailActivity.class);

        //pass along data from activity clicked
        intent.putExtra(LocalActivitiesActivity.ACTIVITY_TITLE_EXTRA, activity.getTitle());
        intent.putExtra(LocalActivitiesActivity.ACTIVITY_MESSAGE_EXTRA, activity.getMessage());
        intent.putExtra(LocalActivitiesActivity.ACTIVITY_CITY_EXTRA, activity.getCity());
        intent.putExtra(LocalActivitiesActivity.ACTIVITY_STATE_EXTRA, activity.getState());
        intent.putExtra(LocalActivitiesActivity.ACTIVITY_ZIP_EXTRA, "" + activity.getZip());
        intent.putExtra(LocalActivitiesActivity.ACTIVITY_ID_EXTRA, "" + activity.getActivityId());
        Log.d("activity ID", "" + activity.getActivityId());
        intent.putExtra(LocalActivitiesActivity.ACTIVITY_USER_ID_EXTRA, "" + activity.getUserId());
        intent.putExtra(LocalActivitiesActivity.CURRENT_USER_ID_EXTRA, curUser);
        intent.putExtra(LocalActivitiesActivity.ACTIVITY_USER_IMAGE_EXTRA, activity.getUserImageURL());
        Log.d("user ID", "" + activity.getUserId());
        Log.d("Current user ID", curUser);
        switch (ftl) {
            case VIEW:
                intent.putExtra(LocalActivitiesActivity.FRAGMENT_TO_LOAD_EXTRA, LocalActivitiesActivity.FragmentToLaunch.VIEW);
                break;
            case EDIT:
                intent.putExtra(LocalActivitiesActivity.FRAGMENT_TO_LOAD_EXTRA, LocalActivitiesActivity.FragmentToLaunch.EDIT);
                break;
            case CREATE:
                intent.putExtra(LocalActivitiesActivity.FRAGMENT_TO_LOAD_EXTRA, LocalActivitiesActivity.FragmentToLaunch.CREATE);
                break;
        }

        startActivity(intent);

    }
}
