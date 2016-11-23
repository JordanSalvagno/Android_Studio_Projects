package com.jsalvagno.friend_list;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CommentViewFragment extends Fragment {


    public CommentViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentLayout = inflater.inflate(R.layout.fragment_comment_view, container, false);

        TextView userName = (TextView) fragmentLayout.findViewById(R.id.commentViewUserName);
        TextView comment = (TextView) fragmentLayout.findViewById(R.id.commentViewComment);
        RatingBar rating = (RatingBar) fragmentLayout.findViewById(R.id.commentViewRating);
        ImageView icon = (ImageView) fragmentLayout.findViewById(R.id.commentViewUserIcon);

        Intent intent = getActivity().getIntent();

        userName.setText(intent.getExtras().getString(UserRatingsActivity.RATING_UNAME_EXTRA));
        comment.setText(intent.getExtras().getString(UserRatingsActivity.RATING_COMMENT_EXTRA));
        rating.setRating(intent.getExtras().getFloat(UserRatingsActivity.RATING_EXTRA));

        long userId = intent.getExtras().getLong(UserRatingsActivity.RATING_USER_ID_EXTRA);

        icon.setImageResource(Activity.userIdToDrawable(userId));

        return fragmentLayout;
    }

    private void launchUserProfileActivity(){

    }

}
