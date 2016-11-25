package com.jsalvagno.friend_list;

/**
 * Created by jordansalvagno on 9/20/16.
 */

public class Rating {

    private String comment, commenterUname, commenterImage;
    private float rating;
    private int userRated, userCommenter, ratingId;

    public Rating(String commenterUname, String comment, float rating, int ratingId, int userRated, int userCommenter, String commenterImage){

        this.commenterUname = commenterUname;
        this.comment = comment;
        this.ratingId = ratingId;
        this.rating = rating;
        this.userRated = userRated;
        this.userCommenter = userCommenter;
        this.commenterImage = commenterImage;

    }

    public String getCommenterUname(){
        return commenterUname;
    }
    public String getComment(){
        return comment;
    }

    public float getRating(){
        return rating;
    }

    public int getRatingId(){
        return ratingId;
    }

    public int getUserRated(){
        return userRated;
    }
    public int getUserComment(){

        return userCommenter;
    }

    public String getCommentersImage(){
       return commenterImage;
    }


    public String toString(){
        return "";
    }

    public int getAssociatedDrawable() {
        return userIdToDrawable(userCommenter);
    }

    public static int userIdToDrawable(long userId){
        return R.drawable.elcacto;
    }
}
