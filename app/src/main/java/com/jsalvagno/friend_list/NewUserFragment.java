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
public class NewUserFragment extends Fragment {
    private EditText userName, realName, email, password, passwordConf, city, state, zip;
    private AlertDialog confirmDialogObject;
    private String curUser = "";

    public NewUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //inflate fragment edit layout
        View fragmentLayout = inflater.inflate(R.layout.fragment_new_user, container, false);

        //grab widgets refrences from layout
       //userName, realName, email, password, passwordConf, city, state, zip;
        userName = (EditText) fragmentLayout.findViewById(R.id.editUserName);
        realName = (EditText) fragmentLayout.findViewById(R.id.editUserRealName);
        email = (EditText) fragmentLayout.findViewById(R.id.editUserEmail);
        password= (EditText) fragmentLayout.findViewById(R.id.editUserPassword);
        passwordConf = (EditText) fragmentLayout.findViewById(R.id.editPasswordConf);
        city = (EditText) fragmentLayout.findViewById(R.id.editUserCity);
        state = (EditText) fragmentLayout.findViewById(R.id.editUserState);
        zip = (EditText) fragmentLayout.findViewById(R.id.editUserZip);

        //populate widgets with note data, if new note then leave blank
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

    private void createUser(){
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "http://192.241.232.97:3000/auth/";
        Log.d("url", url);
        JSONObject userObject = new JSONObject();
        try {
            userObject.put("username", userName.getText().toString());
            userObject.put("name", realName.getText().toString());
            userObject.put("email", email.getText().toString());
            userObject.put("password", password.getText().toString());
            userObject.put("password_confirmation", passwordConf.getText().toString());
            userObject.put("city", city.getText().toString());
            userObject.put("state", state.getText().toString());
            userObject.put("zip", zip.getText().toString());
        }catch(JSONException e){
            Log.e("JSONexception", e.toString());
        }
        Log.d("jsonParams", userObject.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, userObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("data", response.toString());
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
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
                    createUser();
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
