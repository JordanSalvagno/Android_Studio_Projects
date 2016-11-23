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
public class UserProfileEditFragment extends Fragment {

    EditText EditName, EditEmail, EditCity, EditState, EditZip, EditBio;
    private AlertDialog confirmDialogObject;

    public UserProfileEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentLayout = inflater.inflate(R.layout.fragment_user_profile_edit, container, false);

        EditName = (EditText) fragmentLayout.findViewById(R.id.editUserRealName);
        EditEmail = (EditText) fragmentLayout.findViewById(R.id.editUserEmail);
        EditCity = (EditText) fragmentLayout.findViewById(R.id.editUserCity);
        EditState = (EditText) fragmentLayout.findViewById(R.id.editUserState);
        EditZip = (EditText) fragmentLayout.findViewById(R.id.editUserZip);
        EditBio = (EditText) fragmentLayout.findViewById(R.id.editUserBio);

        /*userId = intent.getExtras().getString(ActivityDetailActivity.USER_ID_EXTRA);
        curUser = intent.getExtras().getString(ActivityDetailActivity.CURRENT_USER_ID_EXTRA);
        activitiesURL = intent.getExtras().getString(ActivityDetailActivity.USER_ACTIVITIES_EXTRA);
        ratingsURL = intent.getExtras().getString(ActivityDetailActivity.USER_RATINGS_EXTRA);*/
        EditName.setText(UserProfileActivity.name);
        EditEmail.setText(UserProfileActivity.email);
        EditCity.setText(UserProfileActivity.city);
        EditState.setText(UserProfileActivity.state);
        EditZip.setText(UserProfileActivity.zip);
        EditBio.setText(UserProfileActivity.bio);

        Button savedButton = (Button) fragmentLayout.findViewById(R.id.saveUser);

        buildConfirmDialog();

        savedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialogObject.show();
            }
        });
        return fragmentLayout;

    }

    private void updateUser() {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Log.d("activity ID edit", UserProfileActivity.curUser);
        String url = "http://192.241.232.97:3000/users/" + UserProfileActivity.curUser;
        Log.d("url", url);
        Map<String, JSONObject> jsonParams = new HashMap<>();
        JSONObject jsonObject = new JSONObject();
        JSONObject attributeObject = new JSONObject();
        try {
            attributeObject.put("name", EditName.getText().toString());
            attributeObject.put("email", EditEmail.getText().toString());
            attributeObject.put("city", EditCity.getText().toString());
            attributeObject.put("state", EditState.getText().toString());
            attributeObject.put("zip", EditZip.getText().toString());
            attributeObject.put("bio", EditBio.getText().toString());
            jsonObject.put("id", UserProfileActivity.curUser);
            jsonObject.put("type", "users");
            jsonObject.put("attributes", attributeObject);
            jsonParams.put("data", jsonObject);
        } catch (JSONException e) {

        }
        Log.d("jsonParams", jsonParams.toString());
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
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/vnd.api+json");
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

                    updateUser();
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
