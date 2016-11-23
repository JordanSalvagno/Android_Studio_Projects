package com.jsalvagno.friend_list;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class NewUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        createAndAddFragment();
    }
    private void createAndAddFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        NewUserFragment newUserFragment = new NewUserFragment();
        setTitle("Rating Detail");
        fragmentTransaction.add(R.id.new_user_container, newUserFragment, "NEW_USER_FRAGMENT");

        fragmentTransaction.commit();
    }
}
