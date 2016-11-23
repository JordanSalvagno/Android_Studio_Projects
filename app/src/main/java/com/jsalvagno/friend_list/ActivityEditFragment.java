package com.jsalvagno.friend_list;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ActivityEditFragment extends Fragment {

    private EditText title, message, city, state, zip;
    private AlertDialog confirmDialogObject;
    private boolean newActivity = false;
    private String curUser = "";
    private String activityId = "";

    public ActivityEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //find out whether a new note is being created
       //inflate fragment edit layout
        View fragmentLayout = inflater.inflate(R.layout.fragment_activity_edit, container, false);

        //grab widgets refrences from layout
        title = (EditText) fragmentLayout.findViewById(R.id.editActivityTitle);
        message = (EditText) fragmentLayout.findViewById(R.id.editActivityMessage);
        city = (EditText) fragmentLayout.findViewById(R.id.editActivityCity);
        state = (EditText) fragmentLayout.findViewById(R.id.editActivityState);
        zip = (EditText) fragmentLayout.findViewById(R.id.editActivityZip);


        //populate widgets with note data, if new note then leave blank
        Intent intent = getActivity().getIntent();
        Bundle bundle = this.getArguments();
        if(bundle != null){
            newActivity = bundle.getBoolean(ActivityDetailActivity.NEW_ACTIVITY_EXTRA, false);
        }
        else {
            title.setText(intent.getExtras().getString(LocalActivitiesActivity.ACTIVITY_TITLE_EXTRA, "Activity title"));
            message.setText(intent.getExtras().getString(LocalActivitiesActivity.ACTIVITY_MESSAGE_EXTRA, "Describe activity"));
            city.setText(intent.getExtras().getString(LocalActivitiesActivity.ACTIVITY_CITY_EXTRA, "City"));
            state.setText(intent.getExtras().getString(LocalActivitiesActivity.ACTIVITY_STATE_EXTRA, "State"));
            zip.setText(intent.getExtras().getString(LocalActivitiesActivity.ACTIVITY_ZIP_EXTRA, "Zip"));
            activityId = intent.getExtras().getString(LocalActivitiesActivity.ACTIVITY_ID_EXTRA, "-1");
        }
        curUser = intent.getExtras().getString(LocalActivitiesActivity.CURRENT_USER_ID_EXTRA, "0");
        Log.d("Current User", curUser);
        Button savedButton = (Button) fragmentLayout.findViewById(R.id.saveActivity);

        //find out if we are creating a new activity or editing an existing one

        buildConfirmDialog();

        savedButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                confirmDialogObject.show();
            }
        });

        //return layout
        return fragmentLayout;
    }

    private void createActivity(){
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "http://192.241.232.97:3000/activities";
        Log.d("url", url);
        Map<String, JSONObject> jsonParams = new HashMap<String, JSONObject>();
        JSONObject jsonObject = new JSONObject();
        JSONObject attributeObject = new JSONObject();
        JSONObject relationshipObject = new JSONObject();
        JSONObject userObject = new JSONObject();
        JSONObject userDataObject = new JSONObject();
        try {
            attributeObject.put("title", title.getText().toString());
            attributeObject.put("message", message.getText().toString());
            attributeObject.put("city", city.getText().toString());
            attributeObject.put("state", state.getText().toString());
            attributeObject.put("zip", zip.getText().toString());
            userDataObject.put("type", "users");
            userDataObject.put("id", curUser);
            userObject.put("data", userDataObject);
            relationshipObject.put("user", userObject);
            jsonObject.put("type", "activities" );
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
    private void updateActivity(){
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Log.d("activity ID edit", activityId);
        String url = "http://192.241.232.97:3000/activities/" + activityId;
        Log.d("url", url);
        Map<String, JSONObject> jsonParams = new HashMap<String, JSONObject>();
        JSONObject jsonObject = new JSONObject();
        JSONObject attributeObject = new JSONObject();
        try {
            attributeObject.put("title", title.getText().toString());
            attributeObject.put("message", message.getText().toString());
            attributeObject.put("city", city.getText().toString());
            attributeObject.put("state", state.getText().toString());
            attributeObject.put("zip", zip.getText().toString());
            jsonObject.put("id", activityId );
            jsonObject.put("type", "activities" );
            jsonObject.put("attributes", attributeObject);
            jsonParams.put("data", jsonObject);
        }catch(JSONException e){

        }
        Log.d("jsonParams", jsonParams.toString());
        JSONObject js = new JSONObject();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH, url, new JSONObject(jsonParams),
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

    private void buildConfirmDialog() {
        AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(getActivity());
        confirmBuilder.setTitle("Are you sure?");
        confirmBuilder.setMessage("Are you sure you want to save the note?");

        confirmBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                Log.d("Save Activity", "Activity title: " + title.getText() + "Activity message: "
                        + message.getText());

                if(!newActivity)
                    updateActivity();
                else
                    createActivity();
            }
        });

        confirmBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing
            }
        });

        confirmDialogObject = confirmBuilder.create();
    }
}
