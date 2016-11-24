package com.jsalvagno.friend_list;

import android.icu.util.ULocale;
import android.util.Log;

import java.util.Locale;

/**
 * Created by jordansalvagno on 9/6/16.
 */
public class Activity {
    private String title, message, city, state;
    private int activityId, userId;
    private int zip;
    private User user;

    public Activity(String title, String message, String city, String state, int zip, int userId, int activityId){
        this.title = title;
        this.message = message;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.userId = userId;
        this.activityId = activityId;
        this.user = new User("aljlsdjf", "aljsdfjlasjldfj", "username", "j@j.com", "ajlsdjflasjldf" , "aljsdlfjalsd","chico", "95963");
    }

    public Activity(String title, String message, String city, int zip, User user){
        this.title = title;
        this.message = message;
        this.city = city;
        this.zip = zip;
        this.activityId = 0;
        this.userId = 0;
        this.user = user;
    }

    public String getTitle(){
        return title;
    }

    public String getMessage(){
        return message;
    }

    public String getCity(){
        return city;
    }

    public String getState(){
        return state;
    }

    public int getZip(){
        return zip;
    }

    public int getActivityId(){
        return activityId;
    }

    public User getUser(){
       return user;
    }

    public int getUserId(){
       Log.i("User-id in activity:", "" + userId);
        return userId;
    }

    public String toString(){
        return "ID: " + activityId + " User ID: " + userId + " Title: " + " Message: " + message +
                " city: " + city + " Zip: " + zip;
    }

    public int getAssociatedDrawable() {
        return user.getAssociatedDrawable();
    }

    public static int userIdToDrawable(long userId){
        return R.drawable.elcacto;
    }
}
