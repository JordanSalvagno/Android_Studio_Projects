package com.jsalvagno.friend_list;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserRatingsListFragment extends ListFragment {

    private ArrayList<Rating> ratings;
    private RatingAdapter ratingAdapter;
    private String ratingURL, comment, commenterName, curUser, imageURL;
    private int commentId, ratingId, userId;
    private float rating;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent intent = getActivity().getIntent();

        curUser = intent.getExtras().getString(UserProfileActivity.CURRENT_USER_ID_EXTRA);
        ratingURL = intent.getExtras().getString(UserProfileActivity.User_Ratings_URL_EXTRA);
        retrieveRatings(ratingURL);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        launchCommentViewActivity(position);

    }

    private void launchCommentViewActivity(int position) {

        //grab the Activity information for the row clicked
        Rating rating = (Rating) getListAdapter().getItem(position);

        //create a new intent that launches ActivityDetailActivity
        Intent intent = new Intent(getActivity(), CommentViewActivity.class);

        //pass along data from rating clicked
        intent.putExtra(UserRatingsActivity.CURRENT_USER_ID_EXTRA, curUser);
        intent.putExtra(UserRatingsActivity.RATING_COMMENT_EXTRA, rating.getComment());
        intent.putExtra(UserRatingsActivity.RATING_UNAME_EXTRA, rating.getUserComment());
        intent.putExtra(UserRatingsActivity.RATING_EXTRA, rating.getRating());
        intent.putExtra(UserRatingsActivity.RATING_USER_ID_EXTRA, rating.getUserComment());
        intent.putExtra(UserRatingsActivity.RATING_USER_IMAGE_EXTRA, rating.getCommentersImage());

        startActivity(intent);

    }

    public void retrieveRatings(String url) {
        url = url + "?include=rater";
        Log.d("rating url", url);
        ratings = new ArrayList<Rating>();
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray ratingArray = response.optJSONArray("data");
                            JSONArray commenterArray = response.optJSONArray("included");

                            //iterate through json array and fill ratings object
                            for (int i = 0; i < ratingArray.length(); i++) {

                                //get rating info
                                JSONObject ratingObject = ratingArray.getJSONObject(i);
                                ratingId = ratingObject.getInt("id");
                                JSONObject attributeObject = ratingObject.optJSONObject("attributes");
                                userId = attributeObject.getInt("user-id");
                                commentId = attributeObject.getInt("rater-id");
                                comment = attributeObject.getString("comment");
                                rating = (float) attributeObject.getDouble("score");

                                //find username of the rater
                                JSONObject commenterObject = commenterArray.getJSONObject(i).optJSONObject("attributes");
                                commenterName = commenterObject.getString("username");
                                imageURL = commenterObject.getString("image");

                                ratings.add(new Rating(commenterName,comment, rating, ratingId, userId, commentId, imageURL));
                            }
                            ratingAdapter = new RatingAdapter(getActivity(), ratings);
                            setListAdapter(ratingAdapter);
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
}
