package com.jsalvagno.friend_list;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by jordansalvagno on 9/13/16.
 */
public class User {

    private String name, uname, city, bio, email, activitiesURL, ratingsURL, zip, image;
    private int userId;

    public User()
    {
        this.uname = "";
        this.email = "";
        this.activitiesURL = "";
        this.ratingsURL = "";
        this.city = "";
        this.zip = "";
        this.bio = "";
        this.userId= 0;
    }

    public User(String name, String uname, String email, String bio, String activitiesURL, String ratingsURL, String city, String zip, String image){
        this.uname = uname;
        this.email = email;
        this.activitiesURL = activitiesURL;
        this.ratingsURL = ratingsURL;
        this.city = city;
        this.zip = zip;
        this.bio = bio;
        this.userId = 0;
        this.image = image;
    }

    public String getname(){
        return name;
    }


    public String getUname(){
        return uname;
    }

    public String getEmail(){
        return email;
    }

    public String getCity() {
        return city;
    }

    public String getZip() {
        return zip;
    }

    public String getBio(){
        return bio;
    }

    public int getUserId(){
        return userId;
    }

    public String getRatingsURL(){
        return ratingsURL;
    }

    public String getImageUrl(){
        return image;
    }

    public String toString(){
        return "UserId: " + userId + " First Name " + name + " User Name " +
                " Email: " + email + " Bio: " + bio + " city: " + city + " Zip: " + zip;
    }

    public int getAssociatedDrawable() {
        return userIdToDrawable(userId);
    }

    public static int userIdToDrawable(long userId){
        return R.drawable.elcacto;
    }

}
