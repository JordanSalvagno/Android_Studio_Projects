package com.jsalvagno.friend_list;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jordansalvagno on 11/2/16.
 */

public class KeyValues {
    private SharedPreferences sharedPreferences;
    private static final String CURRENT_USER_ID = "com.jsalvagno.friend_list.Current User";

    public KeyValues(){

    }

    public void setCurrentUser(Context context, String curUser){
        SharedPreferences sharedPreferences = context.getSharedPreferences(CURRENT_USER_ID, context.MODE_PRIVATE);
        SharedPreferences.Editor currentUserEdit = sharedPreferences.edit();
        currentUserEdit.putString(CURRENT_USER_ID, curUser);
        currentUserEdit.commit();
    }

    public String getCurrentUser(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(CURRENT_USER_ID, context.MODE_PRIVATE);
        return sharedPreferences.getString(CURRENT_USER_ID, "error");
    }
}
