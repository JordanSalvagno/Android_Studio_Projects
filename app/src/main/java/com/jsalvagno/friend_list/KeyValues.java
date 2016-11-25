package com.jsalvagno.friend_list;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by jordansalvagno on 11/2/16.
 */

public class KeyValues {
    private static final String CURRENT_USER_ID = "com.jsalvagno.friend_list.Current User";
    private static final String CURRENT_USER_IMAGE = "com.jsalvagno.friend_list.Current User Image";

    public KeyValues(){

    }

    public void setCurrentUser(Context context, String curUser){
        SharedPreferences sharedPreferences = context.getSharedPreferences(CURRENT_USER_ID, context.MODE_PRIVATE);
        SharedPreferences.Editor currentUserEdit = sharedPreferences.edit();
        currentUserEdit.putString(CURRENT_USER_ID, curUser);
        Log.d("Current User", curUser);
        currentUserEdit.commit();
    }

    public String getCurrentUser(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(CURRENT_USER_ID, context.MODE_PRIVATE);
        return sharedPreferences.getString(CURRENT_USER_ID, "error");
    }

    public void setCurrentImage(Context context, String imageUrl){
        SharedPreferences sharedPreferences = context.getSharedPreferences(CURRENT_USER_IMAGE, context.MODE_PRIVATE);
        SharedPreferences.Editor currentImage = sharedPreferences.edit();
        currentImage.putString(CURRENT_USER_IMAGE, imageUrl);
        Log.d("Current Image", imageUrl);
        currentImage.commit();
    }

    public String getCurrentImage(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(CURRENT_USER_IMAGE, context.MODE_PRIVATE);
        return sharedPreferences.getString(CURRENT_USER_IMAGE, "error");
    }
}
