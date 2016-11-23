package com.jsalvagno.friend_list;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class CommentViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View contentView = inflater.inflate(R.layout.activity_comment_view, null, false);
        drawer.addView(contentView, 0);
       // setContentView(R.layout.activity_comment_view);

        createAndAddFragment();
    }

    private void createAndAddFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        CommentViewFragment commentViewFragment = new CommentViewFragment();
        setTitle("Rating Detail");
        fragmentTransaction.add(R.id.comment_container, commentViewFragment, "COMMENT_VIEW_FRAGMENT");

        fragmentTransaction.commit();

    }
}
